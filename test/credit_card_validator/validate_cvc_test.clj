(ns credit-card-validator.validate-cvc-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]
            [result.core :as result]))

(deftest validate-cvc-test
  (testing "visa"
    (let [card-type "visa"]
      (testing "should be valid"
        (let [result (core/cvc-is-valid? "123" card-type)]
          (is (result/succeeded? result))))
      (testing "should not be valid"
        (let [result (core/cvc-is-valid? "12" card-type)]
          (is (result/failed? result)))
        (let [result (core/cvc-is-valid? "1234" card-type)]
          (is (result/failed? result))))))

  (testing "mastercard"
    (let [card-type "mastercard"]
      (testing "should be valid"
        (let [result (core/cvc-is-valid? "123" card-type)]
          (is (result/succeeded? result))))
      (testing "should not be valid"
        (let [result (core/cvc-is-valid? "12" card-type)]
          (is (result/failed? result)))
        (let [result (core/cvc-is-valid? "1234" card-type)]
          (is (result/failed? result))))))

  (testing "amex"
    (let [card-type "amex"]
      (testing "should be valid"
        (let [result (core/cvc-is-valid? "123" card-type)]
          (is (result/succeeded? result))))
        (let [result (core/cvc-is-valid? "1234" card-type)]
          (is (result/succeeded? result)))
      (testing "should not be valid"
        (let [result (core/cvc-is-valid? "12" card-type)]
          (is (result/failed? result)))
        (let [result (core/cvc-is-valid? "12345" card-type)]
          (is (result/failed? result)))))))
