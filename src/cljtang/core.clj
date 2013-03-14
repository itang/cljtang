(ns cljtang.core
  (:import java.util.Date
           java.text.SimpleDateFormat
           [java.io PrintWriter StringWriter]))

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
          ;; OPTIMIZE 区分 java imports
          (require sname :reload)
          (require sname))))
    (load-string code)))

(defn more-args->map [more-array]
  (cond
    (nil? more-array) nil
    (and (= 1 (count more-array))
         (map? (first more-array))) (first more-array)
    :else (apply hash-map more-array)))

(defn map->kv-pairs
  "将Map转换为Key-Value Pairs"
  [m & [fv]]
  (for [[k v] m] {:key k 
                  :value (if fv (fv k v) v)}))

(defn maplist-with-no 
  ([coll] (maplist-with-no coll :no))
  ([coll noname]
    (map #(assoc %1 noname %2)
         coll
         (range 1 (-> coll count inc)))))

(def ^:dynamic *default-date-pattern* "yyyy-MM-dd HH:mm:ss")

(defn format-date
  "格式化日期"
  ([^Date date]
     (format-date date *default-date-pattern*))
  ([^Date date ^String pattern]
     (.format (SimpleDateFormat. pattern) date)))

(defn moment
  "当前时间"
  []
  (java.util.Date.))

(defn moment-format
  "当前时间格式化"
  [& args]
  (apply format-date (moment) args))

(defn stacktrace->string
  "获取异常栈信息"
  [^Throwable throwable]
  (let [sw (StringWriter.)
        pw (PrintWriter. sw)]
    (.printStackTrace throwable pw)
    (str sw)))

(defn substring [^String s start len]
  (if (or (empty? s)
          (<= len 0)
          (>= start (count s) ))
    ""
    (let [end
          (min (count s) (+ start len))]
      (subs s start end))))