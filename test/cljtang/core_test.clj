(ns cljtang.core-test
  (:use clojure.test
        cljtang.core)
  (:import java.text.SimpleDateFormat
           java.util.Date))

(deftest named?-test
  (testing "named?"
           (is (true? (named? :name)))
           (is (true? (named? 'name)))
           (is (false? (named? 1)))
           (is (false? (named? true)))
           (is (false? (named? [1 2])))))

(defn- func [a & more]
  (more-args->map more))

(deftest more-args->map-test
  (testing "2 more args"
           (is (= {:name "itang"}
                  (func "one" :name "itang")))
           (is (= {"name" "itang"}
                  (func "one" "name" "itang")))
           (is (= {1 true}
                  (func "one" 1 true))))
  (testing "more args"
           (is (= {:name "itang" :password "test"}
                  (func "one" :name "itang" :password "test"))))
  (testing "more args is map"
           (is (= {:name "itang"}
                  (func "one" {:name "itang"}))))
  (testing "bad more args"
           (is (thrown? Exception (func "one" :name)))
           (is (thrown? Exception (func "one" :one :two :three)))))

(deftest map->kv-pairs-test
  (testing "map->kv-pairs"
           (is (= '({:key :name :value "itang"})
                  (map->kv-pairs {:name "itang"})))
           (is (= '({:key :name :value "itang-"})
                  (map->kv-pairs {:name "itang"}
                                 (fn [k v] (str v "-")))))))

(deftest format-date-test
  (testing "format-date"
    (let [now (Date.)]
      (is (= (format-date now)
             (.format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") now)))
      (is (= (format-date now "yyyy-MM-dd")
             (.format (SimpleDateFormat. "yyyy-MM-dd") now)))
      (is (thrown? Exception (format-date)))
      (is (thrown? Exception (format-date (Date.) "bad-pattern"))))))

(deftest maplist-with-no-test
  (is (= []
         (maplist-with-no [])))
  (is (= []
         (maplist-with-no nil)))
  (is (= [{:no 1 :name "itang"}]
         (maplist-with-no [{:name "itang"}])))
  (is (= [{:index 1 :name "itang"}]
         (maplist-with-no [{:name "itang"}] :noname :index)))
  (is (= [{:no 1 :name "itang"} {:no 2 :name "tqibm"}]
         (maplist-with-no [{:name "itang"} {:name "tqibm"}])))
  (is (= [{:no 2 :name "itang"} {:no 3 :name "tqibm"}]
         (maplist-with-no [{:name "itang"} {:name "tqibm"}] :start 2))))

(deftest substring-test
  (is (= ""
         (substring nil 1 2)))
  (is (= ""
         (substring "" 1 2)))
  (is (= ""
         (substring "hello" 1 0)))
  (is (= ""
         (substring "hello" 2 0)))

  (is (= ""
         (substring "hello" 5 3)))
  (is (= ""
         (substring "hello" 6 1)))

  (is (= "hello"
         (substring "hello" 0 5)))

  (is (= "ello"
         (substring "hello" 1 4)))
  (is (= "el"
         (substring "hello" 1 2)))
  (is (= "ell"
         (substring "hello" 1 3)))

  (is (= "o"
         (substring "hello" 4 1)))
  (is (= "o"
         (substring "hello" 4 4)))

  (is (= "ello"
         (substring "hello" 1 5))))

(deftest if-nil-test
  (is (= "a" (if-nil nil "a" "b")))
  (is (= "b" (if-nil true "a" "b")))
  (is (= "b" (if-nil "some" "a" "b")))
  (is (= "b" (if-nil [] "a" "b"))))

(deftest if-not-nil-test
  (is (= "b" (if-not-nil nil "a" "b")))
  (is (= "a" (if-not-nil true "a" "b")))
  (is (= "a" (if-not-nil "some" "a" "b")))
  (is (= "a" (if-not-nil [] "a" "b"))))

(deftest when-nil-test
  (is (= "b" (when-nil nil "a" "b")))
  (is (nil? (when-nil true "a" "b")))
  (is (nil? (when-nil "some" "a" "b")))
  (is (nil? (when-nil [] "a" "b"))))

(deftest when-not-nil-test
  (is (nil? (when-not-nil nil "a" "b")))
  (is (= "b" (when-not-nil true "a" "b")))
  (is (= "b" (when-not-nil "some" "a" "b")))
  (is (= "a" (when-not-nil [] "a"))))

(deftest not-nil?-test
  (is (not-nil? []))
  (is (not-nil? true))
  (is (not-nil? false))
  (is (not-nil? ""))
  (is (not-nil? :some))
  (is (false? (not-nil? nil))))

(deftest when->-test
  (is (= "some"
        (when-> nil nil? "some")))
  (is (= "somemore"
        (when-> nil nil? (do "some" "somemore"))))
  (is (= "tqibm"
        (when-> "itang" (partial = "itang") "tqibm")))
  (is (= 10
        (when-> -10 neg? 10)))
  (is (= 10
        (when-> 10 neg? 100)))
  (is (= -9
        (when-> -10 neg? inc)))
  (is (= 10
        (when-> 10 neg? inc)))
  (is (= "hao-itang"
        (when-> "itang" (partial = "itang") #(str "hao-" %)))))

(deftest when-not->-test
  (is (= nil
        (when-not-> nil nil? "some")))
  (is (= nil
        (when-not-> nil nil? (do "some" "somemore"))))
  (is (= "tqibm"
        (when-not-> "itang" (partial not= "itang") "tqibm")))
  (is (= 10
        (when-not-> -10 pos? 10)))
  (is (= 10
        (when-not-> 10 pos? 100)))
  (is (= -9
        (when-not-> -10 pos? inc)))
  (is (= 10
        (when-not-> 10 pos? inc)))
  (is (= "hao-itang"
        (when-not-> "itang" (partial not= "itang") #(str "hao-" %))))
  (is (= {:p "itang" :p1 "hao-itang"}
         (when-not-> {:p "itang"} :p1
                     #(assoc % :p1 (str "hao-" (:p %)))))))

(deftest nil->test
  (is (= "itang" (nil-> nil "itang")))
  (is (= "tqibm" (nil-> "tqibm" "itang"))))

(deftest not-nil->test
  (is (= nil (not-nil-> nil "itang")))
  (is (= "itang" (not-nil-> inc "itang")))
  (is (= "itang" (not-nil-> (inc 1) "itang")))
  (is (= "itang" (not-nil-> "tqibm" "itang"))))

(deftest fn-*-test
  (is (="itang" ((fn-* (str "itan" "g"))))))
