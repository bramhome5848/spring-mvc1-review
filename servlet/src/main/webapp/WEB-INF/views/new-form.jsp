<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--
    상대경로 사용, [현재 URL 이 속한 계층 + /save]
    현재 URL : /servlet-mvc/members/new-form
    결과 : /servlet-mvc/members/save
-->
<form action="save" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>