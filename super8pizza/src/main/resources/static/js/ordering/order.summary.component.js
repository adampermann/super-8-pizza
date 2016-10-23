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
        var self = this;
        self.title = "Order Summary";

        self.order = {};
        self.orderItems = [];
        self.address = {};
        self.orderTotal = 0;
        self.deliveryOptions = [];

        self.isDelivery = function() {
            return self.order.deliveryMethodId == 1;
        };

        self.getFormattedAddress = function() {
            return self.address.street + ' ' + self.address.city + ' ' + self.address.state + ', ' + self.address.zip
        };

        self.calculateOrderTotal = function() {
            var total = 0;
            for (var i = 0; i < self.orderItems.length; ++i) {
                total += (self.orderItems[i].price * self.orderItems[i].quantity);
            }

            self.orderTotal = $filter('currency')(total, '$', 2);
        };


        self.confirmOrder = function() {
            // either web request here or refactor those out to the ordering service
            // stubbed out the request for now.
            // $http.post('/orders/placeOrder', self.cart).success(function(data) {
            //
            // }).error(function (error) {
            //
            // });

            // clear out the order so stuff can be re-added
            orderingService.clearOrder();

            // take the user back to the home page.
            $location.url('/');
        };

        self.getDeliveryMethodName = function() {
            for (var i = 0; i < self.deliveryOptions.length; ++i) {
                if (self.deliveryOptions[i].id == self.order.deliveryMethodId) {
                    return self.deliveryOptions[i].name;
                }
            }
        };

        activate();
        function activate() {
            self.order = orderingService.getOrder();
            self.orderItems = self.order.contents;
            self.address = self.order.address;

            self.calculateOrderTotal();

            $http.get('js/ordering/deliveryOpts.json').then(function (response) {
                self.deliveryOptions = response.data
            });
        };
    };
})();