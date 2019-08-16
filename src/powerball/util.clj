(ns powerball.util 
  (:require [clj-time.format :as f]
            [clj-time.core :as t]))

(def formatter (f/formatter "yyyy-MM-dd"))
(defn parse-date [date] (f/parse formatter date))
(defn unparse-date [date] (f/unparse formatter date))

(defn get-yesterday-date [current-date] 
  (unparse-date (t/minus
                 (parse-date current-date)
                 (t/days 1))))