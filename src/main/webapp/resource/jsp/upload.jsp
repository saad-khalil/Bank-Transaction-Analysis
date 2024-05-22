<!DOCTYPE html>
<html>
<head>
    <title>BTA | Upload</title>
    <link rel="icon" type="image/png" href="../img/favicon.png"/>
    <script type="text/javascript" src="../resource/js/angular_v1.6.0.js"></script>
    <script type="text/javascript" src="../resource/js/upload.js"></script>
    <script type="text/javascript" src="../resource/js/navbar.js"></script>
    <link rel="stylesheet" type="text/css" href="../resource/css/upload.css"/>
    <link rel="stylesheet" type="text/css" href="../resource/css/navbar.css"/>
    <!-- <link rel="stylesheet" type="text/css" href="../resource/css/globals.css" /> -->
</head>
<body>
<div id="navbar" ng-app="navbarApp" ng-controller="navbarCtrl">
    <navbar></navbar>
</div>

<div id="upload" ng-app="uploadApp" ng-controller="uploadCtrl">

    <span>{{answer}}</span>
    <h1>Recents</h1>
    <ol>
        <li ng-repeat="x in recents.data" ng-click="goToData({x})">{{x}}</li>
    </ol>
    <button id="button-recents" ng-click="recents()">Recents</button>
    <br>
    <input id="file-input" type="file" ng-model="file"/>
    <button id="button-upload" ng-click="upload()">Upload</button>
</div>
</body>
</html>