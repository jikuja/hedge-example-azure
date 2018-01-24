(set-env! :source-paths #{"src"}
          :resource-paths #{"resources"}
          :dependencies '[[org.clojure/clojurescript "1.9.908"]
                          [adzerk/boot-cljs "1.7.228-2" :scope "test"]
                          [crisptrutski/boot-cljs-test "0.3.4" :scope "test"]
                          [siili/boot-hedge "0.0.4" :scope "test"]
                          [siili/hedge "0.0.4"]])

(require '[boot-hedge.core :refer :all])
(require '[crisptrutski.boot-cljs-test :refer [test-cljs report-errors!] :as cljs-test])

(deftask testing [] (set-env! :source-paths #(conj % "test")) identity)
(ns-unmap 'boot.user 'test)

(deftask test []
  (comp (testing)
        (test-cljs :js-env :node
                   :exit?  true)))
