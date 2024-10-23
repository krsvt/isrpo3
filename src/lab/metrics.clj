(ns lab.metrics
  (:require [iapetos.core :as prometheus]
            [iapetos.export :as export]))


;; Создание регистра метрик
(def registry
  (-> (prometheus/collector-registry)
      (prometheus/register
        (prometheus/counter :patients/get {:description "Number of GET requests to /patient endpoint"})
        (prometheus/counter :patients/created {:description "Number of created patients"})
        ;; Гистограмма для времени выполнения запросов
        (prometheus/histogram :http/request-duration-seconds {:description "Request duration in seconds"}))))

(defn inc-created-patients []
  (prometheus/inc registry :patients/created))

;; Функция для записи времени выполнения запроса
(defn observe-request-duration [duration]
  (prometheus/observe registry :http/request-duration-seconds duration))


(defn inc-get-patient []
  (prometheus/inc registry :patients/get))

(defn metrics-handler [_request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (export/text-format registry)})
