(ns cljtang.lang
  (:require [clojure.string :as str]))

(defn named? [obj]
  (instance? clojure.lang.Named obj))

(defn blank? [obj]
  "判定值是否为空"
  (cond
    (nil? obj) true
    (string? obj) (str/blank? obj) ;; blank?
    (char? obj) (str/blank? (str obj))
    (coll? obj) (empty? obj) ;; empty? coll
    :else false))

(defmacro if-blank [obj blk-expr els-expr]
  `(if (blank? ~obj)
     ~blk-expr
     ~els-expr))

(defmacro when-blank [obj & blk-expr]
  `(when (blank? ~obj)
     ~@blk-expr))

(defmacro blank-else [obj default-value-expr]
  `(if-blank ~obj
             ~default-value-expr
             ~obj))
