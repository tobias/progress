(ns progress.test.util
  (:use progress.util
        midje.sweet))

(fact "convert-unit should do its thing"
  (convert-unit 1) => #"1B"
  (convert-unit 1024) => #"1.00KB"
  (convert-unit (* 1024 1024)) => #"1.00MB"
  (convert-unit (* 1.5 1024 1024)) => #"1.50MB"
  (convert-unit (* 1024 1024 1024)) => #"1.00GB")

(fact "done shows 'done!'"
  (with-out-str (done)) => #"\rdone! *\n")

(against-background [(after :facts (System/clearProperty "progress.monitor"))]
  (fact "monitor? when the progress.monitor sysprop is not set"
    (System/clearProperty "progress.monitor")
    (monitor?) => true)

  (fact "monitor? when the progress.monitor sysprop is \"true\""
    (System/setProperty "progress.monitor" "true")
    (monitor?) => true)

  (fact "monitor? when the progress.monitor sysprop is \"false\""
    (System/setProperty "progress.monitor" "false")
    (monitor?) => false))

(facts "extract-options"
  (extract-options [:foo]) => [{} [:foo]]
  (extract-options [:a :b :foo]) => [{:a :b} [:foo]]
  (extract-options [:a :b]) => [{:a :b} []]
  (extract-options [:a :b "c" "d"]) => [{:a :b} ["c" "d"]]
  (extract-options [:a :b :c :d "c" "d"]) => [{:a :b :c :d} ["c" "d"]]
  (extract-options [:a '(+ 1 1)]) => [{:a '(+ 1 1)} []])
