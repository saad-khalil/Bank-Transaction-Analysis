var uploadApp = angular.module("uploadApp", []);

uploadApp.controller("uploadCtrl", function ($scope, $http, $window) {

    $scope.recents = function () {
        $http({
            method: "GET",
            url: "",
            header: {"Accepted": "application/json"}
        }).then(function success(response) {
            $scope.recents = response;
            //$scope.answer = response;
        }, function error(response) {
            $scope.answer = response;
        });
    };

    $scope.upload = function () {
        var fd = new FormData();
        fd.append('file', $scope.file);
        $http.post(
            "",
            fd,
            {
                transformRequest: angular.identity,
                headers: {"Content-Type": undefined}
            }).then(function success(response) {
            recents();
        }, function error(response) {
            $scope.answer = response;
        });
    };

    $scope.goToData = function (xx) {
        $window.location.href = "data?filename=" + xx.x;
        //$scope.answer = filename.x;
    };


});

angular.element(function () {
    angular.bootstrap(document, ['navbarApp', 'uploadApp']);
});