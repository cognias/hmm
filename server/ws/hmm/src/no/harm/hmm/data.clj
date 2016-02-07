(ns no.harm.hmm.data
  (:require [clojure.java.jdbc :as jdbc]))

(defn create-user [db user-spec]
  (println user-spec)
  (try
    (jdbc/execute!
     db [(str "INSERT INTO users (gcm_id, username, password, e_mail, phone)"
              " VALUES (?, ?, ?, ?, ?);")
         (user-spec :gcm-id)
         (user-spec :username)
         (user-spec :password)
         (user-spec :e-mail)
         (user-spec :phone)])
    (catch Exception e
      (println (. e getNextException))
      nil)))

(defn get-user [db username]
  (jdbc/query db ["SELECT * FROM users WHERE username = ?" username]))
  
