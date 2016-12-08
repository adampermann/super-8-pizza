/**
 * Created by adampermann on 10/14/16.
 */
(function() {

    // our main app
    angular.module('Super8PizzaApp', [
        'ngRoute',
        'order',
        'orderManagement',
        'inventoryListing',
        'account',
        'toastr',
        'ngCookies',
        'angularUtils.directives.dirPagination'
    ]);
})();