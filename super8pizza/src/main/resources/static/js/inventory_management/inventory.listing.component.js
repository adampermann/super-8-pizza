(function () {
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
        vm.newInventoryItem = {};

        vm.getInventory = function () {
            $http.get('/getInventory').then(function (response) {
                vm.inventory = response.data;
            });

        };

        vm.updateInventory = function () {

            var valid = true;

            for (var i = 0; i < vm.inventory.length; i++){
                if (vm.inventory[i].numberInStock < 0 || vm.inventory[i].price < 0) {
                    valid = false;
                    toastr.error('Price and Stock Ammount of all items cannot be negative', 'Error');
                    break;
                }
            }

            if (valid === true) {
                $http.post('/updateInventory', vm.inventory).then(function (response) {
                    if (response.status == 200) {
                        toastr.success('Inventory updated successfully', "Success");
                        vm.getInventory();
                    } else {
                        // display an error
                        toastr.error('Error updating inventory', 'Error');
                    }
                });
            }
        };

        //Initialize newInventoryItem on front-end
        vm.newInventoryItemCreate = function () {
            var newItem = {
                quantity: 1,
                numberInStock: 0,
                enabled: true,
                price: 0,
                name: "",
                type: {
                    id: 1,
                    name: "Crust"
                }
            };

            vm.newInventoryItem = newItem;

        };

        //Push front-end inventory item to database
        vm.addCreatedItem = function () {

            if (vm.newInventoryItem.name === undefined || vm.newInventoryItem.numberInStock === undefined
                || vm.newInventoryItem.price === undefined || vm.newInventoryItem.type === undefined) {
                toastr.error("Please enter all required fields! (item not saved)", "Error");
            } else if (vm.newInventoryItem.name.length == 0) {
                toastr.error("Please enter a name for new item! (item not saved)", "Error");
            } else if (vm.newInventoryItem.price < 0 || vm.newInventoryItem.numberInStock < 0){
                toastr.error("Price or quantity cannot be negative! (item not saved)", "Error");
            } else {
                $http.post('/addInventoryItem', vm.newInventoryItem).then(function (response) {
                    if (response.status == 200) {
                        toastr.success('New Item Added to Inventory', "Success");
                        vm.inventory.push(response.data);
                        vm.getInventory();
                    } else {
                        // display an error
                        toastr.error('Error adding new inventory item', 'Error');
                    }
                }, function (response) {
                    toastr.error(response.data.message, 'Error');
                });
            }

        };


        //create and push new inventory item to database

       /* vm.addInventoryItem = function () {

            var newItem = {
                quantity: 1,
                numberInStock: 0,
                enabled: true,
                price: 0,
                name: "New Item",
                type: {
                    id: 1,
                    name: "Crust"
                }
            };

            $http.post('/addInventoryItem', newItem).then(function (response) {
                if (response.status == 200) {
                    toastr.success('New Item Added to Inventory', "Success");
                    vm.inventory.push(response.data);
                    vm.getInventory();
                } else {
                    // display an error
                    toastr.error('Error adding new inventory item', 'Error');
                }
            });

        };*/


        activate();
        function activate() {

            vm.getInventory();

            $http.get('/getInventoryTypes').then(function (response) {
                vm.types = response.data;
            });

        };
    }

})();