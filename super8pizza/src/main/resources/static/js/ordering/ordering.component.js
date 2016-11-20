/**
 * Created by adampermann on 10/15/16.
 */
(function () {
    'use-strict';

    var module = angular.module('ordering', []);

    module.component('ordering', {
        templateUrl: 'js/ordering/ordering.template.html',
        controller: 'OrderingController'
    });

    // register the controller with the ordering module
    module.controller('OrderingController', OrderingController);

    // inject our controller with needed dependencies
    OrderingController.$inject = ['$http', '$filter', '$location', 'orderingService', 'toastr'];

    function OrderingController($http, $filter, $location, orderingService, toastr) {
        var vm = this;

        // cart is empty until they start adding stuff
        vm.cart = [];
        vm.menu = [];
        vm.cartTotal = $filter('currency')(0, '$', 2);
        vm.deliveryOption = 0;
        vm.payOption = 0;
        vm.paymentOptions = [];
        vm.deliveryOptions = [];
        vm.cardNumber = "";
        vm.street = "";
        vm.city = "";
        vm.state = "";
        vm.zip = "";


        vm.addToCart = function(menuOption) {
            if (vm.isOptionInCart(menuOption)) {
                for (var i = 0; i < vm.cart.length; ++i) {
                    if (vm.cart[i].name == menuOption.name) {
                        vm.cart[i].quantity += menuOption.quantity;
                    }
                }
            } else {
                // option was not in the cart so add it
                vm.cart.push(angular.copy(menuOption));
                console.log(vm.cart);
            }

            // reset the menu option's quantity back to 1
            menuOption.quantity = 1;
            vm.calculateCartTotal();
        };

        vm.isOptionInCart = function(menuOption) {
            for (var i = 0; i < vm.cart.length; ++i) {
                if (vm.cart[i].name == menuOption.name) {
                    return true;
                }
            }

            return false;
        };


        vm.calculateCartTotal = function() {
            var total = 0;
            for (var i =0; i < vm.cart.length; ++i) {
                total += (vm.cart[i].price * vm.cart[i].quantity);
            }

            vm.cartTotal = $filter('currency')(total, '$', 2);
        };

        vm.clearCart = function() {
            vm.cart = [];
            vm.calculateCartTotal();
        };

        vm.isOrderValid = function () {
            // if delivery option is delivery then ensure all address fields are filled out
            // ensuere the cart is not empty

            var valid = true;

            if (vm.deliveryOption == 1) {
                if (vm.street == "" || vm.city == "" || vm.state == "" || vm.zip == "") {
                    valid = false;
                }
            } else if (vm.deliveryOption == 0) {
                valid = false;
            }

            if (vm.payOption == 2 && vm.cardNumber == "") {
                valid = false;
            } else if (vm.payOption == 0) {
                valid = false;
            }

            if (vm.cart.length == 0) {
                valid = false;
            }

            return valid;
        };

        vm.getDeliveryMethodName = function() {
            for (var i = 0; i < vm.deliveryOptions.length; ++i) {
                if (vm.deliveryOptions[i].id == vm.deliveryOption) {
                    return vm.deliveryOptions[i].name;
                }
            }
        };

        vm.getPayMethodName = function() {
            for (var i = 0; i < vm.paymentOptions.length; ++i) {
                if (vm.paymentOptions[i].id == vm.payOption) {
                    return vm.paymentOptions[i].name;
                }
            }
        };


        // validates the order
        vm.validateOrder = function(order) {

        };

        vm.placeOrder = function() {

            // create the order to post to the server
            var order = {
                "orderId": "",
                "orderNumber": 0,
                "userId": "Guest User",
                "timestamp": Date.now(),
                "orderType": {
                    "id": vm.deliveryOption,
                    "name": vm.getDeliveryMethodName()
                },
                "paymentMethod": {
                   "id": vm.payOption,
                    "name": vm.getPayMethodName()
                },
                "orderStatus": {
                    "id": 1,
                    "name": "Placed"
                },
                "cardNumber": vm.cardNumber,
                "address": {
                    "street": vm.street,
                    "city": vm.city,
                    "state": vm.state,
                    "zip": vm.zip
                },
                "orderItems": vm.cart,
                "price": vm.calculateCartTotal()
            };

            $http.post('/placeOrder', order).then(function (response) {

                if (response.status == 200) {
                    // set the order on the ordering service to the
                    // order response from the server
                    orderingService.setOrder(response.data);
                    toastr.success('Order Placed', 'Success!');
                    $location.url('/order/summary');
                } else {
                    // display an error
                    toastr.error('Error Placing Order', 'Error');
                }

            });
        };

        // initialize our controller
        activate();
        function activate() {
            $http.get('/getMenu').then(function (response) {
                vm.menu = response.data;
                console.log(vm.menu);
            });

            $http.get('/getDeliveryOptions').then(function (response) {
                vm.deliveryOptions = response.data;
            });


            $http.get('/getPaymentOptions').then(function (response) {
                vm.paymentOptions = response.data;
            });

            // initialize the cart
            vm.cart=[];
        }
    };
})();
