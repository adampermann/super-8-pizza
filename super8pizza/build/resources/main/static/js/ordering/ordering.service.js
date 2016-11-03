/**
 * Created by adampermann on 10/22/16.
 */
(function () {
    'use-strict';

    var app = angular.module('Super8PizzaApp');

    app.service('orderingService', OrderingService);

    OrderingService.$inject = ['$http'];

    function OrderingService($http) {
        var self = this;
        var _order = { "deliveryMethod": 0, "address": {}, "contents": [] };

        self.setOrder = function(order) {
            _order = order;
        };

        self.getOrder = function() {
            return _order;
        };

        self.clearOrder = function() {
            _order =  { "deliveryMethod": 0, "address": {}, "contents": [] };
        };

    };
})();