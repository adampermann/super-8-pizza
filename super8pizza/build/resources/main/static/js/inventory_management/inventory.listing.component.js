
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

            /*var TableData = new Array();

            $('#tableInventory tr').each(function(row, tr){
                TableData[row]={
                    "name" : $(tr).find('td:eq(0)').text()
                    , "numberInStock" :$(tr).find('td:eq(1)').text()
                    , "price" : $(tr).find('td:eq(2)').text()
                }
            });
            TableData.shift();  // first row is the table header - so remove

            for (i = 0; i < TableData.length; i++) {
                inventory[i].name = TableData[i].name;
                inventory[i].numberInStock = TableData[i].numberInStock;
                inventory[i].price = TableData[i].price;
            }
*/
            $http.post('/updateInventory', vm.inventory).then(function (response) {
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