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

    OrderListingController.$inject = ['$http', '$filter'];

    function OrderListingController($http, $filter) {
        var vm = this;
        vm.orders = [];
        vm.pendingOrders = [];
        vm.completedOrders = [];
        vm.statuses = [];
        vm.filterBy = "";

        vm.isCompleted = function(order) {
            return order.orderStatus.id == 4;
        };

        vm.isPending = function(order) {
            return order.orderStatus.id != 4;
        };

        vm.setStatus = function(order) {
            console.log('setting order status' + order.orderStatus.name);
            order.orderStatus = order.setOrderStatus;

            // should be an ajax call to update the status server side
            // $http.post('/setOrderStatus', order).then(function (response) {
            //    if (response.data.success) {
            //
            //    } else {
            //        // show error couldn't set order status
            //    }
            // });
        };

        activate();
        function activate() {


            // $http.get('/getOrders').then(function (response) {
            //     vm.orders = response.data;
            //     console.log('orders are');
            //     console.log(vm.orders);
            // });

            $http.get('/getOrderStatusOptions').then(function (response) {
                vm.statuses = response.data;

                console.log('statuses are');
                console.log(vm.statuses);
            });

            $http.get('js/order_management/orders.json').then(function (response) {
                vm.orders = response.data;
                console.log(vm.orders);
            });

        };

    };

})();