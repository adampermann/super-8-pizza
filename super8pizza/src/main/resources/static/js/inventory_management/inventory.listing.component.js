
(function() {
    'use-strict';

    var module = angular.module('inventoryListing', []);

    module.component('inventoryListing', {
        templateUrl: 'js/inventory_management/inventory.listing.template.html',
        controller: 'InventoryListingController'
    });

    module.controller('InventoryListingController', InventoryListingController);

    InventoryListingController.$inject = ['$http', '$filter', "toastr"];

    function InventoryListingController($http, $filter, toastr) {
        var vm = this;
        vm.inventory = [];
        vm.types = [];
        vm.filterBy = "";

        vm.getInventory = function() {

            $http.get('/getInventory').then(function (response) {
                vm.inventory = response.data;
            });

        };

        vm.updateInventory = function() {

            $http.post('/updateInventory', vm.inventory).then(function (response) {
                if (response.status == 200) {
                    toastr.success('Inventory updated successfully', "Success");
                    vm.getInventory();
                } else {
                    // display an error
                    toastr.error('Error updating inventory', 'Error');
                }
            });
        };

        vm.addInventoryItem = function() {
            // public InventoryOption type;
            // public String name;
            // public Double price;
            // public String id;
            // public boolean enabled = true;
            // private static int disableItemThreshold = 10;
            // private int numberInStock;
            // public int quantity = 1;

            var newItem = {
                quantity: 1,
                numberInStock: 0,
                enabled: true,
                price: 0,
                name: "New Item",
                type: { id: 1,
                        name: "Crust" }
            };

            $http.post('/addInventoryItem', newItem ).then(function (response) {
                if (response.status == 200) {
                    toastr.success('New Item Added to Inventory', "Success");
                    vm.inventory.push(response.data);
                } else {
                    // display an error
                    toastr.error('Error adding new inventory item', 'Error');
                }
            });

        };


        activate();
        function activate() {

            vm.getInventory();

            $http.get('/getInventoryTypes').then(function (response) {
                vm.types = response.data;
            });

        };

    };

})();