(ns cljtang.core-test
  (:use clojure.test
        cljtang.core)
  (:import java.text.SimpleDateFormat
           java.util.Date))

(deftest find-namespaces-test
  (testing "find-namespaces"
           (is (= #{"cljtang.core-test"}
                  (find-namespaces "cljtang.core-test/func 1 2 3")))
           (is (= #{"cljtang.core-test"
                    "cljtang.core"}
                  (find-namespaces 
                    (str "cljtang.core-test/func"
                         " 1 2 3 "
                         "(cljtang.core/find-namespaces \"\")"))))))

(defn func-for-eval-test [& more] more)

(deftest eval-str-test
  (testing "eval-str"
           (is (= '(1 2 3)
                  (eval-str "cljtang.core-test/func-for-eval-test 1 2 3")))
           (is (= '(1 2 3 #{"cljtang.core-test"})
                  (eval-str 
                    (str "cljtang.core-test/func-for-eval-test"
                         " 1 2 3 "
                         "(cljtang.core/find-namespaces "
                         "\"cljtang.core-test/func-for-eval-test 1 2 3\")"))))))

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
         (maplist-with-no [{:name "itang"}] :index)))
  (is (= [{:no 1 :name "itang"} {:no 2 :name "tqibm"}]
         (maplist-with-no [{:name "itang"} {:name "tqibm"}]))))

(deftest substring-test
  (is (= "hello"
         (substring "hello" 0 5)))
  (is (= ""
         (substring "hello" 1 0)))
  (is (= ""
         (substring "hello" 2 0)))
  (is (= "ello"
         (substring "hello" 1 4)))
  (is (= "el"
         (substring "hello" 1 2)))
  (is (= "ell"
         (substring "hello" 1 3)))
  (is (= ""
         (substring "hello" 5 3)))
  (is (= ""
         (substring "hello" 6 1)))
  (is (= "o"
         (substring "hello" 4 1)))
  (is (= "o"
         (substring "hello" 4 4)))
  (is (= "ello"
         (substring "hello" 1 5))))
