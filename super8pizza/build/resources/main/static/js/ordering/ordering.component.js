/**
 * Created by adampermann on 10/15/16.
 */
(function () {
    'use-strict';

    var module = angular.module('ordering');

    module.component('ordering', {
        templateUrl: 'js/ordering/ordering.template.html',
        controller: 'OrderingController'
    });

    // register the controller with the ordering module
    module.controller('OrderingController', OrderingController);

    // inject our controller with needed dependencies
    OrderingController.$inject = ['$http', '$filter', '$location', 'orderingService'];

    function OrderingController($http, $filter, $location, orderingService) {
        var self = this;

        // cart is empty until they start adding stuff
        self.cart = [];
        self.menu = [];
        self.cartTotal = $filter('currency')(0, '$', 2);
        self.deliveryOption = 0;
        self.deliveryOptions = [];
        self.street = "";
        self.city = "";
        self.state = "";
        self.zip = "";


        self.addToCart = function(menuOption) {
            if (self.isOptionInCart(menuOption)) {
                for (var i = 0; i < self.cart.length; ++i) {
                    if (self.cart[i].name == menuOption.name) {
                        self.cart[i].quantity += menuOption.quantity;
                    }
                }
            } else {
                // option was not in the cart so add it
                self.cart.push({"name": menuOption.name, "price": menuOption.price, "quantity": menuOption.quantity});
            }

            // reset the menu option's quantity back to 1
            menuOption.quantity = 1;
            self.calculateCartTotal()
        };

        self.isOptionInCart = function(menuOption) {
            for (var i = 0; i < self.cart.length; ++i) {
                if (self.cart[i].name == menuOption.name) {
                    return true;
                }
            }

            return false;
        };


        self.calculateCartTotal = function() {
            var total = 0;
            for (var i =0; i < self.cart.length; ++i) {
                total += (self.cart[i].price * self.cart[i].quantity);
            }

            self.cartTotal = $filter('currency')(total, '$', 2);
        };

        self.clearCart = function() {
            self.cart = [];
            self.calculateCartTotal();
        };

        self.isOrderValid = function () {
            // if delivery option is delivery then ensure all address fields are filled out
            // ensuere the cart is not empty

            var valid = true;

            if (self.deliveryOption == 1) {
                if (self.street == "" || self.city == "" || self.state == "" || self.zip == "") {
                    valid = false;
                }
            } else if (self.deliveryOption == 0) {
                valid = false;
            }

            if (self.cart.length == 0) {
                valid = false;
            }

            console.log(valid);
            return valid;
        };


        // takes whatever is in the cart and posts it to the server
        // to process the order
        self.placeOrder = function() {

            var order = {
                "deliveryMethodId": self.deliveryOption,
                "address": {
                    "street": self.street,
                    "city": self.city,
                    "state": self.state,
                    "zip": self.zip
                },
                "contents": self.cart
            };

            orderingService.setOrder(order);

            $location.url('/order/summary');

            // now it should redirect to the order summary page

        };

        // initialize our controller
        activate();
        function activate() {
            $http.get('js/ordering/menu.json').then(function (response) {
                self.menu = response.data;
            });

            $http.get('js/ordering/deliveryOpts.json').then(function (response) {
                self.deliveryOptions = response.data
            });

            self.cart=[];
        }
    };
})();
