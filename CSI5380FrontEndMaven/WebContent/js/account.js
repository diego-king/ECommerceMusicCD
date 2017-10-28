// Copy billing information to shipping information
$(document).ready(function() {
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
});