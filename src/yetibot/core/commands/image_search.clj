(ns yetibot.core.commands.image-search
  (:require
    [taoensso.timbre :refer [info warn error]]
    [clojure.string :as s]
    [yetibot.core.models.google-search]
    [yetibot.core.models.bing-search]
    [yetibot.core.util.http :refer [ensure-img-suffix]]
    [yetibot.core.hooks :refer [cmd-hook]]))

(defn- engine-nss
  "Sequence of configured namespaces to perform image searches on"
  []
  (filter #((ns-resolve % 'configured?))
          ['yetibot.core.models.google-search
           'yetibot.core.models.bing-search]))

(defn- fetch-image
  "Search configured namespaces starting with the first and falling back to next when
   there are no results"
  ([q] (fetch-image q (engine-nss)))
  ([q [n & nssrest]]
   (info "searching for " q "in" n)
   (let [search-fn (ns-resolve n 'image-search)
         res (search-fn q)]
     (if (and (empty? res) (not (empty? nssrest)))
       (fetch-image q nssrest) ; try another search engine
       res))))

(defn- mk-fetcher
  [f]
  (fn [{[_ q] :match}]
    (let [r (fetch-image q)]
      (if (empty? r)
        "No results :("
        (f r)))))

(def ^{:doc "image top <query> # fetch the first image from image search"
       :yb/cat #{:img}}
  top-image
  (mk-fetcher first))

(def ^{:doc "image <query> # fetch a random result from image search"
       :yb/cat #{:img}}
  image-cmd
  (mk-fetcher rand-nth))

(cmd-hook #"image"
          #"^top\s(.*)" top-image
          #"(.+)" image-cmd)
