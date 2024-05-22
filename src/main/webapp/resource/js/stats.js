var statsApp = angular.module("statsApp", ['angular-loading-bar']);

statsApp.config(['$locationProvider', 'cfpLoadingBarProvider', function ($locationProvider, cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = false;
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);
var item;
statsApp.controller("statsCtrl", function ($scope, $http, $location, $window, $filter, $timeout, $rootScope) {

    $scope.check = function (x) {
        return x.expanded || (x.date >= $scope.startDate && x.date <= $scope.endDate);
    }

    $scope.createDate = function (date) {
        var day = parseInt(date.charAt(4) + date.charAt(5));
        var month = parseInt(date.charAt(2) + date.charAt(3)) - 1;
        var year = 2000 + parseInt(date.charAt(0) + date.charAt(1));
        var newDate = new Date(year, month, day);
        return newDate;
    };

    $scope.getdata = function () {
        $http.get($location.path(), {
            header: {"Accepted": "application/json"}
        }).then(function success(response) {
            console.log("Data In");
            let Elements = response.data;
            for (let i = 0; i < Elements.length; i++) {
                Elements[i].expanded = false;
                Elements[i].date = $scope.createDate(Elements[i].date);
            }
            $scope.data = response.data;
            console.log($scope.data);
            $scope.getChart();
        }, function error(response) {
            console.log("Something went wrong.");
        });
    };
    $scope.getdata();
    var url = $location.path();
    var data = url.substring(0, url.lastIndexOf('/'));
    $scope.datahref = function () {
        $window.location.href = data;
    }


    $scope.sumTrans = function (items) {

        var sum = 0;
        if (items != null) {
            for (var i = 0; i < items.length; i++) {
                sum = sum + items[i].numberOfTransactions;
            }
            ;
        }
        return sum;
    };

    $scope.sumIncoming = function (items) {
        var sum = 0;
        if (items != null) {
            for (var i = 0; i < items.length; i++) {
                sum = sum + items[i].income;
            }
            ;
        }
        return sum;
    };

    $scope.sumOutgoing = function (items) {
        var sum = 0;
        if (items != null) {
            for (var i = 0; i < items.length; i++) {
                sum = sum + items[i].outgoing;
            }
            ;
        }
        return sum;
    };

    $rootScope.$watch(function () {

        $timeout(function () {
            var e2 = document.querySelectorAll(".selected td:nth-child(2)");
            var e3 = document.querySelectorAll(".selected td:nth-child(3)");
            var e4 = document.querySelectorAll(".selected td:nth-child(4)");
            var e5 = document.querySelectorAll(".selected td:nth-child(5)");
            var e6 = document.querySelectorAll(".selected td:nth-child(6)");
            var e7 = document.querySelectorAll(".selected td:nth-child(7)");

            //Transactions
            var s2 = 0;
            if (e2 != null) {
                for (i = 0; i < e2.length; i++) {
                    s2 = s2 + parseInt(e2[i].innerHTML.replace(',', ''));
                }
                ;
            }
            document.getElementById('d2').innerHTML = s2;

            //Min
            var s3 = 0;
            if (e3 != null && e3.length != 0) {
                var s3 = parseFloat(e3[0].innerHTML.replace(',', '')) * 100;
                for (var i = 0; i < e3.length; i++) {
                    var item = parseFloat(e3[i].innerHTML.replace(',', '')) * 100;
                    if (item < s3) {
                        s3 = item;
                    }
                }
                ;
            }
            document.getElementById('d3').innerHTML = $filter('currency')(s3 / 100, '', 2);

            //Max
            var s4 = 0;
            if (e4 != null && e4.length != 0) {
                var s4 = parseFloat(e4[0].innerHTML.replace(',', '')) * 100;
                for (var i = 0; i < e4.length; i++) {
                    var item = parseFloat(e4[i].innerHTML.replace(',', '')) * 100;
                    if (item > s4) {
                        s4 = item;
                    }
                }
                ;
            }
            document.getElementById('d4').innerHTML = $filter('currency')(s4 / 100, '', 2);

            //In
            var s5 = 0;
            if (e5 != null) {
                for (i = 0; i < e5.length; i++) {
                    s5 = s5 + parseFloat(e5[i].innerHTML.replace(',', '')) * 100;
                }
                ;
            }
            document.getElementById('d5').innerHTML = $filter('currency')(s5 / 100, '', 2);

            //Out
            var s6 = 0;
            if (e6 != null) {
                for (i = 0; i < e6.length; i++) {
                    s6 = s6 + parseFloat(e6[i].innerHTML.replace(',', '')) * 100;
                }
                ;
            }
            document.getElementById('d6').innerHTML = $filter('currency')(s6 / 100, '', 2);

            //Bal
            var s7 = 0;
            if (e7 != null && e7.length != 0) {
                s7 = parseFloat(e7[e7.length - 1].innerHTML.replace(',', '')) * 100;
            }
            document.getElementById('d7').innerHTML = $filter('currency')(s7 / 100, '', 2);

        }, 0, false);
        return true;
    });

    $scope.getChart = function () {
        let Elements = $scope.data;
        let labelsArr = [];
        let bal = [];
        let min = [];
        let max = [];
        let inc = [];
        let out = [];
        let tra = [];
        for (let i = 0; i < $scope.data.length; i++) {
            date = Elements[i].date;
            res = (date.toString()).substring(4, 15);
            labelsArr.push(res);

            bal1 = Elements[i].balance;
            res2 = parseInt(bal1.toString()) / 100;
            bal.push({t: date.toISOString(), y: res2});

            min1 = Elements[i].minimum;
            res3 = parseInt(min1.toString()) / 100;
            min.push({t: date.toISOString(), y: res3});

            max1 = Elements[i].maximum;
            res4 = parseInt(max1.toString()) / 100;
            max.push({t: date.toISOString(), y: res4});

            inc1 = Elements[i].income;
            res5 = parseInt(inc1.toString()) / 100;
            inc.push({t: date.toISOString(), y: res5});

            out1 = Elements[i].outgoing;
            res6 = parseInt(out1.toString()) / 100;
            out.push({t: date.toISOString(), y: res6});

            tra1 = Elements[i].numberOfTransactions;
            res7 = parseInt(tra1.toString());
            tra.push({t: date.toISOString(), y: res7});


        }

        var data = {
            // A labels array that can contain any sort of values
            labels: labelsArr,
            // Our series array that contains series objects or in this case series data arrays
            datasets: [{
                label: "Balance",
                fill: false,
                data: bal,
                borderColor: "#FCBA3D",
                backgroundColor: "#FCBA3D"
            }, {
                label: "Minimum",
                fill: false,
                data: min,
                borderColor: "#113559",
                backgroundColor: "#113559"
            }, {
                label: 'Maximum',
                fill: false,
                data: max,
                borderColor: "#003FAB",
                backgroundColor: "#003FAB"
            }]
        };

        var data2 = {
            // A labels array that can contain any sort of values
            labels: labelsArr,
            // Our series array that contains series objects or in this case series data arrays
            datasets: [{
                label: "Incoming",
                fill: false,
                data: inc,
                borderColor: "#FCBA3D",
                backgroundColor: "#FCBA3D"
            }, {
                label: "Outgoing",
                fill: false,
                data: out,
                borderColor: "#113559",
                backgroundColor: "#113559"
            }]
        };

        var data3 = {
            // A labels array that can contain any sort of values
            labels: labelsArr,
            // Our series array that contains series objects or in this case series data arrays
            datasets: [{
                label: "Transactions",
                fill: false,
                data: tra,
                borderColor: "#FCBA3D",
                backgroundColor: "#FCBA3D"

            }]
        };


        var mychart1 = new Chart("chart1", {
            type: "line",
            data: data,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                animation: {
                    duration: 0
                },
                scales: {
                    xAxes: [{
                        type: 'time',
                        time: {
                            minUnit: 'day'
                        }
                    }]
                }
            }
        })
        var mychart2 = new Chart("chart2", {
            type: "line",
            data: data2,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                animation: {
                    duration: 0
                },
                scales: {
                    xAxes: [{
                        type: 'time',
                        time: {
                            minUnit: 'day'
                        }
                    }]
                }
            }
        })
        var mychart3 = new Chart("chart3", {
            type: "bar",
            data: data3,
            options: {
                responsive: true,
                maintainAspectRatio: false,
                animation: {
                    duration: 0
                },
                scales: {
                    xAxes: [{
                        type: 'time',
                        time: {
                            minUnit: 'day'
                        }
                    }]
                }
            }
        })

    }
});


angular.element(function () {
    angular.bootstrap(document, ['navbarApp', 'statsApp']);
});


