
(defproject clojurenew "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [javax.xml.bind/jaxb-api "2.3.1"]
                 [http-kit "2.2.0"]
                 [clj-time "0.14.0"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [metosin/jsonista "0.3.8"]
                 [org.xerial/sqlite-jdbc "3.15.1"]
                 [cheshire "5.12.0"]
                 [ring/ring-codec "1.2.0"]
]
  :repl-options {:init-ns clojurenew.core}
  :main clojurenew.core)
