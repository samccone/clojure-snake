(ns clojure-snake.core
  (:import (javax.swing JFrame JPanel)
           (java.awt.event WindowListener KeyListener)))

(def snake (atom [[0 1] [0 2] [0 3] [0 4]]))
(def size 5)
(def game-over (atom false))

(defn move-down [snake]
  (conj (subvec snake 1) [(first (last snake)) (+ (second (last snake)) 1)]))

(defn paint-snake [g snake]
  (doall (for [block snake]
      (.fillRect g (* size (first block)) (* size (second block)) size size))))

(defn tick [drawable]
  (swap! snake move-down)
  (.repaint drawable))

(defn on-window-close [e] (swap! game-over (fn [v] true)))
(defn on-key-press [e] (println (.getKeyChar e)))

(defn game []
  (let [window (proxy [JFrame] ["snake!"])
        drawable (proxy [JPanel] []
                   (paintComponent [g]
                     (proxy-super paintComponent g)
                     (paint-snake g @snake)))]

    (.addWindowListener window (proxy [WindowListener] []
                                 (windowDeactivated [e])
                                 (windowOpened [e])
                                 (windowActivated [e])
                                 (windowClosing [e] (on-window-close e))))
    (.addKeyListener window (proxy [KeyListener] [] (keyPresses [e] (on-key-press e))))
    (.setFocusable window true)
    (.setSize window 200 200)
    (.add window drawable)
    (.setVisible window true)

    (future (loop [] (Thread/sleep 200) (tick drawable) (when (not @game-over) (recur))))))
