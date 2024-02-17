(ns clojurenew.core
  (:require [org.httpkit.server :refer [run-server]]
            [clojure.java.io :as io]
            [clj-time.core :as t]))


(defn renderfigure [title body]
  #_(println-str "<html><head><title>" title "</title></head><body>" body "</body></html>" "")
  (format (slurp (io/resource "index.html")) title body))

(defn lirefichier [hey]
  (slurp (io/resource hey)))

(defn renderhtml [title hey]
  (renderfigure title (clojurenew.core/lireficher hey)))

(defn app [req]
  (println "my application")
  (println (get req :uri "aucune adresse url"))
  (def uri (get req :uri "aucune adresse url"))
  (def html (let [mystr uri]
    (case mystr
          "/" (renderhtml "hey" "welcome.html")
          "/hello" (renderhtml "hello" "hello.html")
          (renderhtml "Erreur" "404.html"))))
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    html})


(defn -main [& args]
  (run-server app {:port 8080})
  (println "Server started on port 8080"))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
