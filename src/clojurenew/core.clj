(ns clojurenew.core
  (:require [org.httpkit.server :refer [run-server]]
            [clj-time.core :as t]))
(load "files/fun")

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    (renderfigure "hey" "hello there")})

(defn -main [& args]
  (run-server app {:port 8080})
  (println "Server started on port 8080"))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
