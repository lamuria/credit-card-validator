(ns credit-card-validator.luhn-checker-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]))

(deftest string-to-number-test
  (let [result (core/to-number "123")]
    (is (= 123 result))))

(deftest sum-digits-test
  (let [result (core/sum-digits 1)]
    (is (= 1 result)))
  (let [result (core/sum-digits 123)]
    (is (= 6 result)))
  (let [result (core/sum-digits 1234)]
    (is (= 10 result))))

(deftest luhn-checker-test
  (testing "should be valid"
    (let [card-number "4188360002354029"
          result (core/luhn-checker card-number)]
      (is result)))

  (testing "should not be valid"
    (let [card-number "4138290002354022"
          result (core/luhn-checker card-number)]
      (is (not result)))))
