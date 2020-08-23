(defproject de.phenomdevel/component-process-watcher "0.1.0-SNAPSHOT"
  :url "https://github.com/PhenomDevel/component-process-watcher"
  :dependencies
  [[org.clojure/clojure "1.10.1"]

   ;; Misc
   [com.taoensso/timbre "4.10.0"]
   [com.stuartsierra/component "1.0.0"]]

  :source-paths
  ["src/clj"]

  :profiles
  {:dev
   {:source-paths
    ["dev"]}})
