(ns cellular-automata.rules-test
  (:require [cellular-automata.rules :refer [conway]]
            [clojure.test :refer [deftest testing is]]))

(deftest conway-test
  (testing "live cell with less than two neighbours dies"
    (is (= (conway [[0 0 0] [0 1 0] [0 0 0]] 1 1) 0))
    (is (= (conway [[0 1 0] [0 1 0] [0 0 0]] 1 1) 0))
    (is (= (conway [[1 0 0] [0 1 0] [0 0 0]] 1 1) 0)))

  (testing "live cell with 2-3 live neighbours lives"
    (is (= (conway [[1 1 1] [1 0 0] [0 0 0]] 0 1) 1))
    (is (= (conway [[0 0 0] [1 1 0] [1 0 0]] 2 0) 1)))

  (testing "live cell with more than 3 neighbours dies"
    (is (= (conway [[1 1 1] [1 1 1] [1 1 1]] 0 1) 0))
    (is (= (conway [[0 1 1] [1 1 1] [1 1 0]] 2 1) 0)))

  (testing "dead cell with exactly 3 neightbours becomes a live cell"
    (is (= (conway [[0 1 0] [1 0 1] [0 0 0]] 1 1) 1))
    (is (= (conway [[1 0 1] [1 0 0] [0 0 0]] 0 1) 1))))
