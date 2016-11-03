/**
 * Created by adampermann on 10/15/16.
 */
(function () {
    'use-strict';

    var module = angular.module('menuList');

    module.component('menu', {
        templateUrl: 'js/menu/menu.template.html',
        controller: MenuController
    });

    function MenuController($scope, $http) {
        var self = this;
        self.menu = [{name: 'Pepperoni Pizza', price: 12, quantity: 0},
            {name: 'Cheese Pizza', price: 12, quantity: 0},
            {name: 'Sausage Pizza', price: 12, quantity: 0}];
        // $http.get('/getMenu')
        //     .then(function (response) {
        //         $scope.menu = response.data;
        //         console.log('Received data from REST API');
        //     });
        console.log(self.menu);
    };

    // inject our controller with needed dependencies
    MenuController.$inject = ['$scope', '$http'];
})();
