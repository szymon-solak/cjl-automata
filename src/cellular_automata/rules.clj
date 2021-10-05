(ns cellular-automata.rules
  (:require
   [cellular-automata.grid :as grid]))

(defn conway [grid row col] (let [neightbours (grid/get-alive-neightbours grid row col)
                                  cell (get-in grid (vec [row col]))]
                              (cond
                                (and (< neightbours 2) (= cell 1)) 0
                                (and (> neightbours 3) (= cell 1)) 0
                                (and (= neightbours 3) (= cell 0)) 1
                                (= cell 1) 1
                                :else 0)))
