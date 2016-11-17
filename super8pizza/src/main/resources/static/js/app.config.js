/**
 * Created by adampermann on 10/22/16.
 */
var app = angular.module('Super8PizzaApp');

app.config(['$locationProvider', '$routeProvider',
    function config($locationProvider, $routeProvider) {

        $locationProvider.html5Mode(true);

        // for when users start using this it could be /ordering/:userId?
        $routeProvider
            .when('/', {
                templateUrl: 'js/home/home.template.html'
            }).
            when('/ordering', {
                template: '<ordering></ordering>'
            }).
            when('/order/summary', {
                template: '<order-summary></order-summary>'
            }).
            when('/order/listing', {
                template: '<order-listing></order-listing>'
            }).
            when('/inventory/listing', {
                template: '<inventory-listing></inventory-listing>'
            }).
            otherwise('/');
    }
]);