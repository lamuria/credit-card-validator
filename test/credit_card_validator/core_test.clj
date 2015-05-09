(ns credit-card-validator.core-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]))

(deftest card-from-number-test
  (testing "should be visa"
    (let [card "4188360002354029"
          result (core/card-from-number card)]
      (is (= "visa"
             (:type result)))))

  (testing "should be mastercard"
    (testing "starts with 51"
      (let [card "5188360002354029"
            result (core/card-from-number card)]
        (is (= "mastercard"
               (:type result)))))
    (testing "starts with 52"
      (let [card "5288360002354029"
            result (core/card-from-number card)]
        (is (= "mastercard"
               (:type result)))))
    (testing "starts with 53"
      (let [card "5188360002354029"
            result (core/card-from-number card)]
        (is (= "mastercard"
               (:type result)))))
    (testing "starts with 54"
      (let [card "5188360002354029"
            result (core/card-from-number card)]
        (is (= "mastercard"
               (:type result)))))
    (testing "starts with 55"
      (let [card "5188360002354029"
            result (core/card-from-number card)]
        (is (= "mastercard"
               (:type result)))))))
