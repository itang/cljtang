(ns cljtang.runtime
  (:import [java.lang.management ManagementFactory RuntimeMXBean])
  (:require [clojure.string :as string]))

(defn pid
  "当前JVM进程id"
  []
  (let [^RuntimeMXBean runtime-mxbean (ManagementFactory/getRuntimeMXBean)
        name (.getName runtime-mxbean)]
    (-> name (string/split #"@") first)))
