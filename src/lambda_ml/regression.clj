(ns lambda-ml.regression
  (:require [lambda-ml.core :as c]
            [clojure.math.numeric-tower :refer :all]))

(def vector-with-intercept (comp vec (partial cons 1.0)))

(defn gradient-descent-step
  "Performs a single gradient step on the model coefficients."
  [h x y alpha theta]
  (let [m (count y)
        n+1 (count (first x))
        ;; Compute gradients
        gradients (for [j (range n+1)]
                    (* (/ 1 m)
                       (apply + (map (fn [xi yi]
                                       (* (- (h xi theta) yi)
                                          (xi j)))
                                     x y))))]
    ;; Simultaneously update all thetas
    (map (fn [t g] (- t (* alpha g))) theta gradients)))

(defn gradient-descent
  "Returns a lazy sequence of estimates of the model coefficients, along with
  the cost, at each iteration of gradient descent. Takes a hypothesis function
  h, which returns a predicted value given an example and parameters, and a cost
  function j, which computes the cost of applying the current model on all
  training examples."
  ([h j x y alpha]
   (let [n+1 (count (first x))]
     (gradient-descent h j x y alpha (repeatedly n+1 rand))))
  ([h j x y alpha theta]
   (lazy-seq (let [theta (gradient-descent-step h x y alpha theta)
                   cost (j x y theta)]
               (cons [theta cost] (gradient-descent h j x y alpha theta))))))

(defn regression-fit
  "Fits a regression model to the given training data."
  ([model data]
   (regression-fit model (map butlast data) (map last data)))
  ([model x y]
   (let [{alpha :alpha iters :iterations h :hypothesis j :cost} model
         x+intercepts (map vector-with-intercept x)
         estimates (gradient-descent h j x+intercepts y alpha)
         [theta cost] (nth estimates iters)]
     (-> model
         (assoc :parameters theta)
         (assoc :costs (map second (take iters estimates)))))))

(defn regression-predict
  "Predicts the values of example data using a regression model."
  [model x]
  (let [{theta :parameters h :hypothesis} model]
    (when (not (nil? theta))
      (->> x
           (map vector-with-intercept)
           (map (partial h theta))))))

;; Linear regression

(defn linear-regression-hypothesis
  [xi theta]
  (c/dot-product xi theta))

(defn linear-regression-cost
  [x y theta]
  (let [m (count y)]
    (/ (apply + (map (fn [xi yi]
                       (expt (- (linear-regression-hypothesis xi theta) yi) 2))
                     x y))
       (* 2 m))))

(defn make-linear-regression
  "Returns a linear regression model with the given parameters."
  [alpha iters]
  {:alpha alpha
   :iterations iters
   :hypothesis linear-regression-hypothesis
   :cost linear-regression-cost})

;; Logistic regression

(defn logistic-regression-hypothesis
  [xi theta]
  (c/sigmoid (c/dot-product xi theta)))

(defn logistic-regression-cost
  [x y theta]
  (let [m (count y)]
    (/ (apply + (map (fn [xi yi]
                       (let [hi (logistic-regression-hypothesis xi theta)]
                         (+ (* yi
                               (Math/log hi))
                            (* (- 1 yi)
                               (Math/log (- 1 hi))))))
                     x y))
       (- m))))

(defn make-logistic-regression
  "Returns a logistic regression model with the given parameters."
  [alpha iters]
  {:alpha alpha
   :iterations iters
   :hypothesis logistic-regression-hypothesis
   :cost logistic-regression-cost})