<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="CD store page">
        <meta name="author" content="Yicong Li, Yuqing Guo">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="css/cdStore.css">
        <title>CD Store</title>
    </head>
    <body>
        <!--  include navigation  -->
        <%@ include file="../../template/nav.html" %>

        <!-- container begins -->
        <div class="container">
            <br>
            <div class="jumbotron">
                <h1> 
                    <span class="glyphicon glyphicon-cd" aria-hidden="true"></span> World Famous CD Gallery
                </h1>
                <p>
                    Collection of the most beautiful and classical music in the world
                    <span class="glyphicon glyphicon-music" aria-hidden="true"></span>
                </p>
                <div class="row">
                    <select name="categories" class="col-sm-4 rounded">
                        <option value="COUNTRY">Country</option>
                        <option value="ROCK">Rock</option>
                        <option value="POP">Pop</option>
                    </select>
                </div>
                <br>
                <div class="row">
                    <a href="./cart" class="btn btn-primary" role="button">
                        Shopping Cart
                        <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                    </a>
                </div>
            </div>
            <!-- cd cover pictures and detail information -->
            <div class="row" id="cdArea" role="tablist" style="margin-bottom: 10%;">
            </div>
        </div>
        <!-- container ends -->

        <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

        <script src="js/lib/jquery.loadTemplate.js"></script>
        <script src="js/store.js"></script>
    </body>
</html>