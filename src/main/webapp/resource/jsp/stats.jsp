<!DOCTYPE html>
<html>
<head>
    <title>BTA | Statistics</title>
    <link rel="icon" type="image/png" href="/BTA/resource/img/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <!-- Javascript Files -->
    <script
            type="text/javascript"
            src="/BTA/resource/js/angular_v1.6.0.js"
    ></script>
    <script type="text/javascript" src="/BTA/resource/js/stats.js"></script>
    <script type="text/javascript" src="/BTA/resource/js/navbar.js"></script>
    <link rel="stylesheet" href="/BTA/resource/css/stats.css"/>
    <link rel="stylesheet" href="/BTA/resource/css/navbar.css"/>

    <script
            type="text/javascript"
            src="//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.js"
    ></script>
    <link
            rel="stylesheet"
            href="//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.css"
            type="text/css"
            media="all"
    />

    <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"></script>

    <!-- <link
      rel="stylesheet"
      href="/BTA/resource/bower_components/chartist/dist/chartist.min.css"
    />
    <script src="/BTA/resource/bower_components/chartist/dist/chartist.min.js"></script> -->
</head>
<body>
<div id="navbar" ng-app="navbarApp" ng-controller="navbarCtrl">
    <navbar></navbar>
</div>
<div id="stats" ng-app="statsApp" ng-controller="statsCtrl">
    <div id="filter-table">
        <button ng-click="getdata()">Refresh</button>
        <hr/>
        <hr/>
        <label id="start-date">START DATE</label><br/>
        <input type="date" id="start-date" ng-model="startDate" min="{{data[0].date | date : 'yyyy-MM-dd'}}"
               max="{{data[data.length-1].date | date : 'yyyy-MM-dd'}}"/>
        <hr/>
        <label id="start-date">END DATE</label><br/>
        <input type="date" id="end-date" ng-model="endDate" min="{{data[0].date | date : 'yyyy-MM-dd'}}"
               max="{{data[data.length-1].date | date : 'yyyy-MM-dd'}}"/>
        <hr/>
        <hr/>
        <label id="filter-label">FILTER</label><br/>
        <input type="text" ng-model="filters"/>
        <hr/>
        <span>{{ debug }}</span>
    </div>
    <div class="table-div" id="stats-table">
        <table>
            <thead>
            <tr>
                <th>Date</th>
                <th>Transactions</th>
                <th>Minimum Balance</th>
                <th>Maximum Balance</th>
                <th>Incoming</th>
                <th>Outgoing</th>
                <th>Final Balance</th>
            </tr>
            </thead>
            <tr
                    ng-repeat="x in data | filter : filters | orderBy:'date'"
                    ng-click="x.expanded = !x.expanded"
                    ng-class="(check(x))? 'selected' : 'unselected'"
            >
                <td>{{ x.date | date: "d MMM y" }}</td>
                <td>{{ x.numberOfTransactions }}</td>
                <td>{{ x.minimum / 100 | currency: "":2 }}</td>
                <td>{{ x.maximum / 100 | currency: "":2 }}</td>
                <td>{{ x.income / 100 | currency: "":2 }}</td>
                <td>{{ x.outgoing / 100 | currency: "":2 }}</td>
                <td>{{ x.balance / 100 | currency: "":2 }}</td>
            </tr>
        </table>
    </div>
    <div class="charts">
        <div class="balance-chart">
            <h3>Balance</h3>
            <div class="chart">
                <canvas ng-controller="statsCtrl" id="chart1"></canvas>
            </div>
        </div>
        <div class="inout-chart">
            <h3>Incoming and Outgoing</h3>
            <div class="chart">
                <canvas ng-controller="statsCtrl" id="chart2"></canvas>
            </div>
        </div>
        <div class="trans-chart">
            <h3>Transactions</h3>
            <div class="chart">
                <canvas ng-controller="statsCtrl" id="chart3"></canvas>
            </div>
        </div>
    </div>
    <div class="table-div general-data">
        <table>
            <thead>
            <tr>
                <th></th>
                <th>Transactions</th>
                <th>Minimum Balance</th>
                <th>Maximum Balance</th>
                <th>Incoming</th>
                <th>Outgoing</th>
                <th>Final Balance</th>
            </tr>
            </thead>
            <tr>
                <td>Selected</td>
                <td id="d2"></td>
                <td id="d3">{{ minimum() / 100 | currency: "":2 }}</td>
                <td id="d4">{{ maximum() / 100 | currency: "":2 }}</td>
                <td id="d5">{{ incoming() / 100 | currency: "":2 }}</td>
                <td id="d6">{{ outgoing() / 100 | currency: "":2 }}</td>
                <td id="d7">{{ balance() / 100 | currency: "":2 }}</td>
            </tr>
            <tr>
                <td>Global</td>
                <td>{{ sumTrans(data) }}</td>
                <td>
                    {{
                    (data | orderBy: "minimum")[0].minimum / 100 | currency:"":2
                    }}
                </td>
                <td>
                    {{
                    (data | orderBy: "minimum")[
                    (data | orderBy: "minimum").length - 1
                            ].maximum / 100 | currency: "":2
                    }}
                </td>
                <td>{{ sumIncoming(data) / 100 | currency: "":2 }}</td>
                <td>{{ sumOutgoing(data) / 100 | currency: "":2 }}</td>
                <td>{{ data[data.length - 1].balance / 100 | currency: "":2 }}</td>
            </tr>
        </table>
    </div>
    <figure id="data" ng-click="datahref()">
        <img src="/BTA/resource/img/image-1-1@2x.png"/>
        <figcaption>Data</figcaption>
    </figure>
</div>
</body>
</html>
