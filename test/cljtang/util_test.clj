(ns cljtang.util-test
  (:use clojure.test
        cljtang.util))

(deftest find-namespaces-test
  (is (= #{"cljtang.core-test"}
         (find-namespaces "cljtang.core-test/func 1 2 3")))
  (is (= #{"cljtang.core-test"
           "cljtang.core"}
         (find-namespaces
           (str "cljtang.core-test/func"
                " 1 2 3 "
                "(cljtang.core/find-namespaces \"\")")))))

(deftest uuid-test
  (is (= 36 (count (uuid))))
  (is (= 32 (count (uuid :simplify))))
  (is (number? (uuid :hash)))
  (is (< 0 (Long/parseLong (uuid :hash-id)))))

(deftest repeat-str-test
  (is (= "" (repeat-str -1 "*")))
  (is (= "" (repeat-str 0 "*")))
  (is (= "*" (repeat-str 1 "*")))
  (is (= "**" (repeat-str 2 "*")))
  (is (= "====" (repeat-str 2 "=="))))
