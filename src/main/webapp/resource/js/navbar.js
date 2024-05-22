var navbarApp = angular.module("navbarApp", ['angular-loading-bar']).config(function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
});
navbarApp.directive("navbar", function () {
    return {
        restrict: 'E',
        templateUrl: '/BTA/app/upload/navbar'
    };
});


navbarApp.controller("navbarCtrl", function ($scope, $window, $http) {

    $scope.uploadhref = function () {
        $window.location.href = "/BTA/app/upload";
    }
    $scope.loginhref = function () {
        $http({
            url: '/BTA/app/login/logout',
            method: "DELETE"
        }).then(function (response) {
            /**** Success Case ****/
            $window.location.href = "/BTA/";
        }, function (response) {
            let obj = JSON.parse(response.data)
            /**** Failure Case ****/
            console.log("Failure -> " + response.data);
        });
    }
});


/*
var navbar = document.getElementById('navbar');
angular.element(document).ready(function() {
   angular.bootstrap(navbar, ['navbarApp']);
});
*/