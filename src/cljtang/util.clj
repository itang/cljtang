(ns cljtang.util
  (:require [clojure.string :as string]
            [cljtang.core :refer :all]))

(defn find-namespaces [^String code]
  (->> (re-seq #"([\w|\\.|\\-]+)/(\w+)" code)
       (map second)
       (into #{})))

(defn ^String uuid []
  (str (java.util.UUID/randomUUID)))

(defn ^String uuid2 []
  (replace (uuid) "-" ""))

(defn ^int uuid->hash []
  (.hashCode (uuid)))

(defn ^String uuid->hash->id []
  (let [hash (when-> (uuid->hash) neg? #(bit-shift-left (- %) 1))]
    (str hash)))

(comment
(defn uid [& {:keys [length type prefix suffix]
              :or {length 6 type :number prefix "" suffix ""}}]
  (println length type prefix suffix)
  (cond = type
    :number (uuid->hash->id))))
