(ns homeworkjure.core
  (:require [reagent.core :as reagent :refer [atom]]
            [homeworkjure.gen :as gen]))


(enable-console-print!)

(defonce app-state
  (atom  {:score 0
          :msg "Tryck en knapp"
          :data {:term-1 "1"
                 :op     "+"
                 :term-2 "1"
                 :result "2"
                 :answer ""}}))

(defonce recognition
  (if (.hasOwnProperty js/window "webkitSpeechRecognition")
    (js/webkitSpeechRecognition.)
    js/window))
(aset recognition "lang" "sv-SE")
(aset recognition "interimResults" false)


(defn new-question []
  (swap! app-state assoc :msg "")
  (swap! app-state assoc :data (gen/gen-random))
  (.start recognition))


(defn extract-result [speech-event]
  (aget speech-event "results" 0 0 "transcript") )


(defn clean
  "If you say '6' it will be parsed as 'sex', not '6' so
  some cleaning is necessary."
  [response]
  (if (= "sex" response) "6" (str response)))

(defn msg-when-correct [response]
  (str response " är rätt!"))

(defn msg-when-wrong [response correct]
  (str "Du svarade '" response "'. Rätt svar är " correct))

(defn handle-speech-event [speech-event]
  (let [response (clean (extract-result speech-event))
        correct-answer (get-in @app-state [:data :answer])]
    (swap! app-state assoc :msg
           (if (= response correct-answer)
             (msg-when-correct response)
             (msg-when-wrong response correct-answer)))))


(aset js/document "onkeypress" new-question)
(aset recognition "onresult" handle-speech-event)

(defn frmt [{:keys [term-1 term-2 op result]}]
  (str term-1 " " op " " term-2 " = " result))

(defn math-trainer []
  (let [data (:data @app-state)]
    [:div {:class "wrapper"}
     [:div]
     [:div {:class "txt"} (frmt data)]
     [:div]
     [:div]
     [:div {:class "results"} (:msg @app-state)]
     [:div]]))

(reagent/render-component [math-trainer]
                          (. js/document (getElementById "app")))
