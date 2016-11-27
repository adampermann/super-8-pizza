/**
 * Created by adampermann on 11/26/16.
 */
(function() {
    'use-strict';

    var module = angular.module('order');

    module.component('customOrder', {
        templateUrl: 'js/order/order.custom.template.html',
        controller: 'CustomOrderController'
    });

    module.controller('CustomOrderController', CustomOrderController);
    CustomOrderController.$inject = ['$location', '$http', '$filter', 'orderingService', 'toastr'];


    function CustomOrderController($location, $http, $filter, orderingService, toastr) {
        var vm = this;
        vm.inventory = [];
        vm.customInventory = [];

        // ENUM values: crust = 1, cheese = 2, topping = 3, sauce = 4
        vm.crusts = [];
        vm.cheeses = [];
        vm.toppings = [];
        vm.sauces = [];

        vm.calculatePrice = function () {
            var calulatedPrice = 10;
            for (var i = 0; i < vm.customInventory.length; ++i) {
                // add dollar for everything in the custom inventory
                calulatedPrice += 1 * vm.customInventory[i].quantity;
            }

            return calulatedPrice;
        };

        vm.addToOrder = function() {
            var customPizza = {
                "id": "",
                "imageURL": 'images/customize-pizza.png',
                "includedItems": vm.customInventory,
                "price": vm.calculatePrice(),
                "name": "Custom Pizza",
                "quantity": 1,
                "enabled": true
            };
            orderingService.setCustomPizza(customPizza);

            toastr.success('Added Custom Pizza to Order!', 'Success');
            $location.url('/ordering');
        };

        vm.addItemToCustomInventory = function (item) {
            var currentItem = vm.itemInCustomInventory(item);

            if (currentItem != null) {
                currentItem.quantity += 1;
            } else {
                // item wasn't already in the Inventory
                var copiedItem = angular.copy(item);
                copiedItem.quantity = 1;
                vm.customInventory.push(copiedItem);
            }
        };

        vm.itemInCustomInventory = function (item) {
            for (var i = 0; i < vm.customInventory.length; ++i) {
                if (vm.customInventory[i].name == item.name) {
                    return vm.customInventory[i];
                }
            }

            return null;
        };

        vm.isCrust = function (item) {
            return item.type.id == 1;
        };

        vm.isCheese = function (item) {
            return item.type.id == 2;
        };

        vm.isTopping = function (item) {
            return item.type.id == 3;
        };

        vm.isSauce = function (item) {
            return item.type.id == 4;
        };

        function separateInventoryByType() {
            vm.crusts = $filter('filter')(vm.inventory, vm.isCrust);
            vm.cheeses = $filter('filter')(vm.inventory, vm.isCheese);
            vm.toppings = $filter('filter')(vm.inventory, vm.isTopping);
            vm.sauces = $filter('filter')(vm.inventory, vm.isSauce);
        }

        activate();
        function activate() {
            // get the inventory and display it
            $http.get("/getInventory").then(function (response) {
                vm.inventory = response.data;
                console.log(vm.inventory);

                separateInventoryByType();

            }, function () {
                toastr.error('Error retrieving inventory from server', 'Error');
            });
        }
    }
})();