(ns credit-card-validator.expiry-date-test
  (:require [clojure.test :refer :all]
            [credit-card-validator.core :as core]
            [result.core :as result]
            [clj-time.core :as t]
            [clj-time.format :as f]))

(deftest expiry-date-test
  (testing "date is valid"
    (testing "2 months after"
      (let [date (t/plus (t/now) (t/months 2))
            date-format (f/unparse core/date-formatter date)
            result (core/expiry-date-is-valid? date-format)]
        (is (result/succeeded? result))))

    (testing "same month"
      (let [date (t/now)
            date-format (f/unparse core/date-formatter date)
            result (core/expiry-date-is-valid? date-format)]
        (is (result/succeeded? result)))))

  (testing "date is invalid"
    (testing "1 month before"
      (let [date (t/minus (t/now) (t/months 1))
            date-format (f/unparse core/date-formatter date)
            result (core/expiry-date-is-valid? date-format)]
        (is (result/failed? (core/expiry-date-is-valid? date-format)))))

    (testing "invalid format"
      (let [date-format "20/1121"
            result (core/expiry-date-is-valid? date-format)]
        (is (result/failed? result))
        (is (= "invalid date"
               (:errors result)))))))
