(ns xarxa.core
  (:gen-class)
  (:refer-clojure :exclude [* - + == /])
  (:use clojure.core.matrix)
  (:use clojure.core.matrix.operators))


;; -Neurons-
;;
;;  Input  Hidden  Output
;;  I1     H1      O1
;;  I1     H2      O2
;;         H3
;;
;; -Connection Strengths-
;;
;; Input  -> Hidden
;;   [[I1-H1 I1-H2 I1-H3]
;;    [I2-H1 I2-H2 I2-H3]]
;;
;; Hidden -> Output
;;   [[H1-O1 H1-O2]
;;    [H2-O1 H2-O2]
;;    [H3-O1 H3-O2]]
;;

(def in-neurons [1 0])
(def ih-synapses [[0.12 0.20 0.13]
                  [0.01 0.02 0.03]])
(def hid-neurons [0 0 0])
(def ho-synapses [[0.15 0.16]
                  [0.02 0.03]
                  [0.01 0.02]])

;; The new value of neuron in the hidden layer:
;; the sum of all the inputs of its connections
;; multiplied by the synapse connection strengths.

(defn activate [x]
  "Output in (range (-1) 1)"
  (Math/tanh x))
(defn deriv-activate [y]
  "Derivative of activate."
  (- 1.0 (* y y)))

;;
;; When multiplying matrices,
;; the elements of the rows in the first matrix
;; are multiplied with
;; the corresponding columns in the second matrix.
;;
;; Treating the rows and columns in each matrix as row and column
;; vectors respectively, this entry is also their vector dot product,
;; i.e. the sum of the products of each corresponding pair.
;;

(defn activate-layer
  "Forward propagate the input of a layer.
     neurons: 1xM matrix (i.e. row vector) of neuron values
     weights: MxP matrix of synapse connection strengths
   Return: 1xP vector of output neuron values.

   The new value of neuron is the sum of all the inputs of its connections
   multiplied by the connection strength."
  [neurons weights]
  ;; For each output neuron, apply the activation fn.
  (mapv activate
        ;; For each output neuron, sum up its input vals * weights.
        (mapv #(reduce + %)
              ;; clojure.core.matrix.operators/* -> element-wise multiplication.
              ;; For each output neuron, compute vector by multiplying:
              ;;   each input neuron's value -by-
              ;;   its connection weight to the output nueuron.
              (* neurons (transpose weights)))))

(defn ppm [m]
  (pm (matrix m)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (ppm (* [[1 2]]
          [[3 5]]))

  (ppm (* [[1 2 3]
           [2 1 1]]
          (transpose
           [[2 1]
            [4 2]
            [3 5]])))

  (ppm (+ [[1 2]
           [3 4]]
          [[4 10]
           [2 3]]))

  (ppm (+ [[1 2]
           [3 4]]
          (* (identity-matrix 2) 3.0))))
