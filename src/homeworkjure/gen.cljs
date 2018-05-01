(ns homeworkjure.gen)


(defn gen-add
  "Generates e.g. a+b=?."
  []
  (let [term-1 (inc (rand-int 8))
        term-2 (inc (rand-int 8))
        answer (+ term-1 term-2)]
    {:term-1 (str term-1)
     :op  "+"
     :term-2 (str term-2)
     :result    "?"
     :answer    (str answer)}))

(defn gen-add-x
  "Generates e.g. a+?=x."
  []
  (let [term-1 (inc (rand-int 8))
        term-2 (inc (rand-int 8))
        answer (+ term-1 term-2)]
    {:term-1 (str term-1)
     :op  "+"
     :term-2 "?"
     :result    (str answer)
     :answer    (str term-2)}))


(defn gen-sub
  "Generates a-b=? where a>b"
  []
  (let [term-2 (rand-int 5)
        term-1 (+ term-2 (rand-int 5))
        answer (- term-1 term-2)]
    {:term-1 (str term-1)
     :op  "-"
     :term-2 (str term-2)
     :result    "?"
     :answer    (str answer)}))


(defn gen-sub-x
  "Generates a-?=x where x>0"
  []
  (let [term-2 (rand-int 5)
        term-1 (+ term-2 (rand-int 5))
        answer (- term-1 term-2)]
    {:term-1 (str term-1)
     :op  "-"
     :term-2 "?"
     :result    (str answer)
     :answer    (str term-2)}))



(defn gen-random [] (let [generators [#'gen-add #'gen-add-x #'gen-sub #'gen-sub-x]] (apply (rand-nth generators) nil)))


