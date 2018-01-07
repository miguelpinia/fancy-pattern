(defproject fancy-pattern "0.1.0-SNAPSHOT"
  :description "Ejercicio de juguete."
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [quil "2.6.0"]]
  :main ^:skip-aot fancy-pattern.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
