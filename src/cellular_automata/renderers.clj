(ns cellular-automata.renderers
  (:require
   [clojure.string :as string]))

(defn print-grid [grid] (println (string/join \newline grid)))

(defn console [grid]
  (println "\n\n")
  (print-grid grid))
