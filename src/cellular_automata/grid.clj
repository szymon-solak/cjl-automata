(ns cellular-automata.grid)

(defn permutations [a b] (for [x a y b] (vector x y)))

(defn get-neightbour-coords [grid row col] (filter
                                            (fn [pair] (not= pair (vec [row col])))
                                            (permutations
                                             (filter
                                              #(-> (> (count grid) %))
                                              (filter nat-int? [(dec row) row (inc row)]))
                                             (filter
                                              #(-> (> (count (first grid)) %))
                                              (filter nat-int? [(dec col) col (inc col)])))))

(defn get-alive-neightbours [grid row col] (reduce
                                            +
                                            (map
                                             (fn [coords] (get-in grid coords))
                                             (vec (get-neightbour-coords grid row col)))))
