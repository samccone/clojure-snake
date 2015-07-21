(ns clojure-snake.core
  (:import (javax.swing JFrame JPanel)
           (java.awt.event WindowListener)))

(def snake (atom [[0 1] [0 2] [0 3] [0 4]]))
(def size 5)
(def gameOver (atom false))

(defn moveDown [snake]
  (conj (subvec snake 1) [(first (last snake)) (+ (second (last snake)) 1)]))

(defn paintSnake [g snake]
  (doall (for [block snake]
      (.fillRect g (* size (first block)) (* size (second block)) size size))))

(defn tick [drawable]
  (swap! snake moveDown)
  (.repaint drawable))

(defn game []
  (let [window (proxy [JFrame] ["snake!"])
        drawable (proxy [JPanel] []
                   (paintComponent [g]
                     (proxy-super paintComponent g)
                     (paintSnake g @snake)))]

    (.addWindowListener window (proxy [WindowListener] []
                          (windowClosing [e] (println "all done") (swap! gameOver (fn [v] false)))))
    (.setSize window 200 200)
    (.add window drawable)
    (.setVisible window true)

    (loop []
      (when (not @gameOver)
        (println "tick")
        (. Thread sleep 1000)
        (tick drawable)
        (recur)))))
