(ns clojurenew.core
  (:require [org.httpkit.server :refer [run-server]]
            [clojure.java.io :as io]

            [clj-time.core :as t]
            [clojure.java.jdbc :refer :all]
            [cheshire.core :as json]
            ))
  (use 'ring.util.codec)
  (use 'clojure.walk)
  (require '[clojure.string :as str])

(def testdata
  { :url "http://example.com",
   :title "SQLite Example",
   :body "Example using SQLite with Clojure"
   })



(defn renderfigure [title body]
  #_(println-str "<html><head><title>" title "</title></head><body>" body "</body></html>" "")
  (format (slurp (io/resource "index.html")) title body))

(defn lirefichier [hey]
  (slurp (io/resource hey)))

(defn renderhtml [title hey]
  {:status 200
    :body (format (slurp (io/resource "index.html")) title (slurp (io/resource hey)))
    :contenttype "text/html"
    })


;; create db
(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "db/database.db"})

(defn output
  "execute query and return lazy sequence"
  []
  (query db ["select * from bath"]))

(defn rendercollection [title bdd template req]
  (println "action create")
  (def mytemplate (slurp (io/resource template)))
  (def figure (map #(format mytemplate (get % :url "") (get % :title "") (get % :body "")) (output)))
  (def body (format (slurp (io/resource "bains.html")) (str/join "" figure)))
  (def title "hey")
  (def hey (if (zero? (count (output))) "<p>il n'y a pas de bains à afficher</p>" body))
  (def reponsebody (format (slurp (io/resource "index.html")) title hey))
  {:status 200
    :body reponsebody
    :contenttype "text/html"
    :redirect ""
    })

(defn actioncreate [title hey req]
  (println "action create")
  (def heyreader (io/reader (:body req) :encoding "UTF-8"))
  (println heyreader)
  (println "hey HEY action create")
  (println "hHo")
  (def a (slurp heyreader) )
  (println a)

  (def myhash (keywordize-keys (form-decode a)))
  (println "hey HEY he")
  (println myhash)
  (def mytestdata {:title (get myhash :title "hye")
                   :url (get myhash :url "url")
                   :body (str/join "" (get myhash :body ["ur" "body"])) })
  (insert! db :bath mytestdata)
  (println "OHOHOH")
  {:status 301
    :body (slurp (io/resource "redirect.html"))
    :contenttype "text/html"
    :redirect "/voir_bains"
    })

(defn create-db
  "create db and table"
  []
  (try (db-do-commands db
                       (create-table-ddl :bath
                                         [[:timestamp :datetime :default :current_timestamp ]
                                          [:url :text]
                                          [:title :text]
                                          [:body :text]]))
       (catch Exception e
         (println (.getMessage e)))))

(defn print-result-set
  "prints the result set in tabular form"
  [result-set]
  (doseq [row result-set]
    (println row)))


(defn output
  "execute query and return lazy sequence"
  []
  (query db ["select * from bath"]))





(defn app [req]
  (println "bdd")
  (println (output))
  (println "my application")
  (println req)
  (println (get req :uri "aucune adresse url"))
  (def uri (get req :uri "aucune adresse url"))
  (def html (let [mystr uri]
    (case mystr
          "/" (renderhtml "hey" "welcome.html")
          "/hello" (renderhtml "hello" "hello.html")
          "/create_bath" (renderhtml "hello" "form.html")
          "/action_create_bath" (actioncreate "hello" "form.html" req)
          "/voir_bains" (rendercollection "hello" output "_bain.html" req)

          (renderhtml "Erreur" "404.html"))))
  (def status (get html :status 200))
  (def content (get html :contenttype "text/html"))
  (def body (get html :body "<h1>erreur 404 desole</h1>"))
  (def redirect (get html :redirect ""))
  {:status  status
   :headers {"Content-Type" content
             "Location" redirect}
   :body    body})


(defn -main [& args]
  (create-db)
  (run-server app {:port 8080})
  (println "Server started on port 8080"))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
