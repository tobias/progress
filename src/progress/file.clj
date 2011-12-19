(ns progress.file
  (:use progress.bar
        progress.util))


(defn file-ratio
  "Generates a file size ratio string based on the given sizes"
  [current-size final-size]
  (str (convert-unit current-size) " / " (convert-unit final-size)))

(defn display-file-progress
  "Displays the progress of a file download using either a progress
bar or a simple final-size counter, depending on wether a final-size is given."
  ([current-size]
     (display-file-progress current-size nil))
  ([current-size final-size]
     (if (nil? final-size)
       (padded-printr (convert-unit current-size))
       (padded-printr
        (str
         (progress-bar (if (> final-size 0)
                         (* 100 (/ current-size final-size))
                         0))
         " "
         (file-ratio current-size final-size))))))

(defn done?
  "Returns true if the download is finished based on the file sizes."
  [current-size final-size]
  (= current-size final-size))

(defn check-progress
  "Checks the progress of a download, displaying progress data."
  [f options]
  (Thread/sleep (get options :monitor-interval 100))
  (if (.exists f)
    (let [current-size (.length f)
          final-size (:filesize options)]
      (when-not (done? current-size (:filesize options))
        (display-file-progress current-size final-size)
        (recur f options)))
    (recur f options)))

(defn monitor
  "Starts a thread to handle check-progress"
  [f options]
  (let [out *out*]
    (doto
        (Thread. (fn [] (binding [*out* out]
                          (check-progress f options))))
      (.start))))

(defmacro with-file-progress
  "Monitors the size of a file for the duration of BODY.

Options are specified as part of the arg vector in keyword/value pairs.
Currently, the only two options available are :filesize and :monitor-interval.
:monitor-interval controls how often the monitor thread checks and updates
the progress, and defaults to 100ms.

Monitoring can be disabled entirely by setting the progress.monitor sytem property
to \"false\"."
  [f & opts-and-body]
  (let [[options# body#] (extract-options opts-and-body)]
    `(let [monitor-thread# (if (monitor?) (monitor ~f ~options#))]
       (try
         ~@body#
         (done)
         (finally (if monitor-thread#
                    (.stop monitor-thread#)))))))
