<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="cd store order verification and payment page">
        <meta name="author" content="Mike Kreager">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/parsley.css">
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/parsley.min.js"></script>
        <script src="js/creditcard.js"></script>
        <title>Payment</title>
    </head>
    <body>
        <%@ include file="../../template/navbar.jsp" %>
        <%@ include file="../../template/messages.jsp" %>
        <div class="container w-50">
            <form method="POST" action="payment" data-parsley-validate>
                <div class="card">
                    <h4 class="card-header">Verify billing address</h4>
                    <div class="card-body">
                        <div class="card-text">${billingFullName}</div>
                        <input type="hidden" name="billingFullName" value="${billingFullName}" />
                        <div class="card-text">${billingAddressLine1}</div>
                        <input type="hidden" name="billingAddressLine1" value="${billingAddressLine1}" />
                        <div class="card-text">${billingAddressLine2}</div>
                        <input type="hidden" name="billingAddressLine2" value="${billingAddressLine2}" />
                        <div class="card-text">${billingCity}</div>
                        <input type="hidden" name="billingCity" value="${billingCity}" />
                        <div class="card-text">${billingProvince}</div>
                        <input type="hidden" name="billingProvince" value="${billingProvince}" />
                        <div class="card-text">${billingCountry}</div>
                        <input type="hidden" name="billingCountry" value="${billingCountry}" />
                        <div class="card-text">${billingZip}</div>
                        <input type="hidden" name="billingZip" value="${billingZip}" />
                        <div class="card-text">${billingPhone}</div>
                        <input type="hidden" name="billingPhone" value="${billingPhone}" />
                    </div>
                </div>
                <br>
                <div class="card">
                    <h4 class="card-header">Verify shipping address</h4>
                    <div class="card-body">
                        <div class="card-text">${shippingFullName}</div>
                        <input type="hidden" name="shippingFullName" value="${shippingFullName}" />
                        <div class="card-text">${shippingAddressLine1}</div>
                        <input type="hidden" name="shippingAddressLine1" value="${shippingAddressLine1}" />
                        <div class="card-text">${shippingAddressLine2}</div>
                        <input type="hidden" name="shippingAddressLine2" value="${shippingAddressLine2}" />
                        <div class="card-text">${shippingCity}</div>
                        <input type="hidden" name="shippingCity" value="${shippingCity}" />
                        <div class="card-text">${shippingProvince}</div>
                        <input type="hidden" name="shippingProvince" value="${shippingProvince}" />
                        <div class="card-text">${shippingCountry}</div>
                        <input type="hidden" name="shippingCountry" value="${shippingCountry}" />
                        <div class="card-text">${shippingZip}</div>
                        <input type="hidden" name="shippingZip" value="${shippingZip}" />
                        <div class="card-text">${shippingPhone}</div>
                        <input type="hidden" name="shippingPhone" value="${shippingPhone}" />
                    </div>
                </div>
                <br>
                <div class="card">
                    <h4 class="card-header">Enter credit card number</h4>
                    <div class="card-body">
                        <div class="card-text">
                            <!-- Sample credit card number provided for ease of testing -->
                            <input class="form-control" id="creditCard" name="creditCard" required="required" value="4557982426543136"
                                data-parsley-trigger="change" data-parsley-creditcard="" type="tel">
                        </div>
                    </div>
                </div>
		            <br>
		            <div class="mb-5">
		                <button type="submit" class="btn btn-primary">Submit</button>
		            </div>
		        </form>
		    </div>
    </body>
</html>