const gulp = require('gulp');
const browserify = require('browserify');
const babelify = require('babelify');
const envify = require('envify/custom');
const source = require('vinyl-source-stream');
const buffer = require('vinyl-buffer');
const { deleteAsync } = require('del');
const log = require('fancy-log');
const sourcemaps = require('gulp-sourcemaps');
const uglify = require('gulp-uglify');
const browserSync = require('browser-sync').create();
const replace = require('gulp-replace');
const rename = require('gulp-rename');
const gulpif = require('gulp-if');

const variant = process.env.VARIANT || 'desktop';
const isProduction = process.env.NODE_ENV === 'production';

if (!['desktop', 'mobile'].includes(variant)) {
  throw new Error(`Invalid VARIANT: ${variant}. Expected "desktop" or "mobile".`);
}

const outRoot = isProduction ? 'dist/prod' : 'dist/dev';
const outputDir = `${outRoot}/${variant}`;

const paths = {
  html: {
    src: 'index.template.html',
    dest: outputDir
  },
  js: {
    entry: './src/app.js',
    dest: outputDir
  },
  libs: {
    dev: './libs/pixi.js',
    prod: './libs/pixi.min.js',
    dest: outputDir
  },
  assets: {
    src: [`assets/common/**/*`, `assets/${variant}/**/*`],
    dest: `${outputDir}/assets`
  }
};

function html() {
  const pixiFile = isProduction ? 'pixi.min.js' : 'pixi.js';
  const bundleFile = isProduction ? 'bundle.min.js' : 'bundle.js';

  const pixiTag = `<script src="${pixiFile}"></script>`;
  const bundleTag = `<script src="${bundleFile}"></script>`;

  return gulp.src(paths.html.src)
    .pipe(replace('<!-- PIXI_SCRIPT -->', `${pixiTag}\n${bundleTag}`))
    .pipe(rename('index.html'))
    .pipe(gulp.dest(paths.html.dest));
}

function copyPixi() {
  const pixiSrc = isProduction ? paths.libs.prod : paths.libs.dev;
  return gulp.src(pixiSrc)
    .pipe(rename(`pixi${isProduction ? '.min' : ''}.js`))
    .pipe(gulp.dest(paths.libs.dest));
}

function copyAssets() {
  return gulp.src(paths.assets.src, { allowEmpty: true })
    .pipe(gulp.dest(paths.assets.dest));
}

function buildJS() {
  const b = browserify({
    entries: paths.js.entry,
    debug: !isProduction
  });

  b.transform(envify({
    VARIANT: variant,
    NODE_ENV: isProduction ? 'production' : 'development'
  }), { global: true });

  b.transform(babelify, {
    presets: [[
      '@babel/preset-env',
      {
        targets: 'defaults',
        useBuiltIns: 'usage',
        corejs: 3
      }
    ]],
    extensions: ['.js']
  });

  return b.bundle()
    .on('error', function (err) {
      log.error('Browserify Error:', err.message);
      this.emit('end');
    })
    .pipe(source(isProduction ? 'bundle.min.js' : 'bundle.js'))
    .pipe(buffer())
    .pipe(gulpif(!isProduction, sourcemaps.init({ loadMaps: true })))
    .pipe(gulpif(isProduction, uglify()))
    .pipe(gulpif(!isProduction, sourcemaps.write('./')))
    .pipe(gulp.dest(paths.js.dest))
    .pipe(gulpif(!isProduction, browserSync.stream()));
}

function clean() {
  return deleteAsync([`${outputDir}/**/*`]);
}

function serve() {
  browserSync.init({
    server: {
      baseDir: `./dist/dev/${variant}`,
      index: 'index.html'
    },
    port: 3000
  });
}

function watch() {
  gulp.watch('./src/**/*.js', gulp.series(buildJS));
  gulp.watch('index.template.html', gulp.series(html));
  gulp.watch([`assets/common/**/*`, `assets/${variant}/**/*`], gulp.series(copyAssets));
  gulp.watch([`./dist/dev/${variant}/**/*`]).on('change', browserSync.reload);
}

const build = gulp.series(
  clean,
  gulp.parallel(copyPixi, copyAssets, html, buildJS)
);

const dev = gulp.series(
  build,
  gulp.parallel(serve, watch)
);

exports.clean = clean;
exports.html = html;
exports.assets = copyAssets;
exports.build = build;
exports.dev = dev;
exports.default = isProduction ? build : dev;
