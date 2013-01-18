(ns cljtang.core
  (:import java.util.Date
           java.text.SimpleDateFormat))

(defn find-namespaces [^String code]
  (->> (re-seq #"([\w|\\.|\\-]+)/(\w+)" code)
       (map second)
       (into #{}) ;(apply hash-set)
       ))

(defn- wrap-code [^String code]
  (if (.startsWith code "(")
    code
    (str "(" code ")")))

(defn eval-str [^String code & [opts]]
  (let [code (wrap-code code)
        namespaces (find-namespaces code)]
    (doseq [name namespaces]
      (let [reload? (get opts :reload false)
            sname (symbol name)]
        (if reload?
          (require sname :reload)
          (require sname))))
    (load-string code)))

(def ^:dynamic *default-date-pattern* "yyyy-MM-dd HH:mm:ss")

(defn format-date
  "格式化日期"
  ([^Date date]
     (format-date date *default-date-pattern*))
  ([^Date date ^String pattern]
     (.format (SimpleDateFormat. pattern) date)))

