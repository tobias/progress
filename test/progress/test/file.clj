(ns progress.test.file
  (:use progress.file
        progress.bar
        progress.util
        midje.sweet))

(facts "done? when file sizes are equal"
  (done? 1 2) =not=> true
  (done? 2 2) => true)

(fact "file-ratio returns the ratio"
  (file-ratio 1 1024) => "1B / 1.00KB")

(fact "display-file-progress with filesize"
  (display-file-progress 1 1) => nil
  (provided (progress-bar 100) => ""
            (file-ratio 1 1) => ""))

(fact "display-file-progress with 0 when given a dl-size of 0"
  (display-file-progress 0 1) => nil
  (provided (progress-bar 0) => ""
            (file-ratio 0 1) => ""))

(fact "display-file-progress with no filesize"
  (display-file-progress 1) => nil
  (provided (convert-unit 1) => ""))

(def a-value (atom nil))

(against-background [(before :facts (reset! a-value nil))]
  (fact "with-file-progress"
    (with-file-progress :file (reset! a-value :called)) => :done
    (provided
      (monitor :file {}) => nil
      (done) => :done)
    @a-value => :called)

    (fact "with-file-progress with :filesize option"
    (with-file-progress :file :filesize 1234 (reset! a-value :called)) => :done
    (provided
      (monitor :file {:filesize 1234}) => nil
      (done) => :done)
    @a-value => :called)
  
  (fact "with-file-progress with monitoring disabled"
    (with-file-progress :file (reset! a-value :called)) => :done
    (provided
      (monitor?) => false
      (monitor anything anything) => nil :times 0
      (done) => :done)
    @a-value => :called))


