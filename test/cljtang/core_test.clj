(ns cljtang.core-test
  (:use clojure.test
        cljtang.core)
  (:import java.text.SimpleDateFormat
           java.util.Date))

(deftest core-test
  (testing "format-date"
    (is (= (format-date (Date.))
           (.format (SimpleDateFormat. "yyyy-MM-dd HH:mm:ss") (Date.))))
    (is (= (format-date (Date.) "yyyy-MM-dd")
           (.format (SimpleDateFormat. "yyyy-MM-dd") (Date.))))
    (is (thrown? Exception (format-date)))
    (is (thrown? Exception (format-date (Date.) "bad-pattern")))))