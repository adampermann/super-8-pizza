/**
 * Created by adampermann on 10/22/16.
 */
(function () {
    'use-strict';

    var app = angular.module('Super8PizzaApp');

    app.service('orderingService', OrderingService);

    OrderingService.$inject = [];

    function OrderingService() {
        var self = this;
        var _order = {
            "orderId": "",
            "orderNumber": 0,
            "userId": "guest",
            "timestamp": Date.now(),
            "orderType": {},
            "paymentMethod": {},
            "cardNumber": "",
            "address": {},
            "orderItems": [],
            "price": 0,
            "orderStatus": {}
        };

        self.setOrder = function(order) {
            _order = order;
        };

        self.getOrder = function() {
            return _order;
        };

        self.clearOrder = function() {
            _order =  {
                "orderId": "",
                "orderNumber": 0,
                "userId": "guest",
                "timestamp": Date.now(),
                "orderType": {},
                "paymentMethod": {},
                "cardNumber": "",
                "address": {},
                "orderItems": [],
                "price": 0,
                "orderStatus": {}
            };
        };

    };
})();