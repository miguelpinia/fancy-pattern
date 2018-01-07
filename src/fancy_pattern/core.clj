(ns fancy-pattern.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))


;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Definiciones básicas ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;

(defstruct hexa :x :y)
(def r 50)
(def r1 (/ r (Math/sqrt 3)))
(def r2 (* r (+ 1 (/ 1 (Math/sqrt 3)))))
(def r3 (/ (* r (+ 1 (Math/sqrt 3))) 2))
(def petalo-tam 15)
(def centre-size 12)
(def str-w 3)

(defn petalo
  "Dibuja un petalo en la posición (x, y)"
  [x y]
  (q/ellipse x y petalo-tam petalo-tam))

(defn petalo-r [x y]
  (let [dx (* 0.1 petalo-tam (- (Math/random) 0.5))
        dy (* 0.1 petalo-tam (- (Math/random) 0.5)) ]
    (petalo (+ x 0) (+ y 0))))

(defn flor []
  (q/fill 223 130 138)
  (doseq
      [[x y]
       [[0 0]
        [(/ r2 8) (/ r3 4)]
        [(/ r2 8) (- (/ r3 4))]
        [(/ (+ r1 r2) 4) (/ r1 4)]
        [(/ (+ r1 r2) 4) ( - (/ r1 4))] ]]
    (do
      (q/stroke 223 130 138)
      (q/stroke-weight str-w)
      (q/line (/ r1 2) 0 x y)
      (q/no-stroke)
      (petalo-r x y)))
  (q/fill 248 181 0)
  (q/ellipse (/ r1 2) 0 centre-size centre-size))

(defn patron-hex []
  (q/fill 223 130 138)

  (flor)

  (q/with-translation
    [ (/ (+ r1 r2) 2) (- (/ r1 2)) ]
    (q/with-rotation
      [(/ Math/PI -2)]
      (flor)))

  (q/with-translation
    [ (/ (+ r1 r2) 2)  (/ r1 2) ]
    (q/with-rotation
      [(/ Math/PI 2)]
      (flor)))

  (q/with-translation
    [ (+ r1 r2) 0 ]
    (q/with-rotation
      [Math/PI]
      (flor))))

(defn hexagono [h]
  (q/with-translation
    [(* (:x h) (+ r1 (/ r2 2)))
     (+ (* 2 r3 (:y h))
        (if (even? (:x h))
          0
          r3))]
    (patron-hex)))

(defn config []
  (q/frame-rate 30)
  (q/color-mode :rgb)
  (for [x (range 10) y (range 5)]
    (struct hexa x y)))

(defn actualiza-estado [s] s)

(defn dibuja-estado [state]
  (q/background 255)
  (q/no-loop)

  (q/with-translation [50 100]
    (doseq [h state]
      (hexagono h)))

  (q/save "out.png"))

(q/defsketch nen
  :title      "super-omedetai"
  :size       [800 800]
  :setup      config
  :update     actualiza-estado
  :draw       dibuja-estado
  :features   [:keep-on-top]
  :middleware [m/fun-mode])
