/**
 * Created by adampermann on 11/26/16.
 */
(function() {
    'use-strict';

    var app = angular.module('Super8PizzaApp');

    app.controller('NavigationController', NavigationController);

    NavigationController.$inject = ['$rootScope', '$http', '$location', 'Session', 'orderingService', 'toastr'];

    function NavigationController($rootScope, $http, $location, Session, orderingService, toastr) {
        // init local properties
        var vm = this;
        vm.user = {
            username: "",
            password: "",
            email: "",
            joinKey: "",
            role: {},
            userId: "",
            address: {}
        };
        vm.confirmPass = "";

        // init the user data on the $rootScope of the app
        $rootScope.user = {};
        $rootScope.authenticated = false;
        $rootScope.isAdmin = false;

        vm.login = function() {
            if (vm.user.username === undefined || vm.user.password === undefined) {
                toastr.error("Please enter all required fields!", 'Error');

            } else if (vm.user.username.length == 0 || vm.user.password.length == 0) {
                toastr.error("Please enter all required fields!", 'Error');

            } else {

                authenticate(vm.user, function() {

                    if ($rootScope.authenticated) {
                        redirectOnLogin();
                    } else {
                        $location.url('/login');
                    }

                });
            }
        };

        // should not be able to register without logging out  first
        vm.register = function() {

            // check to see if confirm pass equals the user password / validate
            if (vm.user.email === undefined || vm.user.password === undefined || vm.user.username === undefined || vm.confirmPass ===  undefined) {
                toastr.error("Please enter all required fields!", "Error");

            } else if (vm.user.email.length == 0 || vm.user.username.length == 0 || vm.user.password.length == 0 || vm.confirmPass.length == 0) {
                toastr.error("Please enter all required fields!", "Error");

            } else if (vm.confirmPass != vm.user.password) {
                toastr.error("Password and confirm password don't match", "Error");

            } else {

                $http.post('/register', vm.user).then(function (response) {

                    if (response.status == 200) {
                        validLoginAttempt(response.data);

                        // go to proper location
                        redirectOnLogin();

                    } else {
                        invalidLoginAttempt(response.data.message);
                    }

                }, function (response) {
                    invalidLoginAttempt(response.data.message);
                });
            }

        };

        vm.logout = function() {

            // clear out user data on the $rootScope
            $rootScope.displayName = "";
            $rootScope.authenticated = false;
            $rootScope.isAdmin = false;

            // clear out locally stored data
            Session.destroy();
            orderingService.clearCart();
            orderingService.clearOrder();
            orderingService.clearCustomPizza();

            // display a toastr message saying logout success
            toastr.info('Successfully logged out', 'Info');

            // redirect home
            $location.url('/');
        };

        // authenticates the user with the server
        function authenticate(user, callback) {

            $http.post('/login', user).then(function(response) {

                if (response.status == 200) {
                    validLoginAttempt(response.data);
                } else {
                    invalidLoginAttempt(response.data.message);
                }

                callback && callback();
            }, function(response) {
                invalidLoginAttempt(response.data.message);
                callback && callback();
            });
        }

        // redirects on login
        function redirectOnLogin() {
            if ($rootScope.isAdmin) {
                $location.url('/order/listing')
            } else {
                $location.url('/account');
            }
        }

        // actions on valid login
        function validLoginAttempt (user) {
            $rootScope.user = user;
            $rootScope.displayName = user.username;
            $rootScope.authenticated = true;

            // admin set admin flag
            if ($rootScope.user.role.id == 2) {
                $rootScope.isAdmin = true;
            }

            // store the user session
            Session.create($rootScope.user);
        }

        // actions on invalid login
        function invalidLoginAttempt (message) {
            $rootScope.authenticated = false;
            $rootScope.isAdmin = false;
            $rootScope.displayName = "";
            toastr.error(message, 'Error');
        }

        // call activate to init the authentication when the app begins
        activate();
        function activate() {

            // when first navigating to the page see if we have a user stored
            // if so attempt to login as that user.
            var storedUser =  Session.getCurrentUser();
            if (storedUser != null) {
                authenticate(storedUser, function() {
                    if ($rootScope.authenticated) {
                        $location.url('/');
                    }
                })
            }
        }
    }
})();