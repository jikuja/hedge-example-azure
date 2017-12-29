(ns handler.core
  (:require [cljs.nodejs]
            [cljs.core.async :refer [chan]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def operators {"+" +
                "-" -
                "*" *
                "/" /})

(def leftpad (cljs.nodejs/require "leftpad"))

(defn extract-query-parameter
  "Extracts query parameter from req. Returns nil if querystring or parameter is empty"
  ([req parameter default]
   (let [querystring (cljs.nodejs/require "querystring")
         query-string (get req :query-string)]
       (cond
         (nil? query-string) default
         :else
         (goog.object/get (.parse querystring query-string) parameter default))))
  ([req parameter]
   (extract-query-parameter req parameter nil)))

(defn calc
  [req]
  (let [operator (extract-query-parameter req "op" nil)
        value1 (-> (extract-query-parameter req "value1" nil) js/parseInt)
        value2 (-> (extract-query-parameter req "value2" nil) js/parseInt)]
    (cond
      (or (nil? operator) (= js/NaN value1) (= js/NaN value2))
      (throw (js/Error. "Missing input parameter!"))
      (contains? operators operator)
      (str ((get operators operator) value1 value2))
      :else
      (throw (js/Error. "Bad operator type!")))))

(defn hello
  [req]
  "hello!")

(defn hello-json
  [req]
  {:body {:key1 "value1" :key2 (leftpad 42 5)}})

(defn fail-hard
  [req]
  (go
    (throw (js/Error. "Fail hard! Hedge does not catch this? And AWS/Azure does not handle this properly"))
    "Fake return value which should be handled by hedge."))
