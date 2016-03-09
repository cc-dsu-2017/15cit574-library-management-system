gulp     = require 'gulp'
gutil    = require 'gulp-util'
shell    = require 'gulp-shell'
notify   = require 'gulp-notify'
watch    = require 'gulp-watch'
sequence = require 'gulp-watch-sequence'
dirSync  = require 'gulp-directory-sync'

notifier = require 'node-notifier'

sass     = require 'gulp-sass'

webpack       = require 'webpack'
webpackStream = require 'webpack-stream'
webpackConfig = require './webpack.config'
stripAnsi     = require 'strip-ansi'

browserSync       = require('browser-sync').create()
browserSyncConfig = require './bs-config'
reload            = browserSync.reload
# htmlInjector      = require 'bs-html-injector'
# reload            = htmlInjector

jshint = require 'jshint'

notify.logLevel 0

### ########################### Base Tasks ########################### ###

gulp.task 'default', ['build-dev']

gulp.task 'watch', ['build-dev']

# Production build
gulp.task 'build', ['sass', 'webpack:build', 'jekyll:build']

# Build and watch cycle (another option for development)
gulp.task 'build-dev', ['webpack:build-dev', 'sass:build-dev', 'browser-sync', 'watch']

gulp.task 'watch', ->
  queue = sequence(300)

  watch './assets/scripts/**/*',
    name        : 'JS'
    emitOnGlob  : false
    maxListeners: 999
  , queue.getHandler 'webpack:build-dev'

  watch './assets/styles/**/*.scss',
    name        : 'CSS'
    emitOnGlob  : false
    maxListeners: 999
  , queue.getHandler 'sass:build-dev'

  watch 'target/library-management-system-1.0-SNAPSHOT/**/*',
    name        : 'TARGET'
    emitOnGlob  : false
    maxListeners: 999
  , queue.getHandler 'browser-sync:reload'

  return

### ####################### Browser Sync Tasks ####################### ###

gulp.task 'browser-sync', ->
  # browserSync.use htmlInjector,
  #   files: "target/epls-1.0-SNAPSHOT/**/*.jsp"
  browserSync.init browserSyncConfig
  return

gulp.task 'browser-sync:reload', reload

### ########################### SASS Tasks ########################### ###

sassOptions =
  includePaths: ['./assets/styles/_sass/']

gulp.task 'sass:build-dev', ->
  sassPipeline = sass(sassOptions)
    .on 'error', sass.logError
    .on 'error', notify.onError (error) ->
      browserSync.sockets.emit 'fullscreen:message',
        title: "SASS Error:"
        body:  error.message

      title: 'SASS Error'
      # subtitle: "<%= error.plugin %>"
      message: error.message
      onLast: true

  gulp.src './assets/styles/main.scss'
  .pipe sassPipeline
  .pipe gulp.dest './src/main/webapp/styles'
  return


### ######################### WebPack Tasks ########################## ###

gulp.task 'webpack:build', (callback) ->
  # modify some webpack config options
  myConfig = Object.create webpackConfig
  myConfig.plugins = myConfig.plugins.concat(
    new webpack.DefinePlugin(
      'process.env':
        # This has effect on the react lib size
        'NODE_ENV': JSON.stringify('production')
    ),
    new webpack.optimize.DedupePlugin(),
    new webpack.optimize.UglifyJsPlugin()
  )

  # run webpack
  webpack myConfig, (err, stats) ->
    throw new gutil.PluginError('webpack:build', err) if(err)
    gutil.log '[webpack:build]', stats.toString
      colors: true
    callback()
    return
  return

# modify some webpack config options
myDevConfig = Object.create webpackConfig
myDevConfig.devtool = 'sourcemap'
myDevConfig.debug = true

# create a single instance of the compiler to allow caching
devCompiler = webpack(myDevConfig)
# send a fullscreen error message to the browser
devCompiler.plugin 'done', (stats) ->
    if stats.hasErrors() || stats.hasWarnings()
      notifier.notify
       title: 'WebPack Error'
       message: """
                No. of Errors: #{stats.compilation.errors.length}
                stats.compilation.errors[0].message
                """
      browserSync.sockets.emit 'fullscreen:message',
        title: "Webpack Error:",
        body:  stripAnsi(stats.toString()),
        timeout: 100000
      return
    # browserSync.reload();
    return

gulp.task 'webpack:build-dev', (callback) ->
  # run webpack
  devCompiler.run (err, stats) ->
    throw new gutil.PluginError('webpack:build-dev', err) if err
    gutil.log '[webpack:build-dev]', stats.toString
      colors: true
    callback()
    return
  # gulp.src webpackConfig.entry
  # .pipe webpackStream(webpackConfig).on 'error', notify.onError
  #   title: 'WebPack Error'
  #   # subtitle: "<%= error.plugin %>"
  #   message: "<%= error.message %>"
  #   onLast: true
  # .pipe gulp.dest webpackConfig.output.path

gulp.task 'webpack-dev-server', (callback) ->
  # modify some webpack config options
  myConfig = Object.create webpackConfig
  myConfig.devtool = 'eval'
  myConfig.debug = true

  # Start a webpack-dev-server
  new WebpackDevServer(webpack(myConfig),
    publicPath: '/' + myConfig.output.publicPath
    stats:
      colors: true
  ).listen 8108, 'localhost', (err) ->
    throw new gutil.PluginError('webpack-dev-server', err) if(err)
    gutil.log '[webpack-dev-server]', 'http://localhost:8108/webpack-dev-server/'
    return

### ########################## Jekyll Tasks ########################## ###

gulp.task 'jekyll:build', shell.task ['bundle exec jekyll build']
gulp.task 'jekyll:build-dev', shell.task ['bundle exec jekyll build --config _config.yml,_config.dev.yml']
gulp.task 'jekyll:watch', shell.task ['bundle exec jekyll build --config _config.yml,_config.dev.yml --watch']
gulp.task 'jekyll', ['jekyll:watch']

### ########################### Lint Tasks ########################### ###

gulp.task 'js:lint', ->
  gulp.src '/src/**/*.js'
  .pipe jshint()
  # Use gulp-notify as jshint reporter
  .pipe notify (file) ->
    return false if file.jshint.success

    errors = file.jshint.results.map (data) ->
      "(#{data.error.line}:#{data.error.character}) #{data.error.reason}" if data.error
    .join '\n'

    "#{file.relative} (#{file.jshint.results.length} errors)\n#{errors}"

  return

### ######################## File Sync Tasks ######################### ###

gulp.task 'sync-src-target', ->
  gulp.src ''
  .pipe dirSync 'src/webapp', 'target/library-management-system-1.0-SNAPSHOT', { printSummary: true, nodelete: true }
  .on('error', gutil.log);
  return

