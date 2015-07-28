(ns clojure-snake.core
  (:import (javax.swing JFrame JPanel)
           (java.awt.event WindowListener KeyListener)
           (java.awt Color Dimension)))

(def snake (atom [[0 1] [0 2] [0 3] [0 4]]))
(def snake-belly (atom 0))
(def apples (atom [[10 3] [20 14] [39 39] [1 20]]))
(def size 5)
(def world-size 40)
(def direction (atom "down"))
(def game-over (atom false))

(defn remove-tail [snake] (subvec snake 1))

(defn drop-apple [apples max-x max-y]
  (conj apples [(rand-int max-x) (rand-int max-y)]))

(defn eat [snake snake-belly apples]
  (if-not (nil? (some #{(last snake)} @apples))
    (do
      (swap! apples #(filterv (fn [a] (not= (last snake) a)) %))
      (swap! snake-belly #(+ 5 %))
      (swap! apples #(drop-apple % world-size world-size)))))

(defn in-vector? [coords match] (some #{match} coords))

(defn metabolize [snake]
  (if (> @snake-belly 0)
  (do
    (swap! snake-belly #(- % 1))
    snake)
  (remove-tail snake)))

(defn move-down [snake]
  (conj snake [(first (last snake)) (mod (inc (second (last snake))) world-size)]))

(defn move-left [snake]
  (conj snake [(mod (dec (first (last snake))) world-size) (second (last snake))]))

(defn move-right [snake]
  (conj snake [(mod (inc (first (last snake))) world-size) (second (last snake))]))

(defn move-up [snake]
  (conj snake [(first (last snake)) (mod (dec (second (last snake))) world-size)]))

(defn paint-snake [g snake]
  (.setColor g Color/black)
  (doall (for [block snake]
      (.fillRect g (* size (first block)) (* size (second block)) size size))))

(defn paint-apples [g apples]
  (.setColor g Color/red)
  (doall (for [block apples]
      (.fillRect g (* size (first block)) (* size (second block)) size size))))

(defn move-direction [direction snake]
  (case direction
    "left" (move-left snake)
    "right" (move-right snake)
    "down" (move-down snake)
    "up" (move-up snake)))

(defn end-game [] (reset! game-over true))

(defn tick [drawable direction snake]
  (let [new-snake (metabolize (move-direction direction @snake))]
    (if (in-vector? @snake (last new-snake)) (end-game))
    (reset! snake new-snake)
    (eat @snake snake-belly apples)
    (.repaint drawable)))

(defn on-window-close [e] (end-game))

(defn on-key-press [e] (let [new-direction (case (.getKeyCode e)
                          ;left
                          37 "left"
                          ;right
                          39 "right"
                          ;down
                          40 "down"
                          ;up
                          38 "up"
                          @direction)]

                          (if-not (or
                                (and (= "right" @direction) (= "left" new-direction))
                                (and (= "right" new-direction) (= "left" @direction))
                                (and (= "up" new-direction) (= "down" @direction))
                                (and (= "up" @direction) (= "down" new-direction)))
                              (reset! direction new-direction))))

(defn get-speed [snake]
  (max 32 (- 200 (* (count snake) 2)))

(defn game []
  (let [window (proxy [JFrame] ["snake!"])
        drawable (proxy [JPanel] []
                   (paintComponent [g]
                     (proxy-super paintComponent g)
                     (paint-snake g @snake)
                     (paint-apples g @apples)))]
    (.setPreferredSize drawable (Dimension. (* size world-size) (* size world-size)))
    (doto window
      (.addWindowListener (proxy [WindowListener] []
                                   (windowDeactivated [e])
                                   (windowOpened [e])
                                   (windowActivated [e])
                                   (windowClosing [e] (on-window-close e))))
      (.addKeyListener (proxy [KeyListener] []
                                (keyReleased [e])
                                (keyTyped [e])
                                (keyPressed [e] (on-key-press e))))
      (.setFocusable true)
      (.setResizable false)
      (.add drawable)
      (.pack)
      (.setVisible true))

    (future (loop []
              (Thread/sleep (get-speed @snake))
              (tick drawable @direction snake)
              (when (not @game-over) (recur))))))
