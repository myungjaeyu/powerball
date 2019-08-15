(defproject powerball "0.1.0-SNAPSHOT"
  :main powerball.core
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]
                 [toucan "1.12.0"]
                 [org.postgresql/postgresql "42.2.5"]
                 [environ "1.1.0"]
                 [enlive "1.1.6"]]
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}})