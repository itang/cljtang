(ns cljtang.util)

(defn find-namespaces [^String code]
  (->> (re-seq #"([\w|\\.|\\-]+)/(\w+)" code)
       (map second)
       (into #{})))
