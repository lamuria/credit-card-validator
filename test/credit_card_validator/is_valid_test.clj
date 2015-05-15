(ns credit-card-validator.is-valid-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]))

(deftest is-valid
  (testing "visa"
    (let [card "4188360002354029"
          cvc "123"]
      (testing "should be valid"
        (is (core/is-valid? card cvc "DATE"))))))
