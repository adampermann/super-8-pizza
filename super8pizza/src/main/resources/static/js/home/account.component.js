/**
 * Created by adampermann on 11/26/16.
 */
(function() {
    'use-strict';

    var module = angular.module('account', []);

    module.component('account', {
        templateUrl: 'js/home/account.template.html',
        controller: 'AccountController'
    });

    // register the controller with the ordering module
    module.controller('AccountController', AccountController);

    AccountController.$inject = ['$rootScope', '$http', '$location', 'Session', 'orderingService', 'toastr'];

    function AccountController($rootScope, $http, $location, Session, orderingService, toastr) {
        var vm = this;
        vm.user = $rootScope.user;  // should only be able to access this page when logged in so this should be ok
        vm.orders = [];
        vm.confirmPass = $rootScope.user.password;

        vm.updateAccount = function() {

            // check to see if confirm pass equals the user password / validate
            if (vm.user.email === undefined || vm.user.password === undefined || vm.user.username === undefined || vm.confirmPass ===  undefined) {
                toastr.error("Please enter all required fields!", "Error");

            } else if (vm.user.email.length == 0 || vm.user.username.length == 0 || vm.user.password.length == 0 || vm.confirmPass.length == 0) {
                toastr.error("Please enter all required fields!", "Error");

            } else if (vm.confirmPass != vm.user.password) {
                toastr.error("Password and confirm password don't match", "Error");
            } else {
                $http.post('/updateAccount', vm.user).then(function(response) {
                    var updatedUser = response.data;

                    // update the user on the front end
                    if (response.status == 200) {
                        $rootScope.user = updatedUser;
                        $rootScope.displayName = updatedUser.username;
                        $rootScope.authenticated = true;

                        // admin set admin flag
                        if ($rootScope.user.role.id == 2) {
                            $rootScope.isAdmin = true;
                        }

                        // store the user session
                        Session.create($rootScope.user);
                        toastr.success('Account updated successfully!', 'Success');
                    } else {
                        toastr.error('Error updating account', 'Error');
                    }
                }, function (response) {
                    toastr.error(response.data.message, 'Error');
                });
            }
        };

        vm.cancelUpdate = function() {
            // redirect back home
            $location.url('/');
        };

        vm.orderAgain = function(order) {
            orderingService.setCart(order.orderItems);
            $location.url('/ordering');
        };

        activate();
        function activate() {
            // change this to get the user's order history
            var url = '/getOrdersForUser/' + $rootScope.user.userId + '/';

            $http.get(url).then(
            function(response) {
                vm.orders = response.data;
            }, function(response){
                toastr.error(response.data.message, 'Error');
            });
        }

    }
})();