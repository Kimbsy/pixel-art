(ns pixel-art.sprites.letter-pickup
  (:require [quip.sprite :as qpsprite]
            [quip.utils :as qpu]))

(defn init
  []
  {:current-animation-idx 0
   :sprite
   (qpsprite/animated-sprite :pickups
                             [400 300]
                             48 48
                             "img/letter-pickup.png"
                             :animations {:mutate    {:frames      52
                                                      :y-offset    0
                                                      :frame-delay 6}
                                          :activated {:frames      10
                                                      :y-offset    1
                                                      :frame-delay 2
                                                      :cycle-pause 60}}
                             :current-animation :mutate)})
