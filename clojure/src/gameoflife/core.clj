(ns gameoflife.core)

(defn update-map-state [m {x :x y :y}]
  (let [inc-nbs (fn [[alive nbs]] [alive (+ (or nbs 0) 1)])
        upd #(update-in %1 [(+ y %2) (+ x %3)] inc-nbs)
        ; row-1
        m (upd m -1 -1)
        m (upd m -1 0)
        m (upd m -1 1)
        ; row-2
        m (upd m 0 -1)
        m (update-in m [y x] (fn [[alive nbs]] [true nbs]))
        m (upd m 0 1)
        ; row-3
        m (upd m 1 -1)
        m (upd m 1 0)
        m (upd m 1 1)]
    m))

(defn get-map-state [cells]
  (reduce update-map-state {} cells))

(defn next-alive [[alive nbs]]
  (or (and alive (= nbs 2)) (= nbs 3)))

(defn find-next-alive [map-state]
  (mapcat
    #(for [col (val %1) :when (next-alive (val col))]
      {:x (key col) :y (key %1)}) map-state))

(defn paint-map [map-state]
  (doseq [[y row] (sort map-state)]
    (doseq [[x [alive nbs]] (sort row)]
      (print (if alive "*" " ")))
    (println))
  (println)
  map-state)

(defn next-tick [cells]
  (find-next-alive (paint-map (get-map-state cells))))

; (do (use 'gameoflife.core :reload) (-main))
(defn -main [& args]
  (-> [{:x 1 :y 0} {:x 2 :y 0} {:x 3 :y 0}
       {:x 0 :y 1} {:x 1 :y 1} {:x 2 :y 1}]
    next-tick
    next-tick
    next-tick
    next-tick
    next-tick) nil)

