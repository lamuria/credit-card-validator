(ns credit-card-validator.expiry-date-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(deftest expiry-date-test
  (testing "date is valid"
    (testing "2 months after"
      (let [date (t/plus (t/now) (t/months 2))
            date-format (f/unparse core/date-formatter date)]
        (is (core/expiry-date-is-valid? date-format))))

    (testing "same month"
      (let [date (t/now)
            date-format (f/unparse core/date-formatter date)]
        (is (core/expiry-date-is-valid? date-format)))))

  (testing "date is invalid"
    (testing "1 month before"
      (let [date (t/minus (t/now) (t/months 1))
            date-format (f/unparse core/date-formatter date)]
        (is (not (core/expiry-date-is-valid? date-format)))))))
