<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<link rel="stylesheet" href="/css/parsley.css">
<title>Create Account</title>
</head>
<body>
	<c:if test="${sessionScope.message != null}">
		<p class="alert alert-info" role="alert">
			<c:out value="${sessionScope.message}" />
			<c:remove var="message" scope="session" />
		</p>
	</c:if>
  <div class="container w-50">
    <form method="POST" action="account" data-parsley-validate>
    	<h2>Create a new account</h2>
		  <div class="form-row">
		  	<div class="form-group col-md-6">
		      <label for="firstName" class="col-form-label">First name *</label>
		      <input type="text" class="form-control" id="firstName" name="firstName" data-parsley-trigger="change" required>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="lastName" class="col-form-label">Last name *</label>
		      <input type="text" class="form-control" id="lastName" name="lastName" data-parsley-trigger="change" required>
		    </div>
			</div>
	    <div class="form-group">
	      <label for="username" class="col-form-label">Username (email) *</label>
	      <input type="email" class="form-control" id="username"  name="username" data-parsley-trigger="change" required>
	    </div>
			<div class="form-row">
		    <div class="form-group col-md-6">
		      <label for="password" class="col-form-label">Password *</label>
		      <input type="password" class="form-control" id="password" name="password" minlength="6" 
		      		data-parsley-minlength-message="Password must be at least 6 characters." data-parsley-trigger="change" required>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="password_check" class="col-form-label">Re-enter password *</label>
		      <input type="password" class="form-control" id="passwordCheck" name="passwordCheck" data-parsley-equalto="#password"
		      		data-parsley-equalto-message="Passwords do not match." data-parsley-trigger="change" required>
		    </div>
		  </div>
		  <h2>Billing information</h2>
		  <div class="form-group">
	      <label for="billingFullName" class="col-form-label">Full name *</label>
	      <input type="text" class="form-control" id="billingFullName" name="billingFullName" data-parsley-trigger="change" required>
	    </div>
	    <div class="form-group">
	      <label for="billingAddressLine1" class="col-form-label">Address 1 *</label>
	      <input type="text" class="form-control" id="billingAddressLine1" name="billingAddressLine1" data-parsley-trigger="change" required>
			</div>
	    <div class="form-group">
	      <label for="billingAddressLine2" class="col-form-label">Address 2 *</label>
	      <input type="text" class="form-control" id="billingAddressLine2" name="billingAddressLine2" data-parsley-trigger="change" required>
      </div>
      <div class="form-row">
	      <div class="form-group col-md-6">
		      <label for="billingCity" class="col-form-label">City *</label>
		      <input type="text" class="form-control" id="billingCity" name="billingCity" data-parsley-trigger="change" required>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="billingProvince" class="col-form-label">Province *</label>
		      <input type="text" class="form-control" id="billingProvince" name="billingProvince" data-parsley-trigger="change" required>
		    </div>
			</div>
			<div class="form-row">
		    <div class="form-group col-md-6">
		      <label for="billingCountry" class="col-form-label">Country *</label>
		      <input type="text" class="form-control" id="billingCountry" name="billingCountry" data-parsley-trigger="change" required>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="billingZip" class="col-form-label">Zip Code *</label>
		      <input type="text" class="form-control" id="billingZip" name="billingZip" data-parsley-trigger="change" required>
		    </div>
			</div>
	    <div class="form-group">
	      <label for="billingPhone" class="col-form-label">Phone Number *</label>
	      <input type="text" class="form-control" id="billingPhone" name="billingPhone" data-parsley-trigger="change" required>
	    </div>
		  <h2>Shipping information</h2>
		  <div class="form-group">
		    <div class="form-check">
		      <label class="form-check-label">
		        <input class="form-check-input" type="checkbox" id="sameAsBilling"> Same as billing
		      </label>
		    </div>
		  </div>
		  <div class="form-group">
	      <label for="shippingFullName" class="col-form-label">Full name *</label>
	      <input type="text" class="form-control" id="shippingFullName" name="shippingFullName" data-parsley-trigger="change" required>
	    </div>
	    <div class="form-group">
	      <label for="shippingAddressLine1" class="col-form-label">Address 1 *</label>
	      <input type="text" class="form-control" id="shippingAddressLine1" name="shippingAddressLine1" data-parsley-trigger="change" required>
			</div>
	    <div class="form-group">
	      <label for="shippingAddressLine2" class="col-form-label">Address 2 *</label>
	      <input type="text" class="form-control" id="shippingAddressLine2" name="shippingAddressLine2" data-parsley-trigger="change" required>
      </div>
      <div class="form-row">
	      <div class="form-group col-md-6">
		      <label for="shippingCity" class="col-form-label">City *</label>
		      <input type="text" class="form-control" id="shippingCity" name="shippingCity" data-parsley-trigger="change" required>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="shippingProvince" class="col-form-label">Province *</label>
		      <input type="text" class="form-control" id="shippingProvince" name="shippingProvince" data-parsley-trigger="change" required>
		    </div>
			</div>
			<div class="form-row">
		    <div class="form-group col-md-6">
		      <label for="shippingCountry" class="col-form-label">Country *</label>
		      <input type="text" class="form-control" id="shippingCountry" name="shippingCountry" data-parsley-trigger="change" required>
		    </div>
		    <div class="form-group col-md-6">
		      <label for="shippingZip" class="col-form-label">Zip Code *</label>
		      <input type="text" class="form-control" id="shippingZip" name="shippingZip" data-parsley-trigger="change" required>
		    </div>
			</div>
	    <div class="form-group">
	      <label for="shippingPhone" class="col-form-label">Phone Number *</label>
	      <input type="text" class="form-control" id="shippingPhone" name="shippingPhone" data-parsley-trigger="change" required>
	    </div>
		  <button type="submit" class="btn btn-primary">Submit</button>
		 </form>
		 <br>
		 <p>Already signed up? <a href="<%= request.getContextPath() %>/login">Log in here</a>.
  </div>
  <script src="/js/jquery-3.2.1.min.js"></script>
  <script src="/js/bootstrap.min.js"></script>
  <script src="/js/parsley.min.js"></script>
  <script>
    // Copy billing information to shipping information
    $("#sameAsBilling").on("change", function(){
	  if (this.checked) {
		  $("[id='shippingFullName']").val($("[id='billingFullName']").val());
	    $("[id='shippingAddressLine1']").val($("[id='billingAddressLine1']").val());
	    $("[id='shippingAddressLine2']").val($("[id='billingAddressLine2']").val());
	    $("[id='shippingCity']").val($("[id='billingCity']").val());
	    $("[id='shippingProvince']").val($("[id='billingProvince']").val());
	    $("[id='shippingCountry']").val($("[id='billingCountry']").val());
	    $("[id='shippingZip']").val($("[id='billingZip']").val());
	    $("[id='shippingPhone']").val($("[id='billingPhone']").val());
	  }
    });
  </script>
</body>
</html>