(ns progress.bar)

(defn progress-bar
  "Returns a string representing a progress bar. PERCENT should be an int 0 - 100"
  [percent]
  (if (< percent 100)
    (let [barlen (int (/ percent 2))]
      (str "["
           (apply str (repeat barlen "=")) ">"
           (apply str (repeat (- 50 barlen) " "))
           "] "
           (int percent) "%"))))
