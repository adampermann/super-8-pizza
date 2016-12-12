/**
 * Created by adampermann on 10/15/16.
 */
(function () {
    'use-strict';

    var module = angular.module('order');

    module.component('ordering', {
        templateUrl: 'js/order/ordering.template.html',
        controller: 'OrderingController'
    });

    // register the controller with the ordering module
    module.controller('OrderingController', OrderingController);

    // inject our controller with needed dependencies
    OrderingController.$inject = ['$rootScope', '$http', '$filter', '$location', 'orderingService', 'toastr'];

    function OrderingController($rootScope, $http, $filter, $location, orderingService, toastr) {
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
            if (vm.isOptionInCart(menuOption) && menuOption.name != "Custom Pizza") {
                for (var i = 0; i < vm.cart.length; ++i) {
                    if (vm.cart[i].name == menuOption.name) {
                        vm.cart[i].quantity += menuOption.quantity;
                    }
                }
            } else {
                // option was not in the cart so add it
                vm.cart.push(angular.copy(menuOption));
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

        // redirect to customize pizza page
        // save the state of the app
        vm.customizePizza = function() {

            // just save cart or entire order?
            // if entire order, something has to change from the
            orderingService.setCart(vm.cart);
            $location.url('/order/custom');
        };

        // validates the order
        // vm.validateOrder = function(order) {
        //
        // };

        vm.placeOrder = function() {

            // create the order to post to the server
            var order = createOrderFromModel();

            $http.post('/placeOrder', order).then(function (response) {

                if (response.status == 200) {
                    // update the ordering service
                    orderingService.setOrder(response.data);
                    orderingService.clearCart();
                    orderingService.clearCustomPizza();

                    // success and redirect
                    toastr.success('Order Placed', 'Success!');
                    $location.url('/order/summary');
                } else {
                    // display an error
                    toastr.error('Error placing order', 'Error');
                }

            }, function (response) {
                // display an error
                toastr.error(response.data.message, 'Error');
            });
        };

        function createOrderFromModel() {
            var order = {
                "orderId": "",
                "orderNumber": 0,
                "userId": angular.equals($rootScope.user, {}) ? "Guest User" : $rootScope.user.userId,
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

            return order;
        }

        // restores an order if the page is navigated back to
        // and a user had a previous session without placing the order
        // function restoreOrder() {
        //     var order = orderingService.getOrder();
        //     if (order != null) {
        //         vm.deliveryOption = order.orderType.id;
        //         vm.payOption = order.paymentMethod.id;
        //         vm.cardNumber = order.cardNumber;
        //         vm.street = order.address.street;
        //         vm.city = order.address.city;
        //         vm.state = order.address.zip;
        //         vm.cart = order.orderItems;
        //     }
        // }

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

            // initialize the items from the ordering service if possible
            var savedCart = orderingService.getCart();
            var customPizza = orderingService.getCustomPizza();


            if (savedCart != null) {
                vm.cart = savedCart;
            } else {
                vm.cart = [];
            }

            if (customPizza != null) {
                vm.cart.push(customPizza);

                // clear the saved custom pizza
                orderingService.clearCustomPizza();
            }

            // calculate the total incase we have items in the cart
            vm.calculateCartTotal();

            // initialize the address from the logged in user's addy
            if ($rootScope.authenticated) {
                var address = $rootScope.user.address;

                // the address isn't empty so auto fill
                if (!angular.equals({}, address)) {
                    vm.street = address.street == null ? "" : address.street;
                    vm.city = address.city == null ? "" : address.city;
                    vm.state = address.state == null ? "" : address.state;
                    vm.zip = address.zip == null ? "" : address.zip;
                }
            }
        }
    }
})();
