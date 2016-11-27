/**
 * Created by adampermann on 10/22/16.
 */
(function () {
    'use-strict';

    var app = angular.module('Super8PizzaApp');

    app.service('orderingService', OrderingService);

    OrderingService.$inject = [];

    function OrderingService() {
        var vm = this;
        vm.order = null;
        vm.cart = null;
        vm.customPizza = null;

        vm.setCustomPizza = function(pizza) {
            vm.customPizza = pizza;
        };

        vm.getCustomPizza = function() {
            return vm.customPizza;
        };

        vm.clearCustomPizza = function() {
            vm.customPizza = null;
        };

        vm.setCart = function(cart) {
            vm.cart = cart;
        };

        vm.getCart = function() {
            return vm.cart;
        };

        vm.clearCart = function() {
            vm.cart = null;
        };

        vm.setOrder = function(order) {
            vm.order = order;
        };

        vm.getOrder = function() {
            return vm.order;
        };

        vm.clearOrder = function() {
            vm.order = null;
        };

    }

})();