(ns cljtang.runtime-test
  (:use clojure.test
        cljtang.runtime))

(deftest pid-test
  (is (not (nil? (pid)))))
