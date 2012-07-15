(ns ileitchbot.twitter)
(use 'ileitchbot.credentials)
(require 'twitter
         ['oauth.client :as 'oauth])

(def oauth-consumer (oauth/make-consumer oauth-consumer-key
                                         oauth-consumer-secret
                                         "https://api.twitter.com/oauth/request_token"
                                         "https://api.twitter.com/oauth/access_token"
                                         "https://api.twitter.com/oauth/authorize"
                                         :hmac-sha1))

(defn tweet [user message]
  (println (str user " " message))
  (twitter/with-oauth oauth-consumer
                    oauth-access-token
                    oauth-access-token-secret
                    (twitter/update-status (str user " " message)))))