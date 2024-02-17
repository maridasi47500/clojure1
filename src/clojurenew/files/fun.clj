load "fichier"
(defn renderfigure [title & body]
  (println-str "<html><head><title>" title "</title></head><body>" (apply str (interpose " " body)) "</body></html>" "")
  ;; (format (lire "../../vues/index.html") title body)
  )
