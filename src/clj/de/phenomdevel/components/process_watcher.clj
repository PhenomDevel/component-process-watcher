(ns de.phenomdevel.components.process-watcher
  (:require
   [clojure.spec.alpha :as s]

   [taoensso.timbre :as log]
   [com.stuartsierra.component :as c]

   [de.phenomdevel.components.process-watcher.specs :as specs]))


;; =============================================================================
;; Private Helper

(defn- print-stream
  [stream]
  (let [buffer
        (->> stream
             (java.io.InputStreamReader.)
             (java.io.BufferedReader.))]

    (loop [line (.readLine buffer)]
      (when line
        (println line)
        (recur (.readLine buffer))))))

(defn- redirect-output!
  [p]
  (when (instance? java.lang.Process p)
    (-> p
        (.getInputStream)
        (print-stream)
        (future))))


;; =============================================================================
;; Component

(defrecord ProcessWatcher [command

                           process]

  c/Lifecycle
  (start [this]
    (let [process
          (.exec (Runtime/getRuntime) command)]

      (log/info "[ProcessWatcher] Starting new process with `")
      (redirect-output! process)
      (assoc this :process process)))

  (stop [this]
    (log/info "[ProcessWatcher] Stopping process for `")
    (when (and process (.isAlive process))
      (.destroy process))
    (assoc this :process nil)))


;; =============================================================================
;; Public API

(defn new-process-watcher
  [config]
  {:pre [(s/valid? ::specs/config config)]}
  (map->ProcessWatcher config))
