(defproject hmm "0.1.0"
  :description "Web service for Hmm"
  :url "http://www.harm.no/hmm"
  :license {:name ""
            :url ""}
  :dependencies [[clj-http "2.0.1"]
                 [org.clojure/clojure "1.7.0"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [org.postgresql/postgresql "9.4.1207.jre7"]]
  :main ^:skip-aot hmm.core
  :plugins [[lein-ring "0.9.7" :exclusions [org.clojure/clojure]]]
  :ring {:handler no.harm.hmm.core/main-handler}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
