(ns homeworkjure.core
  (:require [reagent.core :as reagent :refer [atom]]
            [clojure.string :refer [lower-case]]
            [homeworkjure.gen :as gen]))


(enable-console-print!)

(defonce app-state
  (atom  {:score 0
          :msg "Tryck en knapp"
          :q "-"
          :answer ""}))

(defonce recognition
  (if (.hasOwnProperty js/window "webkitSpeechRecognition")
    (js/webkitSpeechRecognition.)
    js/window))
(aset recognition "lang" "sv-SE")
(aset recognition "interimResults" false)


(defn new-question []
  (swap! app-state merge {:msg ""} (gen/gen-random))
  (.start recognition))


(defn extract-result [speech-event]
  (aget speech-event "results" 0 0 "transcript") )


(defn clean
  "If you say '6' it will be parsed as 'sex', not '6' so
  some cleaning is necessary."
  [response]
  (case (lower-case response)
    "sex" "6"
    "mio" "9"
    response))


(defn handle-correct-answer [state response]
  (assoc state
         :msg (str response " är rätt!")
         :q (clojure.string/replace (:q state) "?" (:answer state))
         :score (inc (:score state))))


(defn handle-wrong-answer [state response]
  (assoc state
         :q (clojure.string/replace (:q state) "?" (:answer state))
         :msg (str "Du svarade '" response "'. Rätt svar är " (:answer state))))


(defn handle-speech-event [speech-event]
  (let [response (-> speech-event extract-result clean)
        correct? (= response (:answer @app-state))]
    (if correct?
      (swap! app-state handle-correct-answer response)
      (swap! app-state handle-wrong-answer response))))


(aset js/document "onkeypress" new-question)
(aset recognition "onresult" handle-speech-event)


(defn math-trainer []
  (let [{:keys [q score msg]} @app-state]
    [:div {:class "wrapper"}
     ;; Row 1
     [:div]
     [:div {:class "score"} (str "Poäng: " score)]
     [:div]
     ;; Row 2
     [:div]
     [:div {:class "txt"} q]
     [:div]
     ;; Row 3
     [:div]
     [:div {:class "results"} msg]
     [:div]]))


(reagent/render-component
 [math-trainer]
 (. js/document (getElementById "app")))
