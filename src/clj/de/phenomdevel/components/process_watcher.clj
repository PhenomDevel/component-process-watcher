(ns de.phenomdevel.components.process-watcher
  (:require
   [clojure.spec.alpha :as s]

   [taoensso.timbre :as log]
   [com.stuartsierra.component :as c]

   [de.phenomdevel.components.process-watcher.specs :as specs]))


;; =============================================================================
;; Private Helper

(defn- log-stream
  [stream]
  (let [buffer
        (->> stream
             (java.io.InputStreamReader.)
             (java.io.BufferedReader.))]

    (loop [line (.readLine buffer)]
      (when line
        (log/debug "[ProcessWatcher]" line)
        (recur (.readLine buffer))))))

(defn- redirect-output!
  [p]
  (when (instance? java.lang.Process p)
    (-> (.getInputStream p)
        (log-stream)
        (future))))


;; =============================================================================
;; Component

(defrecord ProcessWatcher [command

                           process]

  c/Lifecycle
  (start [this]
    (let [process
          (.exec (Runtime/getRuntime) command)]

      (log/info "[ProcessWatcher] Starting new process with command `" command "`")
      (redirect-output! process)
      (assoc this :process process)))

  (stop [this]
    (log/info "[ProcessWatcher] Stopping process for command `" command "`")
    (when (and process (.isAlive process))
      (.destroy process))
    (assoc this :process nil)))


;; =============================================================================
;; Public API

(defn new-process-watcher
  [config]
  {:pre [(s/valid? ::specs/config config)]}
  (map->ProcessWatcher config))
