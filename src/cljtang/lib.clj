(ns cljtang.lib
  (:require potemkin
            [plumbing core]
            [bultitude.core])
  (:require [cljtang core util runtime]))

(potemkin/import-vars
 [potemkin
  import-vars
  import-fn
  import-macro
  import-def]

 [plumbing.core
  for-map
  safe-get
  safe-get-in
  map-vals
  map-keys
  ?>
  ?>>
  dissoc-in
  keywordize-map]

 [bultitude.core
  namespaces-on-classpath]

 [cljtang.lib
  domap
  when-not->
  named?
  defdynamic
  when-nil
  abs
  mixin-ns
  format-date
  doeach
  if-nil
  *default-date-pattern*
  not-nil->
  not-nil?
  if-not-nil
  moment-format
  stacktrace->string
  fn-*
  more-args->map
  each
  when->
  moment
  maplist-with-no
  nil->
  substring
  when-not-nil
  map->kv-pairs]
 
 [cljtang.util
  uuid
  repeat-str])
