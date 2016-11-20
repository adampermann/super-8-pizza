/**
 * Created by adampermann on 11/19/16.
 */

var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var gulpIf = require('gulp-if');

gulp.task('scripts', function() {
   return gulp.src('js/**/*.js')
       .pipe(concat('concat.js'))
       .pipe(rename('main.js'))
       .pipe(gulp.dest('dist/js'));
});


gulp.task('watch', function() {
   gulp.watch('js/**/*.js', ['scripts']);
});