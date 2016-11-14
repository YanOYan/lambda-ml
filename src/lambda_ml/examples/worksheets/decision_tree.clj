;; gorilla-repl.fileformat = 1

;; **
;;; # Lambda ML Example: Decision Tree
;; **

;; @@
(ns lambda-ml.examples.worksheets.decision-tree
  (:require [lambda-ml.decision-tree :refer :all]
            [lambda-ml.data.binary-tree :as bt]
            [lambda-ml.metrics :refer :all]
            [gorilla-plot.core :as plot]
            :reload))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def data
  [[5.1 3.5 1.4 0.2 "Iris-setosa"]
   [4.9 3.0 1.4 0.2 "Iris-setosa"]
   [4.7 3.2 1.3 0.2 "Iris-setosa"]
   [4.6 3.1 1.5 0.2 "Iris-setosa"]
   [5.0 3.6 1.4 0.2 "Iris-setosa"]
   [5.4 3.9 1.7 0.4 "Iris-setosa"]
   [4.6 3.4 1.4 0.3 "Iris-setosa"]
   [5.0 3.4 1.5 0.2 "Iris-setosa"]
   [4.4 2.9 1.4 0.2 "Iris-setosa"]
   [4.9 3.1 1.5 0.1 "Iris-setosa"]
   [5.4 3.7 1.5 0.2 "Iris-setosa"]
   [4.8 3.4 1.6 0.2 "Iris-setosa"]
   [4.8 3.0 1.4 0.1 "Iris-setosa"]
   [4.3 3.0 1.1 0.1 "Iris-setosa"]
   [5.8 4.0 1.2 0.2 "Iris-setosa"]
   [5.7 4.4 1.5 0.4 "Iris-setosa"]
   [5.4 3.9 1.3 0.4 "Iris-setosa"]
   [5.1 3.5 1.4 0.3 "Iris-setosa"]
   [5.7 3.8 1.7 0.3 "Iris-setosa"]
   [5.1 3.8 1.5 0.3 "Iris-setosa"]
   [5.4 3.4 1.7 0.2 "Iris-setosa"]
   [5.1 3.7 1.5 0.4 "Iris-setosa"]
   [4.6 3.6 1.0 0.2 "Iris-setosa"]
   [5.1 3.3 1.7 0.5 "Iris-setosa"]
   [4.8 3.4 1.9 0.2 "Iris-setosa"]
   [5.0 3.0 1.6 0.2 "Iris-setosa"]
   [5.0 3.4 1.6 0.4 "Iris-setosa"]
   [5.2 3.5 1.5 0.2 "Iris-setosa"]
   [5.2 3.4 1.4 0.2 "Iris-setosa"]
   [4.7 3.2 1.6 0.2 "Iris-setosa"]
   [4.8 3.1 1.6 0.2 "Iris-setosa"]
   [5.4 3.4 1.5 0.4 "Iris-setosa"]
   [5.2 4.1 1.5 0.1 "Iris-setosa"]
   [5.5 4.2 1.4 0.2 "Iris-setosa"]
   [4.9 3.1 1.5 0.1 "Iris-setosa"]
   [5.0 3.2 1.2 0.2 "Iris-setosa"]
   [5.5 3.5 1.3 0.2 "Iris-setosa"]
   [4.9 3.1 1.5 0.1 "Iris-setosa"]
   [4.4 3.0 1.3 0.2 "Iris-setosa"]
   [5.1 3.4 1.5 0.2 "Iris-setosa"]
   [5.0 3.5 1.3 0.3 "Iris-setosa"]
   [4.5 2.3 1.3 0.3 "Iris-setosa"]
   [4.4 3.2 1.3 0.2 "Iris-setosa"]
   [5.0 3.5 1.6 0.6 "Iris-setosa"]
   [5.1 3.8 1.9 0.4 "Iris-setosa"]
   [4.8 3.0 1.4 0.3 "Iris-setosa"]
   [5.1 3.8 1.6 0.2 "Iris-setosa"]
   [4.6 3.2 1.4 0.2 "Iris-setosa"]
   [5.3 3.7 1.5 0.2 "Iris-setosa"]
   [5.0 3.3 1.4 0.2 "Iris-setosa"]
   [7.0 3.2 4.7 1.4 "Iris-versicolor"]
   [6.4 3.2 4.5 1.5 "Iris-versicolor"]
   [6.9 3.1 4.9 1.5 "Iris-versicolor"]
   [5.5 2.3 4.0 1.3 "Iris-versicolor"]
   [6.5 2.8 4.6 1.5 "Iris-versicolor"]
   [5.7 2.8 4.5 1.3 "Iris-versicolor"]
   [6.3 3.3 4.7 1.6 "Iris-versicolor"]
   [4.9 2.4 3.3 1.0 "Iris-versicolor"]
   [6.6 2.9 4.6 1.3 "Iris-versicolor"]
   [5.2 2.7 3.9 1.4 "Iris-versicolor"]
   [5.0 2.0 3.5 1.0 "Iris-versicolor"]
   [5.9 3.0 4.2 1.5 "Iris-versicolor"]
   [6.0 2.2 4.0 1.0 "Iris-versicolor"]
   [6.1 2.9 4.7 1.4 "Iris-versicolor"]
   [5.6 2.9 3.6 1.3 "Iris-versicolor"]
   [6.7 3.1 4.4 1.4 "Iris-versicolor"]
   [5.6 3.0 4.5 1.5 "Iris-versicolor"]
   [5.8 2.7 4.1 1.0 "Iris-versicolor"]
   [6.2 2.2 4.5 1.5 "Iris-versicolor"]
   [5.6 2.5 3.9 1.1 "Iris-versicolor"]
   [5.9 3.2 4.8 1.8 "Iris-versicolor"]
   [6.1 2.8 4.0 1.3 "Iris-versicolor"]
   [6.3 2.5 4.9 1.5 "Iris-versicolor"]
   [6.1 2.8 4.7 1.2 "Iris-versicolor"]
   [6.4 2.9 4.3 1.3 "Iris-versicolor"]
   [6.6 3.0 4.4 1.4 "Iris-versicolor"]
   [6.8 2.8 4.8 1.4 "Iris-versicolor"]
   [6.7 3.0 5.0 1.7 "Iris-versicolor"]
   [6.0 2.9 4.5 1.5 "Iris-versicolor"]
   [5.7 2.6 3.5 1.0 "Iris-versicolor"]
   [5.5 2.4 3.8 1.1 "Iris-versicolor"]
   [5.5 2.4 3.7 1.0 "Iris-versicolor"]
   [5.8 2.7 3.9 1.2 "Iris-versicolor"]
   [6.0 2.7 5.1 1.6 "Iris-versicolor"]
   [5.4 3.0 4.5 1.5 "Iris-versicolor"]
   [6.0 3.4 4.5 1.6 "Iris-versicolor"]
   [6.7 3.1 4.7 1.5 "Iris-versicolor"]
   [6.3 2.3 4.4 1.3 "Iris-versicolor"]
   [5.6 3.0 4.1 1.3 "Iris-versicolor"]
   [5.5 2.5 4.0 1.3 "Iris-versicolor"]
   [5.5 2.6 4.4 1.2 "Iris-versicolor"]
   [6.1 3.0 4.6 1.4 "Iris-versicolor"]
   [5.8 2.6 4.0 1.2 "Iris-versicolor"]
   [5.0 2.3 3.3 1.0 "Iris-versicolor"]
   [5.6 2.7 4.2 1.3 "Iris-versicolor"]
   [5.7 3.0 4.2 1.2 "Iris-versicolor"]
   [5.7 2.9 4.2 1.3 "Iris-versicolor"]
   [6.2 2.9 4.3 1.3 "Iris-versicolor"]
   [5.1 2.5 3.0 1.1 "Iris-versicolor"]
   [5.7 2.8 4.1 1.3 "Iris-versicolor"]
   [6.3 3.3 6.0 2.5 "Iris-virginica"]
   [5.8 2.7 5.1 1.9 "Iris-virginica"]
   [7.1 3.0 5.9 2.1 "Iris-virginica"]
   [6.3 2.9 5.6 1.8 "Iris-virginica"]
   [6.5 3.0 5.8 2.2 "Iris-virginica"]
   [7.6 3.0 6.6 2.1 "Iris-virginica"]
   [4.9 2.5 4.5 1.7 "Iris-virginica"]
   [7.3 2.9 6.3 1.8 "Iris-virginica"]
   [6.7 2.5 5.8 1.8 "Iris-virginica"]
   [7.2 3.6 6.1 2.5 "Iris-virginica"]
   [6.5 3.2 5.1 2.0 "Iris-virginica"]
   [6.4 2.7 5.3 1.9 "Iris-virginica"]
   [6.8 3.0 5.5 2.1 "Iris-virginica"]
   [5.7 2.5 5.0 2.0 "Iris-virginica"]
   [5.8 2.8 5.1 2.4 "Iris-virginica"]
   [6.4 3.2 5.3 2.3 "Iris-virginica"]
   [6.5 3.0 5.5 1.8 "Iris-virginica"]
   [7.7 3.8 6.7 2.2 "Iris-virginica"]
   [7.7 2.6 6.9 2.3 "Iris-virginica"]
   [6.0 2.2 5.0 1.5 "Iris-virginica"]
   [6.9 3.2 5.7 2.3 "Iris-virginica"]
   [5.6 2.8 4.9 2.0 "Iris-virginica"]
   [7.7 2.8 6.7 2.0 "Iris-virginica"]
   [6.3 2.7 4.9 1.8 "Iris-virginica"]
   [6.7 3.3 5.7 2.1 "Iris-virginica"]
   [7.2 3.2 6.0 1.8 "Iris-virginica"]
   [6.2 2.8 4.8 1.8 "Iris-virginica"]
   [6.1 3.0 4.9 1.8 "Iris-virginica"]
   [6.4 2.8 5.6 2.1 "Iris-virginica"]
   [7.2 3.0 5.8 1.6 "Iris-virginica"]
   [7.4 2.8 6.1 1.9 "Iris-virginica"]
   [7.9 3.8 6.4 2.0 "Iris-virginica"]
   [6.4 2.8 5.6 2.2 "Iris-virginica"]
   [6.3 2.8 5.1 1.5 "Iris-virginica"]
   [6.1 2.6 5.6 1.4 "Iris-virginica"]
   [7.7 3.0 6.1 2.3 "Iris-virginica"]
   [6.3 3.4 5.6 2.4 "Iris-virginica"]
   [6.4 3.1 5.5 1.8 "Iris-virginica"]
   [6.0 3.0 4.8 1.8 "Iris-virginica"]
   [6.9 3.1 5.4 2.1 "Iris-virginica"]
   [6.7 3.1 5.6 2.4 "Iris-virginica"]
   [6.9 3.1 5.1 2.3 "Iris-virginica"]
   [5.8 2.7 5.1 1.9 "Iris-virginica"]
   [6.8 3.2 5.9 2.3 "Iris-virginica"]
   [6.7 3.3 5.7 2.5 "Iris-virginica"]
   [6.7 3.0 5.2 2.3 "Iris-virginica"]
   [6.3 2.5 5.0 1.9 "Iris-virginica"]
   [6.5 3.0 5.2 2.0 "Iris-virginica"]
   [6.2 3.4 5.4 2.3 "Iris-virginica"]
   [5.9 3.0 5.1 1.8 "Iris-virginica"]])
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-var'>#&#x27;lambda-ml.examples.worksheets.decision-tree/data</span>","value":"#'lambda-ml.examples.worksheets.decision-tree/data"}
;; <=

;; @@
(def model (make-classification-tree gini-impurity))
(def fit (decision-tree-fit model data))

(print-decision-tree fit)
;; @@
;; ->
;;; {:cost #function[lambda-ml.metrics/gini-impurity], :prediction #function[lambda-ml.core/mode], :weighted #function[lambda-ml.decision-tree/classification-weighted-cost]}
;;;  {:decision 2.45, :cost 0.33333334, :feature 2}
;;; 	 Iris-setosa
;;; 	 {:decision 1.75, :cost 0.11030596, :feature 3}
;;; 		 {:decision 4.95, :cost 0.08564815, :feature 2}
;;; 			 {:decision 1.65, :cost 0.0, :feature 3}
;;; 				 Iris-versicolor
;;; 				 Iris-virginica
;;; 			 {:decision 1.55, :cost 0.22222222, :feature 3}
;;; 				 Iris-virginica
;;; 				 {:decision 6.95, :cost 0.0, :feature 0}
;;; 					 Iris-versicolor
;;; 					 Iris-virginica
;;; 		 {:decision 4.85, :cost 0.028985508, :feature 2}
;;; 			 {:decision 5.95, :cost 0.0, :feature 0}
;;; 				 Iris-versicolor
;;; 				 Iris-virginica
;;; 			 Iris-virginica
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(def model (make-regression-tree mean-squared-error))
(def fit (decision-tree-fit model (map (comp vector #(nth % 1)) data) (map first data)))

(print-decision-tree fit)
;; @@
;; ->
;;; {:cost #function[lambda-ml.metrics/mean-squared-error], :prediction #function[lambda-ml.core/mean], :weighted #function[lambda-ml.decision-tree/regression-weighted-cost]}
;;;  {:decision 3.35, :cost 0.6369771, :feature 0}
;;; 	 {:decision 2.55, :cost 0.63437116, :feature 0}
;;; 		 {:decision 2.1, :cost 0.32540935, :feature 0}
;;; 			 5.0
;;; 			 {:decision 2.25, :cost 0.30688888, :feature 0}
;;; 				 6.066666666666666
;;; 				 {:decision 2.45, :cost 0.3164881, :feature 0}
;;; 					 {:decision 2.35, :cost 0.28678572, :feature 0}
;;; 						 5.325
;;; 						 5.3
;;; 					 5.7625
;;; 		 {:decision 2.95, :cost 0.6862419, :feature 0}
;;; 			 {:decision 2.75, :cost 0.39609805, :feature 0}
;;; 				 {:decision 2.65, :cost 0.29815874, :feature 0}
;;; 					 6.159999999999999
;;; 					 5.855555555555554
;;; 				 {:decision 2.85, :cost 0.4223393, :feature 0}
;;; 					 6.335714285714287
;;; 					 6.0600000000000005
;;; 			 {:decision 3.05, :cost 0.8677472, :feature 0}
;;; 				 6.015384615384614
;;; 				 {:decision 3.25, :cost 0.8785398, :feature 0}
;;; 					 {:decision 3.15, :cost 0.96984357, :feature 0}
;;; 						 5.941666666666667
;;; 						 5.884615384615383
;;; 					 6.016666666666667
;;; 	 {:decision 3.75, :cost 0.5102662, :feature 0}
;;; 		 {:decision 3.55, :cost 0.33150464, :feature 0}
;;; 			 {:decision 3.45, :cost 0.2062037, :feature 0}
;;; 				 5.316666666666666
;;; 				 5.1499999999999995
;;; 			 {:decision 3.65, :cost 0.6611111, :feature 0}
;;; 				 5.6000000000000005
;;; 				 5.266666666666667
;;; 		 {:decision 3.85, :cost 0.76666665, :feature 0}
;;; 			 6.1000000000000005
;;; 			 {:decision 4.3, :cost 0.032, :feature 0}
;;; 				 {:decision 4.05, :cost 0.030333333, :feature 0}
;;; 					 {:decision 3.95, :cost 0.0, :feature 0}
;;; 						 5.4
;;; 						 5.8
;;; 					 {:decision 4.15, :cost 0.0, :feature 0}
;;; 						 5.2
;;; 						 5.5
;;; 				 5.7
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@

;; @@
