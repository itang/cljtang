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
