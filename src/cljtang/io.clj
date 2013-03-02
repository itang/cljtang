(ns cljtang.io
  (:require [clojure.java.io :refer [file]]))

(defn concatenating
  "合并文件内容"
  [src dest & {:keys [separator banner] :or {separator \newline}}]
  (let [src (if (sequential? src) src [src])
        src-files (map file src)
        dest-file (file dest)
        dest-parent-file (.getParentFile dest-file)]
    (when-not (.exists dest-parent-file)
      (.mkdirs dest-parent-file))
    (spit dest-file (str banner))
    (doseq [f src-files]
      (let [separator (if (fn? separator) (separator f) separator)
            content (slurp f)]
        (spit dest-file (str separator content) :append true)))))
