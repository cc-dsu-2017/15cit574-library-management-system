webpack = require 'webpack'

module.exports =
  context: __dirname
  entry: './assets/entry'
  output:
    path: __dirname + '/src/main/webapp/scripts'
    publicPath: '/scripts/'
    filename: './main.js'
    chunkFilename: '[chunkhash].js'
  module:
    preLoaders: [
      { test: /\.js$/, exclude: /(node_modules|lib)/, loader: 'jshint-loader' }
    ]
    loaders: [
      { test: /\.css$/, loader: 'style!css' }
      { test: /\.coffee$/, loader: 'coffee-loader' }
      { test: /\.(coffee\.md|litcoffee)$/, loader: 'coffee-loader?literate' }
      { test: /jquery\/src\/selector\.js$/, loader: 'amd-define-factory-patcher-loader' }
    ]
  resolve:
    # alias: jquery: './assets/bower_components/jquery/dist/jquery.min.js'
    extensions: [
      ''
      '.webpack.js'
      '.web.js'
      '.js'
      '.coffee'
    ]
  plugins: [
    new webpack.ProvidePlugin
      $: "jquery"
      jQuery: "jquery"
      "window.jQuery": "jquery"
  ]
  devtool: 'source-map'
