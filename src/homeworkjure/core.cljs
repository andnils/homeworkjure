(ns homeworkjure.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(def DELAY 50) ;; msec

(defonce app-state
  (atom {:op1 3
         :op2 7
         :operand "+"}))

(defonce recognition (js/webkitSpeechRecognition.))
(aset recognition "lang" "sv-SE")
(aset recognition "interimResults" false)


(defn spin [i]
  (let [op1 (inc (rand-int 9))
        op2 (inc (rand-int 9))]
    (swap! app-state merge {:op1 ops1 :op2 op2})
    (if (pos? i)
      (.setTimeout js/window spin DELAY (dec i))
      (.start recognition))))


(defn get-correct-answer []
  (let [{:keys [op1 op2]} @app-state]
    (+ op1 op2)))


(defn extract-result [speech-event]
  (aget speech-event "results" 0 0 "transcript") )


(defn clean
  "If you say '6' it will be parsed as 'sex', not '6' so
  some cleaning is necessary."
  [response]
  (if (= "sex" response) "6" (str response)))


(defn handle-speech-event [speech-event]
  (let [response (clean (extract-result speech-event))
        correct-answer (get-correct-answer)]
    (swap! app-state assoc :result
           (if (= response (str correct-answer))
             (str response " är rätt!")
             (str "Du svarade " response ". Rätt svar är " correct-answer)))))


(aset js/document "onkeypress" #(do
                                  (swap! app-state assoc :result "")
                                  (.setTimeout js/window spin DELAY 10)))


(aset recognition "onresult" handle-speech-event)


(defn math-trainer []
  [:div {:id "wrapper"}
   [:div {:id "nummer1" :class "nummers"} (:op1 @app-state)]
   [:div {:id "operator"} (:operand @app-state)]
   [:div {:id "nummer2" :class "nummers"} (:op2 @app-state)]
   [:div {:id "result" :class "result"} (:result @app-state)]])

(reagent/render-component [math-trainer]
                          (. js/document (getElementById "app")))


