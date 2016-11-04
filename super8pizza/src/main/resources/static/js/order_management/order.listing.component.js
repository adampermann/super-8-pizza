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

        // vm.getContentsStringForOrder = function(order) {
        //     var contents = '';
        //
        //     for (var i = 0; i < order.contents.length; ++i) {
        //         contents += order.contents[i].name + 'x' + order.contents[i].quantity + '\n';
        //     }
        //
        //     return contents;
        // };

        // vm.updateOrderStatus = function(order, status) {
        //     console.log('upating status: old' + order.status);
        //     console.log('value to be set: ' + status);
        //     order.status = status;
        //
        //     console.log('new: ' + order.status);
        // };

        vm.isCompleted = function(order) {
            return order.status.id == 3;
        };

        vm.isPending = function(order) {
            return order.status.id != 3;
        };

        vm.setStatus = function(order) {
            console.log('setting order status' + order.status.name);

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

            // private String orderId;
            // private long orderNumber;
            // private String userId;
            // private Date timestamp;
            // private OrderType orderType;
            // private PaymentMethod paymentMethod;
            // private Address address;
            // private Map<String, Integer> orderItems;
            // private OrderStatus orderStatus;
            //
            // $http.get('/getOrders').then(function (response) {
            //     vm.orders = response.data;
            // });


            $http.get('js/order_management/orders.json').then(function (response) {
                vm.orders = response.data;
                console.log(vm.orders);
            });

            $http.get('js/order_management/orderstatuses.json').then(function (response) {
                vm.statuses = response.data.statuses;
            });

        };

    };

})();