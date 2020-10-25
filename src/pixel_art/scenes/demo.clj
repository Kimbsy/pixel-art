(ns pixel-art.scenes.demo
  (:require [pixel-art.sprites.letter-pickup :as letter-pickup]
            [pixel-art.sprites.squishy-cube :as squishy-cube]
            [quil.core :as q]
            [quip.sprite :as qpsprite]
            [quip.utils :as qpu]))

;; LIST OF SPRITES
(defn sprites
  []
  [(squishy-cube/init)
   (letter-pickup/init)])

(defn update-demo
  [{:keys [current-sprite-idx pause-timer] :as state}]
  (let [{:keys [animations current-animation update-fn] :as sprite}
        (get-in state [:scenes :demo :sprites current-sprite-idx :sprite])

        {:keys [frame-delay] :as animation} (current-animation animations)]
    (if (zero? pause-timer)
      (let [{:keys [animation-frame] :as updated-sprite} (update-fn sprite)
            cycle-pause (or (:cycle-pause animation) 0)]
        (if (zero? animation-frame)
          (-> state
              (assoc :pause-timer cycle-pause)
              (assoc-in [:scenes :demo :sprites current-sprite-idx :sprite]
                        (-> updated-sprite
                            (assoc :delay-count (dec frame-delay)))))
          (assoc-in state [:scenes :demo :sprites current-sprite-idx :sprite]
                    updated-sprite)))
      (update state :pause-timer dec))))

(defn draw-demo
  [{:keys [current-sprite-idx] :as state}]
  (let [{background-color :background-color
         {:keys [draw-fn sprite-group current-animation] :as sprite} :sprite
         :or {background-color qpu/black}}
        (get-in state [:scenes :demo :sprites current-sprite-idx])]
    (qpu/background background-color)
    (qpu/fill qpu/black)
    (let [width (+ 20 (* 10 (max (count (str sprite-group))
                                 (count (str current-animation)))))]
      (q/rect 0 0 width 70))
    (qpu/fill qpu/white)
    (q/text (str sprite-group) 10 25)
    (q/text (str current-animation) 10 50)
    (draw-fn sprite)))

(defn modify-current-sprite-idx
  [state f]
  (let [sprites (get-in state [:scenes :demo :sprites])]
    (update state :current-sprite-idx
            (fn [idx]
              (mod (f idx) (count sprites))))))

(defn modify-current-animation-idx
  [{:keys [current-sprite-idx] :as state} f]
  (update-in state [:scenes :demo :sprites current-sprite-idx]
             (fn [{current-animation-idx :current-animation-idx
                   {:keys [animations] :as sprite} :sprite
                   :as container}]
               (let [new-animation-idx (mod (f current-animation-idx) (count animations))
                     new-animation (nth (keys animations) new-animation-idx)]
                 (merge container
                        {:current-animation-idx new-animation-idx
                         :sprite (qpsprite/set-animation sprite new-animation)})))))

(defn key-pressed-fns
  []
  [(fn [state {k :key :as e}]
     (if (= :left k)
       (-> state
           (modify-current-sprite-idx inc)
           (assoc :pause-timer 0))
       state))
   (fn [state {k :key :as e}]
     (if (= :right k)
       (-> state
           (modify-current-sprite-idx dec)
           (assoc :pause-timer 0))
       state))
   (fn [state {k :key :as e}]
     (if (= :up k)
       (-> state
           (modify-current-animation-idx inc)
           (assoc :pause-timer 0))
       state))
   (fn [state {k :key :as e}]
     (if (= :down k)
       (-> state
           (modify-current-animation-idx dec)
           (assoc :pause-timer 0))
       state))])

(defn init
  []
  {:update-fn       update-demo
   :draw-fn         draw-demo
   :sprites         (sprites)
   :key-pressed-fns (key-pressed-fns)})
