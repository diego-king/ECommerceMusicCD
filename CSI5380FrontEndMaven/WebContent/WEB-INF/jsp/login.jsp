<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="cd store login page">
        <meta name="author" content="Mike Kreager">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/parsley.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/parsley.min.js"></script>
        <title>Login</title>
    </head>
    <body>
        <%@ include file="../../template/navbar.jsp" %>
        <%@ include file="../../template/messages.jsp" %>
        <div class="container w-50">
            <form method="POST" action="login" data-parsley-validate>
                <div class="card">
		                <h4 class="card-header">Login</h4>
		                <div class="card-body">
		                    <div class="card-text">
		                        <div class="form-group">
						                    <label for="email">Username (email)</label>
						                    <input type="email" class="form-control" id="email" name="email" 
						                        data-parsley-trigger="change" required>
						                </div>
		                    </div>
		                    <div class="card-text">
						                <div class="form-group">
						                    <label for="password">Password</label>
						                    <input type="password" class="form-control" id="password" name="password" required>
						                </div>
						                <button type="submit" class="btn btn-primary">Submit</button>
						            </div>
						        </div>
						    </div>   
            </form>
            <br>
            <p>New user? <a href="account">Sign up here</a>.
        </div>
    </body>
</html>