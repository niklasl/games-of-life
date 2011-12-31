(ns gameoflife.test.core
  (:use [gameoflife.core])
  (:use [clojure.test]))

(deftest should-update-map-state
         (let [map-state (update-map-state {} {:x 0 :y 0})]
           (is (= ((map-state -1) -1) [nil 1]))
           (is (= ((map-state 0) -1) [nil 1]))
           (is (= ((map-state 1) -1) [nil 1]))))

(deftest should-get-map-state
         (let [map-state (get-map-state [{:x 0 :y 0}])]
           (is (= ((map-state -1) -1) [nil 1]))))

(deftest should-find-next-alive
         (is (= (count (find-next-alive {-1 {-1 [nil 1]}})) 0))
         (is (= (count (find-next-alive {-1 {-1 [true 2]}})) 1)))

(deftest should-get-next-tick
         (is (= (count (next-tick [{:x 0 :y 0}])) 0)),
         (is (= (count (next-tick [{:x 0 :y 0} {:x 1 :y 0} {:x 2 :y 0}])) 3)))

; Any live cell with fewer than two live neighbours dies, as if caused by under-population.

; Any live cell with two or three live neighbours lives on to the next generation.

; Any live cell with more than three live neighbours dies, as if by overcrowding.

; Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

