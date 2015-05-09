(ns credit-card-validator.validate-cvc-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]))

(deftest validate-cvc-test
  (testing "visa"
    (let [card-type "visa"]
      (testing "should be valid"
        (let [result (core/validate-cvc "123" card-type)]
          (is result)))
      (testing "should not be valid"
        (let [result (core/validate-cvc "12" card-type)]
          (is (not result)))
        (let [result (core/validate-cvc "1234" card-type)]
          (is (not result))))))

  (testing "mastercard"
    (let [card-type "mastercard"]
      (testing "should be valid"
        (let [result (core/validate-cvc "123" card-type)]
          (is result)))
      (testing "should not be valid"
        (let [result (core/validate-cvc "12" card-type)]
          (is (not result)))
        (let [result (core/validate-cvc "1234" card-type)]
          (is (not result))))))

  (testing "amex"
    (let [card-type "amex"]
      (testing "should be valid"
        (let [result (core/validate-cvc "123" card-type)]
          (is result)))
        (let [result (core/validate-cvc "1234" card-type)]
          (is result))
      (testing "should not be valid"
        (let [result (core/validate-cvc "12" card-type)]
          (is (not result)))
        (let [result (core/validate-cvc "12345" card-type)]
          (is (not result)))))))
