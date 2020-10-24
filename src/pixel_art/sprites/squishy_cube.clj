(ns pixel-art.sprites.squishy-cube
  (:require [quip.sprite :as qpsprite]
            [quip.utils :as qpu]))

(defn init
  []
  {:current-animation-idx 0
   :sprite
   (qpsprite/animated-sprite :player
                             [400 300]
                             32
                             32
                             "img/squishy-cube.png"
                             :animations {:idle {:frames      4
                                                 :y-offset    0
                                                 :frame-delay 10}
                                          :jump {:frames      6
                                                 :y-offset    1
                                                 :frame-delay 5}
                                          :dash {:frames      6
                                                 :y-offset    2
                                                 :frame-delay 5}
                                          :turn {:frames      6
                                                 :y-offset    3
                                                 :frame-delay 2}}
                             :current-animation :idle)})
