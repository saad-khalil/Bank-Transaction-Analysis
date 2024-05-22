var dataApp = angular.module("dataApp", ['angular-loading-bar']);


dataApp.config(['$locationProvider', 'cfpLoadingBarProvider', function ($locationProvider, cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);


dataApp.controller("dataCtrl", function ($scope, $http, $location, $window, $filter) {
    $scope.expan = false;
    $scope.createDate = function (date) {
        var day = parseInt(date.charAt(4) + date.charAt(5));
        var month = parseInt(date.charAt(2) + date.charAt(3)) - 1;
        var year = 2000 + parseInt(date.charAt(0) + date.charAt(1));
        var newDate = new Date(year, month, day);
        return newDate;
    };

    $scope.toggleExpand = function () {
        console.log("toggle");
        for (let i = 0; i < $scope.data.length; i++) {
            if ($scope.expan) {
                $scope.data[i].expanded = false;
            } else {
                $scope.data[i].expanded = true;
            }
        }
        $scope.expan = !$scope.expan;

    }

    $scope.createEDate = function (date) {
        var day = parseInt(date.charAt(2) + date.charAt(3));
        var month = parseInt(date.charAt(0) + date.charAt(1)) - 1;
        var newDate = new Date(0001, month, day);
        return newDate;
    };

    $scope.getdata = function () {
        console.log("File...");
        $http.get($location.path() + "/file", {
            header: {"Accepted": "application/json"}
        }).then(function success(response) {
            $scope.file = response.data;
            console.log("File In");
            $scope.file.openingDate = $scope.createDate($scope.file.openingDate);
            $scope.file.closingDate = $scope.createDate($scope.file.closingDate);
        }, function error(response) {
            console.log("Something went wrong.");
        });
        console.log("Transactions...");
        $http.get($location.path(), {
            header: {"Accepted": "application/json"}
        }).then(function success(response) {
            console.log(response.data);
            let Elements = response.data;
            let lowRisk = "/BTA/resource/img/risk/low_risk.png";
            let mediumRisk = "/BTA/resource/img/risk/medium_risk.png";
            let highRisk = "/BTA/resource/img/risk/high_risk.png";

            for (let i = 0; i < Elements.length; i++) {
                Elements[i].expanded = false;
                Elements[i].date = $scope.createDate(Elements[i].date);
                Elements[i].entryDate = $scope.createEDate(Elements[i].entryDate);

                if (Elements[i].fraud == null) {
                    Elements[i].fraud = ' ';
                } else {
                    switch (Elements[i].fraud) {
                        case 'LOW':
                            Elements[i].fraud = lowRisk;
                            break;
                        case 'MEDIUM':
                            Elements[i].fraud = mediumRisk;
                            break;
                        case 'HIGH':
                            Elements[i].fraud = highRisk;
                            break;
                        default:
                            Elements[i].fraud = ' ';
                            break;

                    }
                    ;
                }
                ;
                for (var key in Elements[i]) {
                    var item = Elements[i][key];
                    Elements[i][key] = (item == null) ? '-' : item;
                }
                ;
            }
            ;
            $scope.data = response.data;
            console.log("Transactions In");
        }, function error(response) {
            console.log("Something went wrong.");
        });


    };
    $scope.getdata();
    var stats = $location.path() + "/stats";
    $scope.statshref = function () {
        $window.location.href = stats;
    }

    $scope.getText = function (source) {
        if (source != null) {
            switch (source) {
                case "/BTA/resource/img/risk/low_risk.png":
                    return 'LOW RISK';
                case "/BTA/resource/img/risk/medium_risk.png":
                    return 'MEDIUM RISK';
                case "/BTA/resource/img/risk/high_risk.png":
                    return 'HIGH RISK';
                default:
                    return null;
            }
        }
    };


});

angular.element(function () {
    angular.bootstrap(document, ['navbarApp', 'dataApp']);
});
