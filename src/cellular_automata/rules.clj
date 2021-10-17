(ns cellular-automata.rules
  (:require
   [cellular-automata.grid :as grid]))

(defn conway [grid row col]
  (let [neightbours (grid/get-alive-neightbours grid row col)
        cell (get-in grid (vec [row col]))]
    (cond
      (and (or (= 2 neightbours) (= 3 neightbours)) (= cell 1)) 1
      (and (= 3 neightbours) (= cell 0)) 1
      (= cell 1) 0
      (= cell 0) 0)))
