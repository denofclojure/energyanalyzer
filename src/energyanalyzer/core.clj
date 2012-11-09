(ns energyanalyzer.core
  (:require [clojure-csv.core :as csv]
            [clojure.string :as str]
            [clj-time.core :as time]
            [clj-time.format :as time-format]))

(defn count-lines
  "Lazily counts the lines in a file."
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
      (count lazy-seq-of-lines))))

(defn parse-line
  "Parses a single line of CSV data into date, kWh, and qualityOfReading"
  [line]
  (str line "\n"))

(defn parse-csv
  "Skips the first line of the CSV and parses the data with a given line-paser function."
  [line-parser filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
      (doall (map line-parser lazy-seq-of-lines)))))
;; (parse-csv parse-line "data/Test15Minute.csv")

(defn doto-csv
  "Skips the first line of the CSV and parses the data with a given line-paser function."
  [line-function filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
      (map line-function lazy-seq-of-lines))))

(defn -main []
  (println "CSV Data: " (parse-csv parse-line "data/Test15Minute.csv")))

#_(defn -main []
  (doto-csv parse-line "data/Test15Minute.csv"))

;; (-main)
;; (count-lines "data/Test15Minute.csv")
;; (parse-csv parse-line "data/Test15Minute.csv")
;; (with-open [rdr (clojure.java.io/reader "data/Test15Minute.csv")] )
