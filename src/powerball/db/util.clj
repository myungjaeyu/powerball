(ns powerball.db.util
  (:require [toucan.db :as db]        
            [environ.core :refer [env]]))

(defn db-conn []
  (db/set-default-db-connection! {:dbtype (env :db-type)
                                  :dbname (env :db-name)
                                  :user (env :db-user)
                                  :host (env :db-host)
                                  :password (env :db-password)}))