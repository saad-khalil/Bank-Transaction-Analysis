<!DOCTYPE html>
<html>
<head>
    <title>Access Denied</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/resource/img/favicon.ico"/>
    <meta charset="UTF-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/accessDenied.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resource/css/accessDenied.css"/>
</head>
<body onload="countdownTimer()">
<div class="accessDeniedPage">
    <h2 class="header">Sorry, you do not have permission to view this page.</h2>
    <img class="image403 clickable" src="${pageContext.request.contextPath}/resource/img/403.png" alt="403"
         onclick="easterEgg()">
    <p>You are being redirected...</p>
    <h2><span id="seconds">5</span></h2>
    <button onclick="window.location.replace('/BTA');">Return Now</button>
</div>
</body>
</html>
