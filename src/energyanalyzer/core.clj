(ns energyanalyzer.core
  (:require [clojure-csv.core :as csv]
            [clojure.string :as str]
            [clj-time.core :as time]
            [clj-time.format :as time-format]))

(defn nrel-date-formatter [] (time-format/formatter "MMM dd, yyyy hh:mm a"))

(defn count-lines
  "Lazily counts the lines in a file."
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
      (count lazy-seq-of-lines))))
;; (count-lines "data/Test15Minute.csv")

(defn return-line
  "Parses a single line of CSV data into date, kWh, and qualityOfReading"
  [line]
  (str line "\n"))

(defn parse-date [date-str]
  (time-format/parse (nrel-date-formatter) date-str))
;; (parse-date "Jan 6, 2013 2:15 PM")

(defn parse-kwh [kwh] kwh)

(defn parse-quality [quality] quality)

(defn parse-line [line-vec]
  [ (parse-date (nth line-vec 0))
    (parse-kwh (nth line-vec 1))
    (parse-quality (nth line-vec 2)) ])

;;(parse-line ["Jan 1, 2012 12:15 AM" "0.1" "0" ""])


(defn parse-csv
  "Skips the first line of the CSV and parses the data with a given line-paser function."
  [line-parser filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
       (map line-parser lazy-seq-of-lines))))
;; (parse-csv parse-line "data/Test15Minute.csv")

(defn doto-csv
  "Skips the first line of the CSV and parses the data with a given line-paser function."
  [eval-function line-function filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (csv/parse-csv rdr)]
      (eval-function (map line-function lazy-seq-of-lines)))))




(defn -main []
  (println "CSV Data: " (doto-csv dorun println "data/Test15Minute.csv")))

#_(defn -main []
  (doto-csv parse-line "data/Test15Minute.csv"))

;; (-main)
;; (count-lines "data/Test15Minute.csv")
;; (parse-csv parse-line "data/Test15Minute.csv")
;; (with-open [rdr (clojure.java.io/reader "data/Test15Minute.csv")] )
