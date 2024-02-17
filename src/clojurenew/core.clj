(ns clojurenew.core
  (:require [org.httpkit.server :refer [run-server]]
            [clojure.java.io :as io]
            [clj-time.core :as t]))


(defn renderfigure [title body]
  #_(println-str "<html><head><title>" title "</title></head><body>" body "</body></html>" "")
  (format (slurp (io/resource "index.html")) title body))

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
