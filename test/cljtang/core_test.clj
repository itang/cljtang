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
                  (find-namespaces "cljtang.core-test/func 1 2 3 (cljtang.core/find-namespaces \"\")")))))

(defn func-for-eval-test [& more] more)

(deftest eval-str-test
  (testing "eval-str"
           (is (= '(1 2 3)
                  (eval-str "cljtang.core-test/func-for-eval-test 1 2 3")))
           (is (= '(1 2 3 #{"cljtang.core-test"})
                  (eval-str (str "cljtang.core-test/func-for-eval-test 1 2 3 "
                                 "(cljtang.core/find-namespaces \"cljtang.core-test/func-for-eval-test 1 2 3\")"))))))

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
           (is (= (format-date (Date.))
                  (.format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") (Date.))))
           (is (= (format-date (Date.) "yyyy-MM-dd")
                  (.format (SimpleDateFormat. "yyyy-MM-dd") (Date.))))
           (is (thrown? Exception (format-date)))
           (is (thrown? Exception (format-date (Date.) "bad-pattern")))))

