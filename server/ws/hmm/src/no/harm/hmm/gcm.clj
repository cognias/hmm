(ns no.harm.hmm.gcm
  (require [clj-http.client :as httpc]))

(defn send-message [message sender receiver]
  (httpc/post "https://gcm-http.googleapis.com/gcm/send"
              {:headers {"Authorization"
                         "key=AIzaSyARnnbX9Tl9M_cZlZhqBO3Zl6FU1KaPAyY"}
               :content-type :json
               :accept :json
               :body "{\"to\": \"/topics/global\", \"data\": {
                        \"message\": \"Hmm twoo\"}}"}))
               
