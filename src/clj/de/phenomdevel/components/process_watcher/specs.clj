(ns de.phenomdevel.components.process-watcher.specs
  (:require
   [clojure.spec.alpha :as s]))


;; =============================================================================
;; Specs

(s/def ::command string?)
(s/def ::config
  (s/keys
   :req-un [::command]))
