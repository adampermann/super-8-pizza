/**
 * Created by adampermann on 10/14/16.
 */
// our main app
(function() {
    angular.module('Super8PizzaApp', [
        'ngRoute',
        'ordering',
        'orderSummary',
        'orderListing'
    ]);
})();