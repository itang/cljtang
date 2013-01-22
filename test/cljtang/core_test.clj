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

(deftest map->kv-pairs-test
  (testing "map->kv-pairs"
           (is (= '({:key :name :value "itang"})
                  (map->kv-pairs {:name "itang"})))))
   
(deftest format-date-test
  (testing "format-date"
           (is (= (format-date (Date.))
                  (.format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") (Date.))))
           (is (= (format-date (Date.) "yyyy-MM-dd")
                  (.format (SimpleDateFormat. "yyyy-MM-dd") (Date.))))
           (is (thrown? Exception (format-date)))
           (is (thrown? Exception (format-date (Date.) "bad-pattern")))))

