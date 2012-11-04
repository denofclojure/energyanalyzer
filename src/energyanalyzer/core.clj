(ns energyanalyzer.core
  (:require [clojure-csv.core :as csv]
            [clojure.string :as str]))

(defn count-lines
  "Lazily counts the lines in a file."
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
      (count lazy-seq-of-lines))))

(defn parse-line
  "Parses a single line of CSV data into date, kWh, and qualityOfReading"
  [line]
  line)

(defn parse-csv
  "Skips the first line of the CSV and parses the data with a given line-paser function."
  [line-parser filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (let [lazy-seq-of-lines (line-seq rdr)]
      (map line-parser lazy-seq-of-lines))))
;; (parse-csv parse-line "data/Test15Minute.csv")

(defn -main []
  (println "CSV Data: " (parse-csv "data/Test15Minute.csv")))

;; (-main)
;; (count-lines "data/Test15Minute.csv")
