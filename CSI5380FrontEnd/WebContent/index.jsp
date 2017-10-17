<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Login Page</title>
    </head>
    <body>
        <form action="LoginServlet" method="post">
            <div>
                <p>Username:</p>
                <input type="text" name="user">
            </div>
            <br>
            <div>
                <p>Password:</p>
                <input type="password" name="pwd">
            </div>
            <br>
            <div>
                <input type="submit" id="login" value="Login">
            </div>
        </form>
    </body>
    <script src="common/lib/jquery-3.2.1.min.js"></script>
    <script src="common/scripts/index.js"></script>
</html>