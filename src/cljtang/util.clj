(ns cljtang.util
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
  [& {:keys [simplify] :or {simplify false}}]
  (let [ret (str (java.util.UUID/randomUUID))]
    (if simplify
      (string/replace ret "-" "")
      ret)))

(defn ^int uuid->hash
  "uuid to hash code"
  []
  (.hashCode (uuid)))

(defn ^String uuid->hash->id
  "uuid to hash code, as id: Unsigned Int"
  []
  (let [hash (when-> (uuid->hash) neg? #(bit-shift-left (- %) 1))]
    (str hash)))

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
