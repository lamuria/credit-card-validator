(ns credit-card-validator.core)

(def default-format #"\d{1,4}")

(def cards
  [{:type "visa"
    :pattern #"^4"
    :format default-format
    :lenght (range 13 17)
    :cvc-lenght [3]
    :luhn true}
   {:type "mastercard"
    :pattern #"^5[1-5]"
    :format default-format
    :lenght [16]
    :cvc-lenght [3]
    :luhn true}
   {:type "amex"
    :pattern #"^3[47]"
    :format "/(1-4)(1-6)?(1-5)?/"
    :lenght [15]
    :cvc-lenght [3 4]
    :luhn true}])

(defn card-from-number
  "Returns a card from number"
  [card-number]
  (some #(when (re-seq (:pattern %) card-number) %)
        cards))

(defn card-from-type
  "Returns a card from type"
  [card-type]
  (some #(when (= (:type %) card-type) %)
       cards))

(defn validate-cvc
  "Validates cvc number"
  [cvc-number card-type]
  (let [cvc-number-length (count cvc-number)
        card (card-from-type card-type)]
    (some #(= cvc-number-length %) (:cvc-lenght card))))

(defn to-number
  [string]
  (let [number (read-string string)]
    (when (number? number) number)))

(defn sum-digits
  [number]
  (reduce + (map #(to-number (str %)) (str number))))

(defn luhn-checker
  [card-number]
  (let [reversed-number (reverse (map #(Character/getNumericValue %) card-number))
        odd-positions (take-nth 2 (rest reversed-number))
        even-positions (take-nth 2 reversed-number)
        s1 (reduce + odd-positions)
        multiply-each-even-position (map #(* 2 %) even-positions)
        sum-digits-of-each (map sum-digits multiply-each-even-position)
        s2 (reduce + sum-digits-of-each)
        total (+ s1 s2)
        rest-division (rem total 10)]
    (= 0 rest-division)))
