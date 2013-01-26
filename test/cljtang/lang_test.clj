(ns cljtang.lang-test
  (:use clojure.test
        cljtang.lang))

(deftest named?-test
  (testing "named?"
           (is (true? (named? :name)))
           (is (true? (named? 'name)))
           (is (false? (named? 1)))
           (is (false? (named? true)))
           (is (false? (named? [1 2])))))

(deftest if-blank-test
  (testing "if-blank"
           (is (= "a" (if-blank nil "a" "b")))
           (is (= "a" (if-blank {} "a" "b")))
           (is (= "a" (if-blank [] "a" "b")))
           (is (= "a" (if-blank "" "a" "b")))
           (is (= "a" (if-blank "   " "a" "b")))
           (is (= "a" (if-blank \newline "a" "b")))))

(deftest when-blank-test
  (testing "when-blank"
           (is (= "b" (when-blank nil "a" "b")))
           (is (= "a" (when-blank {} "a")))
           (is (= "b" (when-blank [] "a" "b")))
           (is (= "b" (when-blank "" "a" "b")))
           (is (= "b" (when-blank "   " "a" "b")))
           (is (= nil (when-blank "some" "a" "b")))))
