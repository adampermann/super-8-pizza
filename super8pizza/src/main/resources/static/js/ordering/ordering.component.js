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
                vm.cart.push({
                    "id": menuOption.id,
                    "name": menuOption.name,
                    "price": menuOption.price,
                    "quantity": menuOption.quantity
                });
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

            console.log('order is ' + valid);
            console.log('card number is ' + vm.cardNumber);
            return valid;
        };


        // takes whatever is in the cart and posts it to the server
        // to process the order
        vm.placeOrder = function() {

            // private String orderId;
            // private long orderNumber;
            // private String userId;
            // private Date timestamp;
            // private OrderType orderType;
            // private PaymentMethod paymentMethod;
            // private Address address;
            // private Map<String, Integer> orderItems;
            // private OrderStatus orderStatus;

            var orderItems = {};

            for (var i = 0; i < vm.cart.length; i++) {
                console.log(vm.cart[i].id);
                orderItems[vm.cart[i].id] = vm.cart[i].quantity;
                console.log(orderItems[vm.cart[i].id]);
            }

            //console.log('order items are: ');

            var order = {
                "orderId": "",
                "orderNumber": 0,
                "userId": "guest user",
                "timestamp": Date.now(),
                "orderType": vm.deliveryOption,
                "paymentMethod": vm.payOption,
                "address": {
                    "street": vm.street,
                    "city": vm.city,
                    "state": vm.state,
                    "zip": vm.zip
                },
                "orderItems": orderItems
            };

            console.log(order);
            //
            // public class OrderSuccessModel {
            //
            //     public boolean success;
            //
            //     public string message;
            //
            //     public string orderId/number /whatever
            // }

            // $http.post('/placeOrder').then(function (response) {
            //     if (response.data.success) {
            //         // we are good and we can pass the order id to the summary page
            //     } else {
            //         // the order didn't place display an error
            //         // or something on the top of the page
            //     }
            // });

            // maybe take this out and pass a route param of the order id
            orderingService.setOrder(order);

            $location.url('/order/summary');
        };

        // initialize our controller
        activate();
        function activate() {
            $http.get('/getMenu').then(function (response) {
                console.log('getting menu');
                vm.menu = response.data;
                console.log(vm.menu);
            });

            $http.get('js/ordering/orderOptions.json').then(function (response) {
                vm.payOptions = response.data.payOpts;
                vm.deliveryOptions = response.data.deliveryOpts;
                console.log('pay opts: ' + vm.payOptions);
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
