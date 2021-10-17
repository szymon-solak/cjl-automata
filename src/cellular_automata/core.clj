(ns cellular-automata.core
  (:gen-class)
  (:require
   [cellular-automata.rules :as rules]
   [cellular-automata.renderers :as renderers]))

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
    (Thread/sleep 50)
    (recur (process-turn next-grid rule))))

(def gosper-glider-gun [[0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0]
                        [0 1 1 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 1 1 0 0 0 0 0 0 0 0 1 0 0 0 1 0 1 1 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 1 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
                        [0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]])

(defn -main
  [& args]

  ;; Show initial pattern, wait for any input to start
  (let [window (renderers/gui-create-window 1200 768)]
    (renderers/gui-draw window gosper-glider-gun)
    (read-line))

  ;; Run the pattern in a gui
  (let [window (renderers/gui-create-window 1200 768)]
    ((run-automata gosper-glider-gun rules/conway (partial renderers/gui-draw window))
     (renderers/gui-destroy-window window)))

  ;; Run the pattern in a terminal
  ;; (run-automata gosper-glider-gun rules/conway renderers/console)
  )
