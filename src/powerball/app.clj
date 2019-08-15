(ns powerball.app
  (:require [powerball.crawler.core :refer [get-powerball]]))

(defn app []
  (-> (get-powerball "2019-08-15" 1)
      println))