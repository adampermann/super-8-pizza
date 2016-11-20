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
            otherwise('/');

            // need this so spring security will play nice with the browser
            // and not constantly prompt for browser login
            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        }
    ]);


    app.controller('NavigationController', NavigationController);

    NavigationController.$inject = ['$rootScope', '$http', '$location'];

    function NavigationController($rootScope, $http, $location) {
        var vm = this;
        $rootScope.credentials = {};
        vm.error = false;
        $rootScope.authenticated = false;
        $rootScope.username = "";

        function authenticate(credentials, callback) {

            var headers = credentials ? { autorization : "Basic" + btoa(credentials.username + ":" + credentials.password) }
                : {};

            $http.get('/user', {headers : headers}).then(function(response) {

                var data = response.data;

                console.log(response);

                if (data.authenticated) {
                    $rootScope.authenticated = true;
                    $rootScope.username = data.principal.username;
                } else {
                    $rootScope.authenticated = false;
                }

                callback && callback();
            }, function() {
                $rootScope.authenticated = false;
                callback && callback();
            });


        }

        // authenticate();
        vm.login = function() {
            authenticate(self.credentials, function() {
                if ($rootScope.authenticated) {
                    $location.url('/');
                    vm.error = false;
                } else {
                    $location.url('/login');
                }

            });
        };

        vm.logout = function() {
            $http.post('/logout', {}).then(function() {
                $rootScope.authenticated = false;
                $location.url('/');
            });
        };

    };

})();
