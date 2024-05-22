var AutoComplete = angular.module("AutoComplete", ['ngAnimate', 'ui.bootstrap']);

AutoComplete.controller('MyController', function ($scope, $http) {
    //ngModel value
    $scope.selected;

    //lookup values
    $scope.names = [];

    $http({
        method: 'GET',
        url: ""
    }).then(function successCallback(response) {
        let arr = [];
        for (let i = 0; i < response.data.length; i++) {
            let json = {name: response.data[i]};
            arr.push(json);
        }
        $scope.names = arr;
    }, function errorCallback(response) {
        console.log("Get error in autocomplete with response =");
        console.log(response);
    })

    document.querySelector('#searchBox').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            if ($scope.selected !== undefined) {
                $http({
                    url: 'data/' + escape($scope.selected.name),
                    method: "GET",
                }).then(function (response) {
                    /**** Success Case ****/
                    window.location = "data/" + escape($scope.selected.name);
                }, function (response) {
                    /**** Failure Case ****/
                });

            }
        }
    });
    console.log("angular loaded");
});

angular.element(function () {
    angular.bootstrap(document, ['navbarApp', 'AutoComplete']);
});
console.log("loaded");
