(ns cellular-automata.core-test
  (:require [clojure.test :refer :all]
            [cellular-automata.core :refer :all]))

(deftest generate-grid-test
  (testing "generates simplest square grid"
    (is (= (cellular-automata.core/generate-grid 1 1) [[#{}]]))))
(testing "generates bigger square grid"
  (is (= (cellular-automata.core/generate-grid 4 4)
         [[#{} #{} #{} #{}]
          [#{} #{} #{} #{}]
          [#{} #{} #{} #{}]
          [#{} #{} #{} #{}]])))
(testing "generates grid when width is bigger than height"
  (is (= (cellular-automata.core/generate-grid 3 1) [[#{} #{} #{}]])))
(testing "generates grid when height is bigger than width"
  (is (= (cellular-automata.core/generate-grid 1 3) [[#{}] [#{}] [#{}]])))
