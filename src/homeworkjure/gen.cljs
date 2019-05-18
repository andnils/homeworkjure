(ns homeworkjure.gen)

(defn frmt [term-1 op term-2 result]
  (str term-1 " " op " " term-2 " = " result))


;; TODO: maybe use spec generators?

(defn gen-add
  "Generates e.g. a+b=?."
  []
  (let [term-1 (inc (rand-int 8))
        term-2 (inc (rand-int (- 10 term-1)))
        answer (+ term-1 term-2)]
    {:q (frmt term-1 "+" term-2 "?")
     :answer (str answer)}))

(defn gen-add-x
  "Generates e.g. a+?=x."
  []
  (let [term-1 (inc (rand-int 8))
        term-2 (inc (rand-int 8))
        answer (+ term-1 term-2)]
    {:q (frmt term-1 "+" "?" answer)
     :answer (str term-2)}))


(defn gen-sub
  "Generates a-b=? where a>b"
  []
  (let [term-2 (rand-int 5)
        term-1 (+ term-2 (rand-int 5))
        answer (- term-1 term-2)]
    {:q (frmt term-1 "-" term-2 "?")
     :answer (str answer)}))


(defn gen-sub-x
  "Generates a-?=x where x>0"
  []
  (let [term-2 (rand-int 5)
        term-1 (+ term-2 (rand-int 5))
        answer (- term-1 term-2)]
    {:q (frmt term-1 "-" "?" answer)
     :answer (str term-2)}))

(defn gen-mul
  "Higher order function for generating
  a certain multiplikationstabell."
  [i]
  (fn []
    (let [term (inc (rand-int 10))
          answer (* i term)]
      {:q (frmt i "×" term "?")
       :answer (str answer)})))

(def gen-mul-1 (gen-mul 1))
(def gen-mul-2 (gen-mul 2))
(def gen-mul-5 (gen-mul 5))
(def gen-mul-10 (gen-mul 10))

(defn gen-random []
  (let
      [generators [#'gen-mul-1
                   #'gen-mul-2
                   #'gen-mul-5
                   #'gen-mul-10]]
    (apply (rand-nth generators) nil)
    ;; (gen-add)
    ))  


