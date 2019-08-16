(ns powerball.crawler.core
  (:require [org.httpkit.client :as http]
            [net.cgrand.enlive-html :as html]))

(def api-url "https://www.dhlottery.co.kr/lottoControl.do?method=powerBallWinNoList")

(defn parse-int [text] 
  (Integer. 
   (re-find #"\d+" 
            text)))

(defn content [data] 
  (-> (:content data) 
      first 
      parse-int))

(defn get-round [elem] 
  (-> (html/select elem [:th]) 
      first 
      content))

(defn get-ball [elem] 
  (-> (html/select elem [:td]) 
      last 
      content))

(defn get-pball [elem] 
  (-> (html/select elem [:.key_clr2]) 
      last 
      content))

(defn is-under? [datum num] 
  (if (< num datum) 1 2))

(defn is-odd? [num] 
  (if (odd? num) 1 2))

(defn is-size? [num] 
  (cond (> num 80) 3
        (> num 64) 2
        :else 1))

(defn is-section? [num]
  (cond (> num 78) 6
        (> num 65) 5
        (> num 57) 4
        (> num 49) 3
        (> num 35) 2
        :else 1))

(defn is-psection? [num]
  (cond (> num 6) 4
        (> num 4) 3
        (> num 2) 2
        :else 1))

(defn get-row [row]
  (let [{:keys [date num round ball pball]} row]
    {:date (java.sql.Timestamp/valueOf (str date " 00:00:00"))
     :num (- 289 num)
     :round round
     :ball ball
     :uo (is-under? 72.5 ball)
     :oe (is-odd? ball)
     :section (is-section? ball)
     :size (is-size? ball)
     :pball pball
     :puo (is-under? 4.5 pball)
     :poe (is-odd? pball)
     :psection (is-psection? pball)}))

(defn get-data [date row dom]
  (-> (html/select dom [:.count])
      (->> (map-indexed (fn [index elem]
                  (get-row {:date date
                            :num (+ index row)
                            :round (get-round elem)
                            :ball (get-ball elem)
                            :pball (get-pball elem)}))))))

(defn get-powerball [date row]
  (-> @(http/post api-url
                  {:form-params {:searchDate date 
                                 :startRow row}})
      :body
      html/html-snippet
      ((fn [dom]
         (get-data date row dom)))))