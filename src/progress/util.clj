(ns progress.util)

(defn padded-printr
  "prints S, prefixed with \r and padded to 80 characters"
  [s]
  (print (str "\r" s (apply str (repeat (- 80 (.length s)) " "))))
  (flush))

(defn done
  "Prints a done message over any progress message"
  []
  (padded-printr "done!")
  (newline))

(defn format-unit
  [bytes conversion type]
  (format "%.2f%s" (float (/ bytes conversion)) type))

(defn convert-unit
  "Converts SIZE (in bytes) to a reasonable alternative and formats it for display"
  [size]
  (let [kb 1024
        mb (* kb 1024)
        gb (* mb 1024)]
    (cond
     (< size kb) (str size "B")
     (< size mb) (format-unit size kb "KB")
     (< size gb) (format-unit size mb "MB")
     :default    (format-unit size gb "GB"))))

(defn monitor?
  "Checks for the progress.monitor sysprop. If false, return false. Otherwise, return true"
  []
  (if-let [prop (System/getProperty "progress.monitor")]
    (not= prop "false")
    true))

(defn extract-options
  "Extracts keyword/value pairs from the front of XS into a map, returning the map and the remainder of XS."
  [xs]
  (let [options 
        (apply hash-map (flatten (take-while #(keyword? (first %)) (partition 2 xs))))]
    [options (subvec (vec xs) (* 2 (count options)))]))

