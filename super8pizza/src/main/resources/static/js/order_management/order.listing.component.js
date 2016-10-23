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

    OrderListingController.$inject = [];

    function OrderListingController() {

    };

})();