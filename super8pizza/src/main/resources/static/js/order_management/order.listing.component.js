/**
 * Created by adampermann on 10/23/16.
 */
(function() {
    'use-strict';

    var module = angular.module('orderListing', []);

    module.component('orderListing', {
        templateUrl: 'js/order_management/order.listing.template.html',
        controller: 'OrderListingController'
    });

    module.controller('OrderListingController', OrderListingController);

    OrderListingController.$inject = ['$http', 'toastr'];

    function OrderListingController($http, toastr) {
        var vm = this;
        vm.orders = [];
        vm.statuses = [];
        vm.filterBy = "";

        vm.setStatus = function(order) {

            // should be an ajax call to update the status server side
            $http.post('/setOrderStatus', order).then(function (response) {
                if (response.status == 200) {
                    toastr.success('Updated order ' + order.orderNumber + ' to ' + order.orderStatus.name, 'Success!');
                    vm.getOpenOrders();
                } else {
                    // display an error
                    toastr.error('Error updating order status', 'Error');
                }
            });
        };

        vm.getOpenOrders = function() {

            $http.get('/getOpenOrders').then(function (response) {
                vm.orders = response.data;
            });
        };

        activate();
        function activate() {

            vm.getOpenOrders();

            $http.get('/getOrderStatusOptions').then(function (response) {
                vm.statuses = response.data;
            });

        };

    };

})();