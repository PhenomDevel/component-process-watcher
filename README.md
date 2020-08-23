[![Clojars Project](https://img.shields.io/clojars/v/de.phenomdevel/component-process-watcher.svg)](https://clojars.org/de.phenomdevel/component-process-watcher)
[![cljdoc badge](https://cljdoc.org/badge/de.phenomdevel/component-process-watcher)](https://cljdoc.org/d/component-process-watcher/component-process-watcher/CURRENT)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/PhenomDevel/component-process-watcher/blob/master/LICENSE)

# component-process-watcher
This library provides an easy to use [`com.stuartsierra/component`](https://github.com/stuartsierra/component) component to start up any process by executing provided `command`

# Installation
To install, add the following to your project `:dependencies`:
```
[de.phenomdevel/component-process-watcher "0.1.0-SNAPSHOT"]
```

## Usage
The code below shows an lightweight example of how you could use this component within your
[`com.stuartsierra/component`](https://github.com/stuartsierra/component) system.
```clj
(require '[de.phenomdevel.components.process-watcher :as process-watcher])
(require '[com.stuartsierra.component :as c])

;; Could also come from a file or something
(def ^:private config
  {:process-watcher
   {:command "lein sass auto"}})

(def !system
  (atom
   (c/system-map
     :process-watcher
     (process-watcher/new-process-watcher (:process-watcher config)))))

(swap! !system c/start)
;; This will start your system with the process-watcher

```

# License
Copyright © 2020 Kevin Kaiser

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
