/**
 * Created by adampermann on 10/22/16.
 */
(function () {
    'use-strict';

    var module = angular.module('orderSummary');

    module.component('orderSummary', {
        templateUrl: 'js/ordering/order.summary.template.html',
        controller: 'OrderSummaryController'
    });

    // register the controller with the order summary module
    module.controller('OrderSummaryController', OrderSummaryController);

    OrderSummaryController.$inject = ['$http', '$location', '$filter', 'orderingService'];

    function OrderSummaryController($http, $location, $filter, orderingService) {
        var vm = this;
        vm.title = "Order Summary";

        vm.order = {};
        vm.orderItems = [];
        vm.address = {};
        vm.orderTotal = 0;
        vm.deliveryOptions = [];
        vm.payOptions = [];

        vm.isDelivery = function() {
            return vm.order.deliveryMethodId == 1;
        };

        vm.getFormattedAddress = function() {
            return vm.address.street + ' ' + vm.address.city + ' ' + vm.address.state + ', ' + vm.address.zip
        };

        vm.calculateOrderTotal = function() {
            var total = 0;
            for (var i = 0; i < vm.orderItems.length; ++i) {
                total += (vm.orderItems[i].price * vm.orderItems[i].quantity);
            }

            vm.orderTotal = $filter('currency')(total, '$', 2);
        };


        vm.confirmOrder = function() {
            // either web request here or refactor those out to the ordering service
            // stubbed out the request for now.
            // $http.post('/orders/placeOrder', vm.cart).success(function(data) {
            //
            // }).error(function (error) {
            //
            // });

            // clear out the order so stuff can be re-added
            orderingService.clearOrder();

            // take the user back to the home page.
            $location.url('/');
        };

        vm.getDeliveryMethodName = function() {
            for (var i = 0; i < vm.deliveryOptions.length; ++i) {
                if (vm.deliveryOptions[i].id == vm.order.deliveryMethodId) {
                    return vm.deliveryOptions[i].name;
                }
            }
        };

        vm.getPayMethodName = function() {
            for (var i = 0; i < vm.payOptions.length; ++i) {
                if (vm.payOptions[i].id == vm.order.payMethodId) {
                    return vm.payOptions[i].name;
                }
            }
        };

        activate();
        function activate() {
            vm.order = orderingService.getOrder();
            vm.orderItems = vm.order.contents;
            vm.address = vm.order.address;

            vm.calculateOrderTotal();

            $http.get('js/ordering/orderOptions.json').then(function (response) {
                vm.deliveryOptions = response.data.deliveryOpts;
                vm.payOptions = response.data.payOpts;
            });
        };
    };
})();