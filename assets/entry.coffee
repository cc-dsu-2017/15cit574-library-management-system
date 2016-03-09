require [
  'script!./scripts/lib/modernizr-custom.min.js'
  'jquery'
],
(modernizr, jQuery) ->
  require [
    'bootstrap'
    'jquery.easing'
    './scripts/lib/jquery.fittext.js'
    './scripts/lib/material.js'
    'script!./scripts/lib/wow.min.js'
  ],
  (easing) ->
    require './scripts/lib/creative.js'
    require './scripts/main'
    return
  return
