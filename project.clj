(defproject cljtang "0.1.0-SNAPSHOT"
  :description "Itang's Clojure Lib"
  :url "http://project.itang.me/cljtang"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0-RC2"]]
  :profiles {:dev {:plugins [[codox "0.6.4"]
                             [jonase/eastwood "0.0.2"]
                             [lein-localrepo "0.4.1"]
                             [lein-deps-tree "0.1.2"]
                             [lein-pprint "1.1.1"]]}
             :1.4.0 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5.0 {:dependencies [[org.clojure/clojure "1.5.0-RC2"]]}}
   :aliases {"run-tests" ["with-profile" "1.4.0:1.5.0" "test"]
             "slamhound" ["run" "-m" "slam.hound"]}
   :warn-on-reflection true
   :injections [(require 'clojure.pprint)]
   :pom-addition [:developers [:developer [:name "itang"]]])
