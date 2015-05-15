(ns credit-card-validator.card-from-type-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]
            [result.core :as result]))

(deftest card-from-type-test
  (testing "visa"
    (let [card-type "visa"
          result (core/card-from-type card-type)]
      (is (result/succeeded? result))
      (println "NUBU: " result)
      (is (= card-type
             (:type result)))))

  (testing "mastercard"
    (let [card-type "mastercard"
          result (core/card-from-type card-type)]
      (is (= card-type
             (:type result)))))

  (testing "amex"
    (let [card-type "amex"
          result (core/card-from-type card-type)]
      (is (= card-type
             (:type result))))))
