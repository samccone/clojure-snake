(ns clojure-snake.core
  (:import (javax.swing JFrame JPanel)
           (java.awt.event WindowListener KeyListener)))

(def snake (atom [[0 1] [0 2] [0 3] [0 4]]))
(def size 5)
(def direction (atom "down"))
(def game-over (atom false))

(defn remove-tail [snake] (subvec snake 1))

(defn move-down [snake]
  (conj snake [(first (last snake)) (inc (second (last snake)))]))

(defn move-left [snake]
  (conj snake [(dec (first (last snake))) (second (last snake))]))

(defn move-right [snake]
  (conj snake [(inc (first (last snake))) (second (last snake))]))

(defn move-up [snake]
  (conj snake [(first (last snake)) (dec (second (last snake)))]))

(defn paint-snake [g snake]
  (doall (for [block snake]
      (.fillRect g (* size (first block)) (* size (second block)) size size))))

(defn move-direction [direction snake]
  (case direction
    "left" (move-left snake)
    "right" (move-right snake)
    "down" (move-down snake)
    "up" (move-up snake)))

(defn tick [drawable direction snake]
  (swap! snake #(remove-tail (move-direction direction %)))
  (.repaint drawable))

(defn on-window-close [e] (reset! game-over true))

(defn on-key-press [e] (swap! direction #(case (.getKeyCode e)
                          ;left
                          37 "left"
                          ;right
                          39 "right"
                          ;down
                          40 "down"
                          ;up
                          38 "up"
                          %)))

(defn game []
  (let [window (proxy [JFrame] ["snake!"])
        drawable (proxy [JPanel] []
                   (paintComponent [g]
                     (proxy-super paintComponent g)
                     (paint-snake g @snake)))]

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
      (.setSize 200 200)
      (.add drawable)
      (.setVisible true))

    (future (loop []
              (Thread/sleep 200)
              (tick drawable @direction snake)
              (when (not @game-over) (recur))))))
