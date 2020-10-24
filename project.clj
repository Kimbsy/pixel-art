(defproject pixel-art "0.1.0-SNAPSHOT"
  :description "Practising pixel art animation"
  :url "https://github.com/Kimbsy/pixel-art"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [quip "1.0.4"]]
  :main ^:skip-aot pixel-art.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
