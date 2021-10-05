(ns cellular-automata.core
  (:gen-class)
  (:require
   [clojure.string :as string]
   [cellular-automata.grid :refer [get-alive-neightbours]]))

(defn apply-conway-rule [grid row col] (let [neightbours (get-alive-neightbours grid row col)
                                             cell (get-in grid (vec [row col]))]
                                         (cond
                                           (and (< neightbours 2) (= cell 1)) 0
                                           (and (> neightbours 3) (= cell 1)) 0
                                           (and (= neightbours 3) (= cell 0)) 1
                                           (= cell 1) 1
                                           :else 0)))

(defn process-turn [grid rule] (vec (map-indexed
                                     (fn [row-idx row] (vec (map-indexed
                                                             (fn [col-idx _col] (rule grid row-idx col-idx))
                                                             row))) grid)))

(defn run-automata [grid rule render] (loop [next-grid (process-turn grid rule)]
                                        (render next-grid)
                                        (recur next-grid)))

(def seed [[0 1 0] [0 1 0] [0 1 0]])

(defn print-grid [grid] (println (string/join \newline grid)))

(def ESC "\033")
(defn console-renderer [grid] (printf (str ESC "[" (count grid) "A" ESC "[K")) (print-grid grid))

(defn -main
  [& args]
  (print-grid seed)
  (run-automata seed apply-conway-rule console-renderer))
