/**
 * Created by adampermann on 10/22/16.
 */
(function() {
    'use-strict';

    var app = angular.module('Super8PizzaApp');

    app.config(['$locationProvider', '$routeProvider', '$httpProvider',
        function config($locationProvider, $routeProvider, $httpProvider) {

            $locationProvider.html5Mode(true);

            // for when users start using this it could be /ordering/:userId?
            $routeProvider.when('/', {
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
            when('/order/history', {
                template: '<order-history></order-history>'
            }).
            when('/inventory/listing', {
                template: '<inventory-listing></inventory-listing>'
            }).
            when('/order/custom', {
                template: '<custom-order></custom-order>'
            }).
            when('/login', {
                controller: 'NavigationController',
                templateUrl: 'js/home/login.template.html',
                controllerAs: '$ctrl'
            }).
            when('/register', {
                controller: 'NavigationController',
                templateUrl: 'js/home/register.template.html',
                controllerAs: '$ctrl'
            }).
            when('/account', {
                template: '<account></account>'
            }).
            otherwise('/');

            // need this so spring security will play nice with the browser
            // and not constantly prompt for browser login
            // $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
            // FUCK SPRING SECURITY IT SUCKS!
            // that doesn't work and still prompts browser login
        }
    ]).config(function (toastrConfig) {
        angular.extend(toastrConfig, {
            autoDismiss: false,
            containerId: 'toast-container',
            maxOpened: 0,
            newestOnTop: true,
            positionClass: 'toast-top-center',
            preventDuplicates: false,
            preventOpenDuplicates: false,
            target: 'body'
        });
    }).service('Session', ['$cookies', function($cookies) {
        // create and destroy stored user information
        var vm = this;

        vm.create = function(user) {
            var userCookie = {
                user: user
            };
            $cookies.putObject('user', userCookie);
        };

        vm.destroy = function() {
            $cookies.putObject('user', null);
        };

        vm.getCurrentUser = function() {
            var userCookie = $cookies.getObject('user');

            if (userCookie == null || userCookie === undefined) {
                return null;
            }

            return userCookie.user;
        };

    }]);

    // since for some reason the server won't allow manual navigation of routes
    // because the way it is configured blows
    // app.run(['$rootScope', '$location', function ($rootScope, $location) {
    //     $rootScope.$on('$routeChangeStart', function(event, next, current) {
    //
    //     });
    // }]);

})();
