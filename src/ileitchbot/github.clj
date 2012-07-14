(ns ileitchbot.github)

(use 'ileitchbot.credentials
     '[clojure.data.json :only (read-json json-str)]
     'clj-time.core
     'clj-time.format)

(require '[http.async.client :as http])

(def issues-url "https://api.github.com/issues")
(def parameters {:access_token github-api-token :filter "subscribed" :state "open"})
(def date-formatter (formatters :date-time-no-ms))

(defn issues-json []
  (with-open [client (http/create-client)]
  (let [response (http/GET client issues-url :query parameters)]
    (http/string (http/await response)))))

(defn format-issue [issue]
  (let [title (:title issue) repo (:name (:repository issue)) url (:html_url issue)]
    (str "(" repo ") " title " " url)))

(defn new-issue? [issue last-check]
  (let [created-at (parse date-formatter (:created_at issue))]
    (after? created-at last-check)))

(defn new-issues [last-check]
  (let [all-issues (read-json (issues-json))]
    (let [new-issues (filter '(new-issue? % last-check) all-issues)]
      (doseq [issue new-issues] (format-issue issue)))))
