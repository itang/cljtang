(ns cljtang.io
  (:require [clojure.java.io :refer [file]]))

(defn concatenating
  "合并文件内容"
  [src dest & {:keys [separator banner trace] :or {separator \newline}}]
  (let [src-files (if (sequential? src) src [src])
        dest-file (file dest)
        dest-parent-file (.getParentFile dest-file)]
    (when-not (.exists dest-parent-file)
      (.mkdirs dest-parent-file))
    (spit dest-file (str banner))
    (doseq [f src-files]
      (when trace (trace f))
      (let [separator (if (fn? separator) (separator f) separator)
            content (slurp f)]
        (spit dest-file (str separator content) :append true)))))
