(ns progress.test.bar
  (:use progress.bar
        midje.sweet)
  (:require [clojure.string :as str]))

(facts "progress-bar shows the bar"
  (let [bar (progress-bar 2)
        bar-parts (str/split bar #" ")]
    (first bar-parts) => "[=>"
    (last bar-parts) => "2%")

  (let [bar (progress-bar 3)
        bar-parts (str/split bar #" ")]
    (first bar-parts) => "[=>"
    (last bar-parts) => "3%")

  (let [bar (progress-bar 6)
        bar-parts (str/split bar #" ")]
    (first bar-parts) => "[===>"
    (last bar-parts) => "6%"))

