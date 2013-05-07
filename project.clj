(defproject progress/progress "1.0.3-SNAPSHOT" 
  :profiles {:dev
             {:dependencies
              [[org.clojure/clojure "1.4.0"]
               [midje "1.5.1"]]}}
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :url "https://github.com/tobias/progress"
  :min-lein-version "2.0.0"
  :description "Useful(?) progress indicators"
  :lein-release {:deploy-via :clojars})
