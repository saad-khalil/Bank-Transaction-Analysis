<%--
  Created by IntelliJ IDEA.
  User: Saad
  Date: 5/23/2021
  Time: 10:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .button {
            border: none;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
        }

        .button1 {
            background-color: #4CAF50;
        }

        /* Green */
        .button2 {
            background-color: #008CBA;
        }

        /* Blue */
    </style>
</head>
<body>

<h1>Testing Start Page</h1>
<p>CLick on button to go to upload page</p>

<button class="button button1" onclick="redirect()">Start</button>

<script>

    function redirect() {

        fetch('app/upload', {method: "GET", body: null}).then(function (response) {

                console.log(response.status)

                if (response.status = 200) {

                    window.location.href = 'app/upload';
                }
            }
        )
    }


</script>
</body>
</html>
