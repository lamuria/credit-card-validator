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
  "Returns type from number"
  [card-number]
  (some #(when (re-seq (:pattern %) card-number) %)
              cards))
