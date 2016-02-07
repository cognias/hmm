(ns no.harm.hmm.core
  (:gen-class t)
  (:require
   [compojure.core :as cmpj]
   [no.harm.hmm.data :as data]
   [no.harm.hmm.gcm :as gcm]
   [ring.middleware.json :as ring-json]
   [ring.middleware.params :as params]
   [ring.util.response :as response]))

(defn -main [& args])

(defn make-db-spec [& optional-args]
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "hmm"
   :user "hmm"
   :password "hmm"})

(def #^{:doc "Database connection obj"} db (ref (make-db-spec)))

(cmpj/defroutes the-routes
  (cmpj/context
   "*" {body :body params :query-params}
   (cmpj/POST "/messages.json" []
              (gcm/send-message nil nil nil))
   (cmpj/GET "/users.json/:username" [username]
             {:body
              {:exists? (not (empty? (data/get-user @db username)))}})
   (cmpj/POST "/users.json" []
              (let [result
                    (data/create-user
                     @db
                     {:username (body "username")
                      :password (body "password")
                      :gcm-id (body "gcm_id")
                      :e-mail (body "e_mail")
                      :phone (body "phone")})]
                (if result
                  {:body
                   {:members [result]}}
                  {:body "User cannot be registered."
                   :status 409})))
   (cmpj/PUT "/users.json" [])
             
   ))


(def main-handler
  (-> the-routes
      params/wrap-params
      ring-json/wrap-json-response
      ring-json/wrap-json-body))

  
