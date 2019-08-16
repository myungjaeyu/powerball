(ns powerball.app
  (:require [powerball.crawler.core :refer [get-powerball]]
            [powerball.db.service :as db]
            [powerball.util :refer [get-yesterday-date]]))

(def date (atom {:current "2019-08-15" :last "2013-01-02"}))
(def flag (atom true))
(def row (atom 1))

(defn log [] (println @row (str (:current @date))))

(defn app []
  (while (true? @flag)
    (let [data (get-powerball (:current @date) @row)]
      (db/add-powerball-many data)
      (log)
      (if (not= (count data) 0)
        (swap! row (partial + 10))
        (if (not= (:current @date) (:last @date))
          ((fn [] 
             (reset! row 1) 
             (swap! date assoc :current (get-yesterday-date (:current @date)))))
          (reset! flag false))))))