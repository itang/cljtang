(defproject cljtang "0.1.0-SNAPSHOT"
  :description "Itang's Clojure Lib"
  :url "http://project.itang.me/cljtang"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:plugins [[codox "0.6.4"]
                             [lein-pprint "1.1.1"]
                             [lein-checkall "0.1.0-SNAPSHOT"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
   :aliases {"run-tests" ["with-profile" "1.4:1.5" "test"]}
   :warn-on-reflection true
   :injections [(require 'clojure.pprint)]
   :pom-addition [:developers [:developer [:name "itang"]]])
