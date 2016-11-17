/**
 * Created by adampermann on 10/26/16.
 */
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

        activate();
        function activate() {


            // $http.get('/getInventory').then(function (response) {

            // });


        };

    };

})();