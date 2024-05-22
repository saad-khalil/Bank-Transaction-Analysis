<html lang="en">
<head>
    <meta charset="utf-8">
    <title>BTA | Login</title>
    <link rel="icon" type="image/png" href="/BTA/resource/img/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="resource/css/loginpage.css">
    <script type="text/javascript" src="resource/js/angular.min.js"></script>
    <script type="text/javascript" src="resource/js/login.js"></script>
    <script type='text/javascript'
            src='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.js'></script>
    <link rel='stylesheet' href='//cdnjs.cloudflare.com/ajax/libs/angular-loading-bar/0.9.0/loading-bar.min.css'
          type='text/css' media='all'/>
    <link rel="stylesheet" type="text/css" href="resource/css/desktop-7.css"/>
    <link rel="stylesheet" type="text/css" href="resource/css/styleguide.css"/>
    <link rel="stylesheet" type="text/css" href="resource/css/globals.css"/>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<div id="container">

    <video id="background_video" autoplay loop muted>
        <source src="${pageContext.request.contextPath}/resource/img/night.mp4" type="video/mp4">
    </video>

    <div class="login_page">
        <div class="bta_logo"></div>
        <div class="login_box">
            <div ng-app="myApp">
                <form ng-controller="UserController" name="myForm" ng-submit="sendData(myForm)">
                    <div class="password_container">

                        <span class="password_text">PASSWORD</span>
                        <input class="password_field" type="password" tabindex="2" name="name" ng-model="password"
                               placeholder="Password" autocomplete="off"
                               onblur="this.placeholder = 'Password'" onfocus="this.placeholder = ''"><span
                            class="ei64_7_83_1612"></span>
                    </div>
                    <div class="username_container">

                        <span class="username_text">USERNAME</span>
                        <input class="username_field" type="text" tabindex="1" name="name" ng-model="name"
                               placeholder="Username" autocomplete="off"
                               onblur="this.placeholder = 'Username'" onfocus="this.placeholder = ''"><span
                            class="ei64_10_83_1612"></span>
                    </div>
                    <div class="button_container">
                        <div class="button valign-text-middle roboto-bold-seashell-18px clickable">
                            <button type="submit" id="login_button" hidden></button>
                            <%--                    <span class="login_text clickable">Login</span>--%>
                            <label class="clickable" for="login_button">LOGIN</label>

                        </div>
                    </div>
                    <!-- Display's Output On The Screen -->
                    <p>
                        <span id="welcomeText" class="msg">{{msgFromServlet}}</span>
                    </p>
                </form>
            </div>
        </div>
        <div class="fyndoo_logo"></div>

        <div class="topicus_logo"></div>


    </div>
</div>
</body>
</html>