(ns cellular-automata.grid-test
  (:require [cellular-automata.grid :refer [get-alive-neightbours]]
            [clojure.test :refer [deftest testing is]]))

(def test-grid [[0 1 1 0 1 1]
                [1 1 0 0 0 1]
                [1 1 0 0 0 0]
                [0 0 0 1 0 0]
                [0 1 0 0 0 1]
                [1 1 1 0 0 0]])

(deftest get-alive-neightbours-test
  (testing "inside the grid"
    (is (= (get-alive-neightbours test-grid 2 2) 3))
    (is (= (get-alive-neightbours test-grid 4 4) 2)))
  (testing "edges of the grid"
    (is (= (get-alive-neightbours test-grid 0 0) 3))
    (is (= (get-alive-neightbours test-grid 0 2) 2))
    (is (= (get-alive-neightbours test-grid 5 5) 1))))
