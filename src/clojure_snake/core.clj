(ns clojure-snake.core
  (:import (javax.swing JFrame JPanel)))

(def snake [0 1 0 2 0 3 0 4])


(comment WIP make work lol
  (defn paintSnake [g snake]
    (let [size 5]
      (.fillRect g (* size x) (* y size) size size)))

(do
  (let [window (JFrame. "snake!")
        drawable (proxy [JPanel] []
                     (paintComponent [g]
                       (paintSnake g snake)))]

    (.setSize window 200 200)
    (.add window drawable)
    (.setVisible window true)))


