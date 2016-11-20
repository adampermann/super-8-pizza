/**
 * Created by adampermann on 10/23/16.
 */
(function() {
    'use-strict';

    var module = angular.module('orderHistory', []);

    module.component('orderHistory', {
        templateUrl: 'js/order_management/order.history.template.html',
        controller: 'OrderHistoryController'
    });

    module.controller('OrderHistoryController', OrderHistoryController);

    OrderHistoryController.$inject = ['$http'];

    function OrderHistoryController($http) {
        var vm = this;
        vm.completedOrders = [];
        vm.filterBy = "";

        activate();
        function activate() {

            $http.get('/getCompleteOrders').then(function (response) {
                vm.completedOrders = response.data;
            });


        };

    };

})();