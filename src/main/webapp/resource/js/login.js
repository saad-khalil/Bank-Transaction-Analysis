var helloAjaxApp = angular.module("myApp", ['angular-loading-bar']).config(function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
});


helloAjaxApp.controller("UserController", ['$scope', '$http', function ($scope, $http, $timeout, cfpLoadingBar) {

    $http.defaults.headers.post["Content-Type"] = "application/json; charset=utf-8";
    $scope.sendData = function () {
        $http({
            url: 'app/login',
            method: "POST",
            data: $scope.json = angular.toJson("{'username' :" + "\"" + $scope.name + "\"" + ", 'password' :" + "\"" + $scope.password + "\"" + "}")


        }).then(function (response) {
            /**** Success Case ****/
            let body = JSON.parse(JSON.stringify(response)).body;
            console.log("Success -> " + response.data + "  Token: " + document.cookie);
            let d = angular.toJson(response);
            console.log(d);
            if (response.data.includes("Welcome")) {
                window.location.href = 'app/upload';
            } else {
                var banana = response.data;
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: banana,
                })
                //$scope.msgFromServlet = response.data;
            }
        }, function (response) {
            let obj = JSON.parse(response.data)
            /**** Failure Case ****/
            console.log("Failure -> " + response.data);
        });
    };

    // $scope.start = function() {
    //     cfpLoadingBar.start();
    // };
    //
    // $scope.complete = function () {
    //     cfpLoadingBar.complete();
    // }
    //
    //
    // // fake the initial load so first time users can see it right away:
    // $scope.start();
    // $scope.fakeIntro = true;
    // $timeout(function() {
    //     $scope.complete();
    //     $scope.fakeIntro = false;
    // }, 750);

}]);