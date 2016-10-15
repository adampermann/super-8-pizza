/**
 * Created by adampermann on 10/15/16.
 */

var MenuController = function($scope, $http) {

    $scope.menu = [{name: 'Pepperoni Pizza', price: 12, quantity: 0},
        {name: 'Cheese Pizza', price: 12, quantity: 0},
        {name: 'Sausage Pizza', price: 12, quantity: 0}];
    // $http.get('/getMenu')
    //     .then(function (response) {
    //         $scope.menu = response.data;
    //         console.log('Received data from REST API');
    //     });
};

MenuController.$inject = ['$scope', '$http'];

angular.module('menuList')
    .component('menuList', {
        templateUrl: 'js/menu/menu.template.html',
        controller: MenuController
    });
