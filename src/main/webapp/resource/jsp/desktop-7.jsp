<!DOCTYPE html>
<html>
<head>
    <title>BTA | Data</title>
    <link rel="icon" type="image/png" href="/BTA/resource/img/favicon.ico"/>
    <meta charset="UTF-8"/>
    <meta name="HandheldFriendly" content="true">
    <meta name="og:type" content="website"/>
    <meta name="twitter:card" content="photo"/>
    <script type="text/javascript" src="../resource/js/angular_v1.6.0.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.6.0/angular-animate.js"></script>
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <script src="//angular-ui.github.io/bootstrap/ui-bootstrap-tpls-1.3.2.js"></script>
    <script type="text/javascript" src="/BTA/resource/js/desktop-7.js"></script>
    <script type="text/javascript" src="/BTA/resource/js/desktop-7-angular.js"></script>
    <script type="text/javascript" src="/BTA/resource/js/navbar.js"></script>
    <script type='text/javascript'
            src='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.js'></script>
    <link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.css'
          type='text/css' media='all'/>
    <link rel="stylesheet" type="text/css" href="../resource/css/desktop-7.css"/>
    <link rel="stylesheet" type="text/css" href="../resource/css/styleguide.css"/>
    <link rel="stylesheet" type="text/css" href="../resource/css/globals.css"/>
    <link rel="stylesheet" type="text/css" href="/BTA/resource/css/navbar.css"/>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body onload="recent()" style="margin: 0; background: #ffffff">
<input type="hidden" id="anPageName" name="page" value="desktop-7"/>
<div class="container-center-horizontal">
    <div ng-app="navbarApp" ng-controller="navbarCtrl">
        <navbar id="navbar">
            <img id="bta-logo" src="/BTA/resource/img/image-3-1@2x.png">
            <figure id="logout" onclick="onLogout()">
                <img id="logout-logo" src="/BTA/resource/img/image-2-1@2x.png"><br>
                <figcaption id="logout-text">Logout</figcaption>
            </figure>
            <br>
        </navbar>
    </div>
    <div class="desktop-7 screen">
        <div class="overlap-group-C61RwL">
            <div class="box-4eduM0">
                <div class="rectangle-2-CxQeVq"></div>
                <img class="line-1-CxQeVq" src="../resource/img/line-1@1x.svg"/>
                <img class="line-4-CxQeVq" src="../resource/img/line-4@1x.svg"/>
                <img class="line-2-CxQeVq" src="../resource/img/line-2@2x.svg"/>
                <img class="line-3-CxQeVq" src="../resource/img/line-3@2x.svg"/>
            </div>
            <div class="frame-24-4eduM0">
                <div class="textbox-P7T2cK">
                    <div class="label-text-style-i934166644-vcKMEq valign-text-middle roboto-normal-black-24px">
                        UPLOAD FILE
                    </div>
                    <div class="frame-5-vcKMEq">
                        <div class="hello-i934166831612-H3Hh4v valign-text-middle roboto-normal-black-24px clickable">
                            <input class="ng-hide" type="file" id="file-upload" accept=".940" onchange="updateName()"/>
                            <label class="clickable" for="file-upload"><span id="test"
                                                                             style="color: grey;padding-left: 12px;">Click to select a file....</span></label>
                        </div>
                    </div>
                </div>
                <div class="frame-23-P7T2cK">
                    <div id="recentOne" class="textbox-Kc7dwT">
                        <div class="label-text-style-i934172644-JuX2NU valign-text-middle roboto-normal-black-24px">
                            RECENT
                        </div>
                        <%--                            <div class="frame-5-JuX2NU">--%>
                        <%--                                <div class="hello-i934172831612-ufxz5m valign-text-middle roboto-normal-black-24px">--%>
                        <%--                                  <button id="recent1" hidden onclick="recentSearch()"></button>--%>
                        <%--                                    <label class="clickable" for="recent1"><span></span></label>--%>
                        <%--                                </div>--%>
                        <%--                            </div>--%>
                    </div>


                </div>
            </div>

            <div class="button-4eduM0">

                <div class="button-i148362101-DVhep3 valign-text-middle roboto-bold-seashell-18px clickable">
                    <button type="button" hidden id="upload-button" onclick="onUpload()"></button>
                    <label class="clickable" for="upload-button">UPLOAD</label>


                </div>

            </div>
            <h1 class="title-4eduM0 valign-text-middle worksans-bold-black-34px">Select File</h1>
            <div class="button-BJQsbv">
                <div class="button-i148062101-GoPoHX valign-text-middle roboto-bold-seashell-18px clickable">
                    <button type="button" id="deleteButton" onclick="onDelete()" hidden></button>
                    <label class="clickable" for="deleteButton">DELETE ALL</label>
                </div>
            </div>
            <div class="textbox-4eduM0">
                <div class="label-text-style-i1560644-NAxzwJ valign-text-middle roboto-normal-black-24px">
                    SEARCH RECENT
                </div>
                <div class="frame-5-NAxzwJ" ng-app="AutoComplete">
                    <div ng-controller="MyController">
                        <input id="searchBox"
                               class="hello-i1560831612-xAgeWN valign-text-middle roboto-normal-black-24px no-outline form-control"
                               placeholder="Enter a filename..." autocomplete="off" ng-model="selected"
                               uib-typeahead="name as name.name for name in names | filter:{name:$viewValue}"
                               typeahead-show-hint="true" typeahead-min-length="0">
                    </div>
                </div>
            </div>
            <div id="x1" class="component-11-4eduM0 clickable" onclick="oneDelete(this.id)">


            </div>
            <div id="x2" class="component-12-4eduM0 clickable" onclick="oneDelete(this.id)">

            </div>
            <div id="x3" class="component-13-4eduM0 clickable" onclick="oneDelete(this.id)">

            </div>
        </div>
    </div>
</div>


</body>
</html>
