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
    OrderingController.$inject = ['$http', '$filter', '$location', 'orderingService'];

    function OrderingController($http, $filter, $location, orderingService) {
        var vm = this;

        // cart is empty until they start adding stuff
        vm.cart = [];
        vm.menu = [];
        vm.cartTotal = $filter('currency')(0, '$', 2);
        vm.deliveryOption = 0;
        vm.payOption = 0;
        vm.payOptions = [];
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
                vm.cart.push({"name": menuOption.name, "price": menuOption.price, "quantity": menuOption.quantity});
            }

            // reset the menu option's quantity back to 1
            menuOption.quantity = 1;
            vm.calculateCartTotal()
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

            console.log(valid);
            return valid;
        };


        // takes whatever is in the cart and posts it to the server
        // to process the order
        vm.placeOrder = function() {

            var order = {
                "deliveryMethodId": vm.deliveryOption,
                "payMethodId": vm.payOption,
                "address": {
                    "street": vm.street,
                    "city": vm.city,
                    "state": vm.state,
                    "zip": vm.zip
                },
                "contents": vm.cart
            };

            orderingService.setOrder(order);

            $location.url('/order/summary');

            // now it should redirect to the order summary page

        };

        // initialize our controller
        activate();
        function activate() {
            $http.get('/getMenu').then(function (response) {
                console.log('getting menu');
                vm.menu = response.data;

                // console.log('adding quantity to the menu opts');
                //
                // for (var i = 0; i < vm.menu.length; ++i) {
                //     menu[i]["quantity"] = 1;
                // }

                console.log(vm.menu);
            });

            $http.get('js/ordering/orderOptions.json').then(function (response) {
                vm.paymentOptions = response.data.payOpts;
                vm.deliveryOptions = response.data.deliveryOpts;

                console.log('pay opts: ' + vm.paymentOptions);
            });

            // $http.get('/getDeliveryOptions').then(function (response) {
            //     vm.deliveryOptions = response.data;
            //
            //     console.log('deliv opts: ' + vm.deliveryOptions);
            // });


            // $http.get('/getPaymentOptions').then(function (response) {
            //     vm.paymentOptions = response.data;
            //
            //     console.log('pay opts: ' + vm.paymentOptions);
            // });

            // initialize the cart
            vm.cart=[];
        }
    };
})();
