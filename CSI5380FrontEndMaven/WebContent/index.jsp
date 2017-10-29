<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="cd store landing page">
        <meta name="author" content="Yicong Li, Yuqing Guo">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/index.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
        <script src="js/lib/jquery-3.2.1.min.js"></script>
        <script src="js/lib/bootstrap.min.js"></script>

        <title>Welcome to our CD gallery</title>
    </head>
    <body>
        <!--  include navigation  -->
        <%@ include file="../../template/navbar.jsp" %>
		<%@ include file="../../template/messages.jsp" %>
        <!-- container begins -->
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <div id="content">
                        <h1>World CD Gallery</h1>
                        <h3>Collection of the most beautiful and classical music in the world</h3>
                        <hr>
                        <a href="./store" class="btn btn-lg btn-light"><i aria-hidden="true"></i> Get started!</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- container ends -->
    </body>
</html>