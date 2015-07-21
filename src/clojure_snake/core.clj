(ns clojure-snake.core
  (:import (javax.swing JFrame JPanel)))

(def snake (atom [[0 1] [0 2] [0 3] [0 4]]))
(def size 5)
(def running true)


(defn moveDown [snake]
  (conj (subvec snake 1) [(first (last snake)) (+ (second (last snake)) 1)]))

(defn paintSnake [g snake]
  (doall (for [block snake]
      (.fillRect g (* size (first block)) (* size (second block)) size size))))

(do
  (let [window (JFrame. "snake!")
        drawable (proxy [JPanel] []
                   (paintComponent [g]
                     (proxy-super paintComponent g)
                     (paintSnake g @snake)))]

    (.setSize window 200 200)
    (.add window drawable)
    (.setVisible window true)

    (loop []
        (. Thread sleep 1000)
        (swap! snake moveDown))
        (.repaint drawable)
        (recur))))


