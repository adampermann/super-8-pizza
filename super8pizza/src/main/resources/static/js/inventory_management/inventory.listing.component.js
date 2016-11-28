
(function() {
    'use-strict';

    var module = angular.module('inventoryListing', []);

    module.component('inventoryListing', {
        templateUrl: 'js/inventory_management/inventory.listing.template.html',
        controller: 'InventoryListingController'
    });

    module.controller('InventoryListingController', InventoryListingController);

    InventoryListingController.$inject = ['$http', '$filter'];

    function InventoryListingController($http, $filter) {
        var vm = this;
        vm.inventory = [];
        vm.filterBy = "";

        vm.getInventory = function() {

            $http.get('/getInventory').then(function (response) {
                vm.inventory = response.data;
            });

        };

        vm.updateInventory = function() {

            $http.post('/updateInventory', inventory).then(function (response) {
                if (response.status == 200) {
                    vm.getInventory();
                } else {
                    // display an error
                    toastr.error('Error updating inventory', 'Error');
                }
            });
        };

        vm.addInventoryItem = function() {

            //post new inventory item
        };


        activate();
        function activate() {

            vm.getInventory();

        };

    };

})();