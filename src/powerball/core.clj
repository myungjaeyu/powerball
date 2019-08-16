(ns powerball.core
  (:require [powerball.app :refer [app]]
            [powerball.db.util :refer [db-conn]]))

(defn -main []
  (db-conn)
  (app))