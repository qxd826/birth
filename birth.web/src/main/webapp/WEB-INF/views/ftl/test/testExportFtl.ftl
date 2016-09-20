<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>test导出</title>
</head>

<body>
<table id="tableExcel" width="100%" border="1" cellspacing="0" cellpadding="0">
    <thead>
    <th>姓名</th>
    <th>电话</th>
    </thead>
    <tbody>
    <#if userList>
        <#list userList as item>
        <tr style="text-align: left">
            <td>${item.userName}</td>
            <td>${item.userMobile}</td>
            </td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
</body>
</html>

