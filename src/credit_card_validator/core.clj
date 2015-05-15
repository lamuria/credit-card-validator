(ns credit-card-validator.core
  (require [clj-time.core :as t]
           [clj-time.format :as f]))

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

(defn cvc-is-valid?
  "Validates cvc number"
  [cvc-number card-type]
  (let [cvc-number-length (count cvc-number)
        card (card-from-type card-type)]
    (some #(= cvc-number-length %) (:cvc-lenght card))))

(def date-formatter (f/formatter "MM/yyyy"))
(def date-regex #"^(0[1-9]|1[0-2])/(19|2[0-1])\d{2}")

(defn validate-expiry-date
  [date-format]
  (let [expiry-date (f/parse date-formatter date-format)
        current-date (f/parse date-formatter (f/unparse date-formatter (t/now)))]
    (not (t/after? current-date expiry-date))))

(defn expiry-date-is-valid?
  [date-format]
  (if (re-matches date-regex date-format)
    (validate-expiry-date date-format)
    (str "invalid date")))

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

(defn is-valid?
  [card-number cvc valid-date]
  (let [card (card-from-number card-number)
        card-type (:type card)]
    (and (cvc-is-valid? cvc card-type)
         (luhn-checker card-number))))
