(ns cljtang.lang
  (:require [clojure.string :as str]))

(defn more-args->map [more-array]
  (cond
    (nil? more-array) nil
    (and (= 1 (count more-array))
         (map? (first more-array))) (first more-array)
    :else (apply hash-map more-array)))

(defn empty-x? [obj]
  "判定值是否为空"
  (cond
    (nil? obj) true
    (string? obj) (str/blank? obj) ;; blank?
    (char? obj) (str/blank? (str obj))
    (coll? obj) (empty? obj) ;; empty? coll
    :else false))

(defn named? [obj]
  (instance? clojure.lang.Named obj))

(defmacro if-empty [cond ept-expr els-expr]
  `(if (empty-x? ~cond) ~ept-expr ~els-expr))

(defmacro empty-else [obj default-value-expr]
  `(if-empty ~obj ~default-value-expr ~obj))
