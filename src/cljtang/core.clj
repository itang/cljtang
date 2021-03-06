(ns cljtang.core
  (:import java.util.Date
           java.text.SimpleDateFormat
           [java.io PrintWriter StringWriter]))

(defn named?
  "instance of clojure.lang.Named?"
  [obj]
  (instance? clojure.lang.Named obj))

(defn each
  "each: like doseq with callback"
  [a1 a2]
  (let [[f coll] (if (fn? a1) [a1 a2] [a2 a1])]
    (doseq [e coll] (f e))))

(def ^{:doc "alias each"} doeach each)

(defn domap
  "eager map"
  [a1 a2]
  (let [[f coll] (if (fn? a1) [a1 a2] [a2 a1])]
    (->> coll (map f) doall)))

(defn abs
  "as Math/abs"
  [x]
  (if (neg? x) (- x) x))

(def ^{:dynamic true
       :doc "default date pattern for date format"}
  *default-date-pattern* "yyyy-MM-dd HH:mm:ss")

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
    (map-indexed #(assoc %2 noname (+ start %1)) coll))

(comment
(defn maplist-with-no
  "给集合项添加序号"
  [coll & {:keys [start noname] :or {start 1 noname :no}}]
    (map #(assoc %1 noname %2)
         coll
         (range start (-> coll count (+ start)))))
)

(defmacro if-nil
  "Evaluates test. If nil, evaluates and returns then expr,
  otherwise else expr, if supplied, else nil."
  ([test then] `(if-nil ~test ~then nil))
  ([test then else]
   `(if (nil? ~test) ~then ~else)))

(defmacro if-not-nil
  "Evaluates test. If not nil, evaluates and returns then expr,
  otherwise else expr, if supplied, else nil."
  ([test then] `(if-not-nil ~test ~then nil))
  ([test then else]
   `(if-not (nil? ~test) ~then ~else)))

(defmacro when-nil
  "Evaluates test. If nil, evaluates body in an implicit do."
  [test & body]
  (list 'if-nil test (cons 'do body)))

(defmacro when-not-nil
  "Evaluates test. If not nil, evaluates body in an implicit do."
  [test & body]
  (list 'if-not-nil test (cons 'do body)))

(defn not-nil?
  "comp not nil?"
  [x]
  (not (nil? x)))

(defn wfn?
  "fn? or keyword?"
  [x]
  (or (fn? x) (keyword? x)))

(defmacro when->
  "like (test ? x : then)"
  [x test then]
  `(let [~'x ~x
         ~'t ~test
         ~'t (if (wfn? ~'t)
              (~'t ~'x)
              ~'t)]
     (if ~'t
       (let [~'then ~then]
         (if (fn? ~'then)
           (~'then ~'x)
           ~'then))
       ~'x)))

(defmacro when-not->
  "like (!test ? x : then)"
  [x test then]
  `(let [~'test ~test
         ~'test (if (wfn? ~'test)
                 (complement ~'test)
                 (not ~'test))]
     (when-> ~x ~'test ~then)))

(defmacro nil->
  "like (x==nil ? then : x)"
  [x then]
  `(when-> ~x nil? ~then))

(defmacro not-nil->
  "like (x != nil ? then : x"
  [x then]
  `(when-> ~x (comp not nil?) ~then))

(defmacro fn-*
  "constantly macro version"
  [& body]
  `(fn [& ~'args] ~@body))

(defn mixin-ns
  "merge s' public var to *ns*"
  [s]
  (let [ps (ns-publics s)]
    (doseq [[k v] ps]
      (intern *ns* k (var-get v)))))

(defmacro defdynamic [name init & {:keys [after-set]}]
  (let [sname (str "*" name "*")
        getname (str "get-" name)
        setname (str "set-" name "!")]
    `(do
       (def ~(with-meta (symbol sname) (assoc (meta name) :dynamic true)) ~init)
       (defn ~(symbol getname) [] ~(symbol sname))
       (defn ~(symbol setname) [~'it]
         (alter-var-root #'~(symbol sname) (constantly ~'it))
         (do ~after-set)))))
