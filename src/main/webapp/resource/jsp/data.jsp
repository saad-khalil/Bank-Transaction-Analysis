<!DOCTYPE html>
<html>
<head>
    <title>BTA | Data</title>
    <link rel="icon" type="image/png" href="/BTA/resource/img/favicon.ico"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Javascript Files -->
    <script type="text/javascript">
    </script>
    <script type="text/javascript" src="/BTA/resource/js/angular_v1.6.0.js"></script>
    <script type="text/javascript" src="/BTA/resource/js/data.js"></script>
    <script type="text/javascript" src="/BTA/resource/js/navbar.js"></script>
    <script type='text/javascript'
            src='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.js'></script>
    <link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.css'
          type='text/css' media='all'/>
    <link rel="stylesheet" type="text/css" href="/BTA/resource/css/data.css"/>
    <link rel="stylesheet" type="text/css" href="/BTA/resource/css/navbar.css"/>
</head>
<body>
<div ng-app="navbarApp" ng-controller="navbarCtrl">
    <navbar id="navbar"></navbar>
</div>
<div class="main" id="data" ng-app="dataApp" ng-controller="dataCtrl" ng-init="init()">
    <div id="filter-table">
        <button ng-click="getdata()">Refresh</button>
        <hr/>
        <button ng-click="toggleExpand()">{{expan ? 'Collapse' : 'Expand'}} All</button>
        <hr/>
        <label id="filter-label">SEARCH</label><br/>
        <input type="text" ng-model="filters"/>
        <hr/>
        <span>{{debug}}</span>
    </div>

    <div class="table-div" id="data-table">
        <table id="table-d">
            <thead>
            <tr>
                <th></th>
                <th>Date</th>
                <th>Amount</th>
                <th>Balance</th>
                <th>Supplementary Details</th>
                <th>Description</th>
            </tr>
            </thead>
            <tbody ng-repeat="x in data | filter : filters | orderBy:'date'" class="repeated"
                   ng-click="x.expanded = !x.expanded" ng-class="x.expanded ? 'selected' : 'unselected'">
            <tr>
                <td>
                    <div class="img-wrapper"><img class="risk" ng-src="{{x.fraud}}"
                                                  onError="this.style.display='none'"/><span
                            class="hovershow">{{getText(x.fraud)}}</span></div>
                </td>
                <td>{{x.date | date: 'd MMM y'}}</td>
                <td>{{(x.amount / 100 | currency: '' : 2)}}</td>
                <td>{{(x.afterTransaction / 100 | currency: '' : 2)}}</td>
                <td>{{x.suppDetails}}</td>
                <td>{{x.description}}</td>
            </tr>
            <tr ng-show="x.expanded">
                <td></td>
                <td></td>
                <td colspan="4">
                    <table class="collapsed-table">
                        <tr>
                            <td>Entry Date<br/>{{x.entryDate | date: 'd MMM'}}</td>
                            <td>Transaction Type<br/>{{x.typeId}}</td>
                            <td>Customer Reference<br/>{{x.customerRef}}</td>
                            <td>Bank Reference<br/>{{x.bankRef}}</td>
                            <td>Funds Code<br/>{{x.fundsCode}}</td>
                        </tr>
                    </table>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="table-div" id="file-table">
        <table id="table-f">
            <thead>
            <tr>
                <th>Document Name</th>
                <th>Transaction Reference</th>
                <th>Bank Account ID</th>
                <th>Statement Number</th>
                <th>Number of Transactions</th>
                <th>Currency</th>
                <th>Opening Date</th>
                <th>Opening Balance</th>
                <th>Closing Date</th>
                <th>Closing Balance</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{{file.name}}</td>
                <td>{{file.transactionReferenceNumber}}</td>
                <td>{{file.accountID}}</td>
                <td>{{file.statementNumber}}</td>
                <td>{{file.numberOfTransactions}}</td>
                <td>{{file.currencyCode}}</td>
                <td>{{file.openingDate | date: 'd MMM y'}}</td>
                <td>{{file.openingBalance / 100 | currency: '' : 2}}</td>
                <td>{{file.closingDate | date: 'd MMM y'}}</td>
                <td>{{file.closingBalance / 100 | currency: '' : 2}}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <figure id="stats" ng-click="statshref()">
        <img src="/BTA/resource/img/image-1-1@2x.png">
        <figcaption>Statistics</figcaption>
    </figure>
</div>
</body>
</html>
