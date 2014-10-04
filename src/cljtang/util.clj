(ns cljtang.util
  (:import (java.util UUID))
  (:require [clojure.string :as string]
            [cljtang.core :refer :all]))

(defn find-namespaces
  "find namespace from code"
  [^String code]
  (->> (re-seq #"([\w|\\.|\\-]+)/(\w+)" code)
       (map second)
       (into #{})))

(defn ^String uuid
  "generate uuid: String"
  [& [mode]]
  (let [^String ret (str (UUID/randomUUID))]
    (case mode
      :full ret
      :simplify (string/replace ret "-" "")
      :hash (.hashCode ret)
      :hash-id (str (when-> (.hashCode ret) neg? #(bit-shift-left (- %) 1)))
      ret)))

(defn ^String repeat-str
  "returns a new string consisting of count copies of the string s"
  [n s]
  (->> s (repeat n) string/join))

(comment
  (defn uid [& {:keys [length type prefix suffix]
                :or {length 6 type :number prefix "" suffix ""}}]
    (println length type prefix suffix)
    (cond = type
          :number (uuid->hash->id))))
