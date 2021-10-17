(ns cellular-automata.core
  (:gen-class)
  (:require
   [cellular-automata.rules :as rules]
   [cellular-automata.renderers :as renderers]
   [cellular-automata.patterns :as patterns]))

(defn process-turn [grid rule]
  (vec
   (map-indexed
    (fn [row-idx row]
      (vec
       (map-indexed
        (fn [col-idx _col] (rule grid row-idx col-idx))
        row)))
    grid)))

(defn run-automata [grid rule render]
  (loop [next-grid grid]
    (render next-grid)
    (Thread/sleep 200)
    (recur (process-turn next-grid rule))))

(defn -main
  [& args]

  ;; Run the pattern in a gui
  (let [window (renderers/gui-create-window 1200 768)]
    ((run-automata patterns/glider rules/conway (partial renderers/gui-draw window))
     (renderers/gui-destroy-window window)))

  ;; Run the pattern in a terminal
  ;; (run-automata gosper-glider-gun rules/conway renderers/console)
  )
