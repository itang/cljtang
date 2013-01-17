(ns cljtang.lang-test
  (:use clojure.test
        cljtang.lang))

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
