(ns xarxa.core
  (:gen-class)
  (:refer-clojure :exclude [* - + == /])
  (:use clojure.core.matrix)
  (:use clojure.core.matrix.operators))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (identity-matrix 4))
  (println
   (+ [[1 2]
       [3 4]]
      (* (identity-matrix 2) 3.0))))
