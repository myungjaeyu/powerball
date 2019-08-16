(ns powerball.db.service
  (:require [toucan.db :refer [insert-many!]]
            [powerball.db.model.powerball :refer [Powerball]]))

(defn add-powerball-many [data]
  (insert-many! Powerball data))