(ns pixel-art.core
  (:gen-class)
  (:require [pixel-art.scenes.demo :as demo]
            [quip.core :as qp]
            [quip.utils :as qpu]))

(defn setup
  []
  {:current-sprite-idx 0})

(defn init-scenes
  []
  {:demo (demo/init)})

(def demo
  (qp/game
   {:title          "Pixel Art Animation Demo"
    :size           [800 600]
    :setup          setup
    :init-scenes-fn init-scenes
    :current-scene  :demo}))

(defn -main
  [& args]
  (qp/run demo))
