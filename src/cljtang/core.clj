(ns cljtang.core
  (:import java.util.Date
           java.text.SimpleDateFormat
           [java.io PrintWriter StringWriter]))

(defn named? [obj]
  (instance? clojure.lang.Named obj))

(defn each [a1 a2]
  (let [[f coll] (if (fn? a1) [a1 a2] [a2 a1])]
    (doseq [e coll] (f e))))

(def doeach each)

(defn domap [a1 a2]
  (let [[f coll] (if (fn? a1) [a1 a2] [a2 a1])]
    (->> coll (map f) doall)))

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

(defn substring
  "截取字符串"
  [^String s start len]
  (if (or (empty? s)
          (<= len 0)
          (>= start (count s) ))
    ""
    (let [end
          (min (count s) (+ start len))]
      (subs s start end))))

(defn more-args->map
  "将‘配置参数’转换为map"
  [more-array]
  (cond
    (empty? more-array) {}
    (and (= 1 (count more-array))
         (map? (first more-array))) (first more-array)
    :else (apply hash-map more-array)))

(defn map->kv-pairs
  "将Map转换为Key-Value Pairs"
  [m & [fv]]
  (for [[k v] m] {:key k 
                  :value (if fv (fv k v) v)}))

(defn maplist-with-no
  "给集合项添加序号"
  [coll & {:keys [start noname] :or {start 1 noname :no}}]
    (map #(assoc %1 noname %2)
         coll
         (range start (-> coll count (+ start)))))
