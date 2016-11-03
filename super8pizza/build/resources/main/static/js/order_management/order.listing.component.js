/**
 * Created by adampermann on 10/23/16.
 */
(function() {
    'use-strict';

    var module = angular.module('orderListing');

    module.component('orderListing', {
        templateUrl: 'js/order_management/order.listing.template.html',
        controller: 'OrderListingController'
    });

    module.controller('OrderListingController', OrderListingController);

    OrderListingController.$inject = ['$http', '$filter'];

    function OrderListingController($http, $filter) {
        var vm = this;
        vm.orders = [];
        vm.filterBy = "";

        vm.getContentsStringForOrder = function(order) {
            var contents = '';

            for (var i = 0; i < order.contents.length; ++i) {
                contents += order.contents[i].name + 'x' + order.contents[i].quantity + '\n';
            }

            return contents;
        };

        activate();
        function activate() {

            $http.get('js/order_management/orders.json').then(function (response) {
                vm.orders = response.data;
            });
        };

    };

})();