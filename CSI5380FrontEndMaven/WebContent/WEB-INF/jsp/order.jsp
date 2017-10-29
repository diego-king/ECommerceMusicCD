<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="cd store order confirmation page">
        <meta name="author" content="Mike Kreager">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/parsley.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/parsley.min.js"></script>
        <title>Order</title>
    </head>
    <body>
        <%@ include file="../../template/navbar.jsp" %>
        <%@ include file="../../template/messages.jsp" %>
        <div class="container w-50">
            <form method="POST" action="order">
		            <div class="card">
		                <h4 class="card-header">Select shipping method</h4>
		                <div class="card-body">
		                    <div class="form-group">
		                        <select class="form-control" name="ship" id="shippingOption">
		                            <c:forEach var="ship" items="${shippingOptions}">
		                                <option value="<c:out value="${ship.id}" />">
		                                    <c:out value="${ship.id}. ${(fn:replace(ship.company, '\"',' '))} -
		                                        ${(fn:replace(ship.type, '\"',' '))} - $${ship.price}" />
		                                </option>
		                            </c:forEach>
		                        </select>
		                    </div>
		                    <div>
						                <button type="submit" class="btn btn-primary">Submit</button>
						            </div>
		                </div>
		            </div>
	          </form>
	      </div>
	  </body>
</html>