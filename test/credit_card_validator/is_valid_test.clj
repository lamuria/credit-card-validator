(ns credit-card-validator.is-valid-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(deftest is-valid
  (testing "visa"
    (testing "should be valid"
      (let [date (t/plus (t/now) (t/months 2))
            date-format (f/unparse core/date-formatter date)
            card "4188360002354029"
            cvc "123"]
        (is (core/is-valid? card cvc date-format))))))
