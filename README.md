# Lab3

## Up
```
docker compose -d app database prometheus grafana --build
```
## App
Доступно на `localhost:8080`.
Метрики на `localhost:8080/metrics`
Реализация метрик
```clojure
(def registry
  (-> (prometheus/collector-registry)
      (prometheus/register
        (prometheus/counter :patients/get {:description "Number of GET requests to /patient endpoint"})
        (prometheus/counter :patients/created {:description "Number of created patients"}))))

(defn inc-created-patients []
  (prometheus/inc registry :patients/created))

(defn inc-get-patient []
  (prometheus/inc registry :patients/get))

(defn metrics-handler [_request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (export/text-format registry)})

;; далее вызовы на эндпоинтах (metrics/inc-created-patients), (metrics/inc-get-patient)
```

## Prometheus
Доступен на `localhost:3000`

## Grafana
![image](https://github.com/user-attachments/assets/ced23acf-7dc0-4c50-b4b6-cf3d48933a37)
