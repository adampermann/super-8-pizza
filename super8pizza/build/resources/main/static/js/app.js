/**
 * Created by adampermann on 10/14/16.
 */
angular.module('Super8PizzaApp', [])
 .controller('HomeController', function($http) {
     var self = this;
     $http.get('/greeting')
        .then(function (response) {
            console.log('received the response from the REST API');
            self.greeting = response.data;
        });

     console.log('inside the angular home controller');
 });