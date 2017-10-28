<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="Shopping cart page">
        <meta name="author" content="Yicong Li">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <title>Shopping Cart</title>
    </head>
    <body style="padding-top: 5rem;">
        <!--  include navigation  -->
        <%@ include file="../../template/navbar.jsp" %>
        <%@ include file="../../template/messages.jsp" %>
        <!-- container begins -->
        <div class="container-fluid" style="padding: 3rem 1.5rem;">
            <div class="row">
                <div class="col-sm-12">
                    <div class="row">
                        <h1>Shopping Cart</h1>
                    </div>
                    <div class="row">
                        <div class="col-sm-12" id="cartArea">
                        </div>
                    </div>
                    <div class="row">
                        <form class="col-sm-5">
                            <a href="./order" class="btn btn-success" id="checkout">Checkout</a>
                            <a href="./store" class="btn btn-success" role="continue">Continue Shopping</a>
                        </form>
                    </div>
                </div>
            </div>                
        </div>
        <!-- container ends -->
                 
        <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

        <script src="js/lib/jquery.loadTemplate.js"></script>
        <script src="js/cart.js"></script>
        
    </body>
</html>