(ns ileitchbot.core)

(use 'ileitchbot.twitter
     'ileitchbot.github
     'overtone.at-at
     'clj-time.coerce)

(def ileitch "@ileitch")
(def frequency 10000)
(def last-check (from-long (now)))

(defn tweet-new-issues []
  (doseq [issue (new-issues last-check)] (tweet ileitch issue))
  (def last-check (from-long (now))))

(defn -main []
  (def pool (mk-pool))
  (every frequency tweet-new-issues pool))