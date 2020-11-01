(ns pixel-art.sprites.explosion-01
  (:require [quip.sprite :as qpsprite]))

(defn init
  []
  {:current-animation-idx 0
   :sprite
   (qpsprite/animated-sprite :explosion-01
                             [400 300]
                             320 320
                             "img/explosion-01/scaled.png"
                             :animations {:explode {:frames      7
                                                    :y-offset    0
                                                    :frame-delay 4
                                                    :cycle-pause 40}}
                             :current-animation :explode)})
