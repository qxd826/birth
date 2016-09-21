<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <!--需要jquery包支持-->
    <script src="${BASE_PATH}/WEB-INF/resources/js/lib/jquery-1.11.1.min.js"></script>
    <script>
        $(document).ready(function ($) {
            window.print();
        });
    </script>
</head>
<body>
<div id="test">
    <tr>
        <span>用户名:${user.userName}</span>
    </tr>
    <tr>
        <span>电话:${user.userMobile}</span>
    </tr>
</div>
</body>
</html>
