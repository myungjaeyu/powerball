(ns powerball.app
  (:require [powerball.crawler.core :refer [get-powerball]]
            [powerball.db.service :as db]))

(defn app []
  (-> (get-powerball "2019-08-15" 1)
      db/add-powerball-many))