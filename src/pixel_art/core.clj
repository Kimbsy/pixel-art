(ns pixel-art.core
  (:gen-class)
  (:require [pixel-art.scenes.demo :as demo]
            [quil.core :as q]
            [quip.core :as qp]))

(defn setup
  []
  (q/text-font (q/create-font "Ubuntu Mono Bold" 20))
  {:current-sprite-idx 0
   :pause-timer        0})

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
