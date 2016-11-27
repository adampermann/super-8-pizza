/**
 * Created by adampermann on 11/19/16.
 */
"use strict";

var gulp = require('gulp'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    cssmin = require('gulp-cssmin'),
    rimraf = require('rimraf');

var paths = {
   css: [
      "bower_components/bootstrap/dist/css/bootstrap.min.css",
      "bower_components/angular-bootstrap/ui-bootstrap-csp.css",
      "bower_components/angular-toastr/dist/angular-toastr.min.css",
      "bower_components/font-awesome/css/font-awesome.min.css",
      "css/site.css"
   ],
   concatCssDest: "dist/all.styles.css",
   jsLibs: [
      "bower_components/jquery/dist/jquery.min.js",
      "bower_components/bootstrap/dist/js/bootstrap.min.js",
      "bower_components/angular/angular.min.js",
      "bower_components/angular-route/angular-route.min.js",
      "bower_components/angular-cookies/angular-cookies.min.js",
      "bower_components/angular-bootstrap/ui-bootstrap.min.js",
      "bower_components/angular-toastr/dist/angular-toastr.tpls.min.js"
   ],
   concatJsLibsDest: "dist/libs.min.js",
   js: [
       "js/app.module.js",
       "js/app.config.js",
       "js/home/*.js",
       "js/order/*.js",
       "js/order_management/*.js",
       "js/inventory_management/*.js"
   ],
   concatJsDest: "dist/app.min.js"
};

gulp.task("clean:jsLibs", function(cb) {
   rimraf(paths.concatJsLibsDest, cb);
});

gulp.task("clean:js", function(cb) {
    rimraf(paths.concatJsDest, cb);
});

gulp.task("clean:css", function(cb) {
    rimraf(paths.concatCssDest, cb);
});

gulp.task("clean", ["clean:jsLibs", "clean:js", "clean:css"]);

// Min and copy
gulp.task("min:jsLibs", function() {
    return gulp.src(paths.jsLibs)
        .pipe(concat(paths.concatJsLibsDest))
        .pipe(uglify())
        .pipe(gulp.dest("."));
});

gulp.task("min:js", function() {
    return gulp.src(paths.js)
        .pipe(concat(paths.concatJsDest))
        .pipe(uglify())
        .pipe(gulp.dest("."));
});

gulp.task("min:css", function() {
    return gulp.src(paths.css)
        .pipe(cssmin())
        .pipe(concat(paths.concatCssDest))
        .pipe(gulp.dest("."));
});

gulp.task("watch", function () {
    gulp.watch(paths.js, ["default"]);
});

gulp.task("default", ["min:js", "min:jsLibs", "min:css"]);
