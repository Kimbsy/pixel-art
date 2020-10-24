(ns pixel-art.scenes.demo
  (:require [pixel-art.sprites.letter-pickup :as letter-pickup]
            [pixel-art.sprites.squishy-cube :as squishy-cube]
            [quip.utils :as qpu]
            [quip.sprite :as qpsprite]))

(defn update-demo
  [{:keys [current-sprite-idx] :as state}]
  (update-in state [:scenes :demo :sprites current-sprite-idx :sprite]
             (fn [{:keys [update-fn] :as sprite}]
               (update-fn sprite))))

(defn draw-demo
  [{:keys [current-sprite-idx] :as state}]
  (let [{background-color             :background-color
         {:keys [draw-fn] :as sprite} :sprite
         :or {background-color qpu/black}}
        (get-in state [:scenes :demo :sprites current-sprite-idx])]
    (qpu/background background-color)
    (draw-fn sprite)))

;; LIST OF SPRITES
(defn sprites
  []
  [(squishy-cube/init)
   (letter-pickup/init)])

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
                   {:keys [animations] :as sprite} :sprite}]
               (let [new-animation-idx (mod (f current-animation-idx) (count animations))
                     new-animation (nth (keys animations) new-animation-idx)]
                 {:current-animation-idx new-animation-idx
                  :sprite (qpsprite/set-animation sprite new-animation)}))))

(defn key-pressed-fns
  []
  [(fn [state {k :key :as e}]
     (if (= :left k)
       (modify-current-sprite-idx state inc)
       state))
   (fn [state {k :key :as e}]
     (if (= :right k)
       (modify-current-sprite-idx state dec)
       state))
   (fn [state {k :key :as e}]
     (if (= :up k)
       (modify-current-animation-idx state inc)
       state))
   (fn [state {k :key :as e}]
     (if (= :down k)
       (modify-current-animation-idx state dec)
       state))])

(defn init
  []
  {:update-fn       update-demo
   :draw-fn         draw-demo
   :sprites         (sprites)
   :key-pressed-fns (key-pressed-fns)})
