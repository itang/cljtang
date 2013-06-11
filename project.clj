(defproject cljtang "0.1.1"
  :description "Itang's Clojure utility 糖"
  :url "http://project.itang.me/cljtang"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:plugins [[codox "0.6.4"]
                             [lein-pprint "1.1.1"]
                             [lein-checkall "0.1.1"]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
   :aliases {"run-tests" ["with-profile" "1.4:1.5" "test"]}
   :global-vars {*warn-on-reflection* true}
   :injections [(require 'clojure.pprint)]
   :pom-addition [:developers [:developer
                               [:id "itang"]
                               [:name "唐古拉山"]
                               [:url "http://www.itang.me"]
                               [:email "live.tang@gmail.com"]]])
