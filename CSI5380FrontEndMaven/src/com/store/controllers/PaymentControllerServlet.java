package com.store.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.store.model.Account;
import com.store.model.Address;
import com.store.model.Customer;
import com.store.model.PurchaseOrder;
import com.store.utils.*;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
/**
 * Controller servlet to handle payment for orders
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
@WebServlet("/payment")
public class PaymentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentControllerServlet() {
        super();
    }

	/**
	 * Get account and address info, and forward the user to the payment page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session and attributes
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String encodedPassword = (String) session.getAttribute("password");
		
		// Create the API client and invoke the request to get the account information
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response accountResponse = client.target(Paths.GET_ACCOUNT)
        	.queryParam("username", username)
        	.queryParam("password", encodedPassword)
        	.request(MediaType.TEXT_PLAIN_TYPE)
        	.accept(MediaType.APPLICATION_JSON)
            .get();
		
		// Read the responses
        int accountStatus = accountResponse.getStatus();
        Account account = accountResponse.readEntity(Account.class);
        accountResponse.close();
        
        // Get customer info
        Customer customer = account.getCustomer();
        request.setAttribute("email", customer.getEmail());
        request.setAttribute("firstName", customer.getFirstName());
        request.setAttribute("lastName", customer.getLastName());
	    
	    // Get billing info
        Address billingAddress = account.getDefaultAddressInfo().getBillingAddress();
        request.setAttribute("billingFullName", billingAddress.getFullName());
        request.setAttribute("billingAddressLine1", billingAddress.getAddressLine1());
        request.setAttribute("billingAddressLine2", billingAddress.getAddressLine2());
        request.setAttribute("billingCity", billingAddress.getCity());
        request.setAttribute("billingProvince", billingAddress.getProvince());
        request.setAttribute("billingCountry", billingAddress.getCountry());
        request.setAttribute("billingZip", billingAddress.getZip());
        request.setAttribute("billingPhone", billingAddress.getPhone());
        
        // Get shipping info
        Address shippingAddress = account.getDefaultAddressInfo().getShippingAddress();	
		request.setAttribute("shippingFullName", shippingAddress.getFullName());
        request.setAttribute("shippingAddressLine1", shippingAddress.getAddressLine1());
        request.setAttribute("shippingAddressLine2", shippingAddress.getAddressLine2());
        request.setAttribute("shippingCity", shippingAddress.getCity());
        request.setAttribute("shippingProvince", shippingAddress.getProvince());
        request.setAttribute("shippingCountry", shippingAddress.getCountry());
        request.setAttribute("shippingZip", shippingAddress.getZip());
        request.setAttribute("shippingPhone", shippingAddress.getPhone());
		
        // Continue with the request if status code is 200, otherwise send an error message to the client
        if (accountStatus == 200) {
        	// Must have a PO id in the session
        	if (session.getAttribute("poId") == null) {
	        	session.setAttribute("message", "You must checkout a cart first.");
	        	response.sendRedirect(request.getContextPath() + "/store");
	        } else {
	        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/payment.jsp");  
	    	    dispatcher.forward(request, response);
	        }
        } else if (accountStatus == 401) {
        	session.setAttribute("message", "Unauthorized.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 403) {
        	session.setAttribute("message", "Forbidden.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 404) {
        	session.setAttribute("message", "Not found.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 500) {
        	session.setAttribute("message", "Database or server error occurred.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else {
        	session.setAttribute("message", "Something went wrong.");
        	response.sendRedirect(request.getContextPath() + "/store");
        }
	}

	/**
	 * Pass the account id, credit card number and address info to the order confirmation service
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// Create JSON object for post
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		// Add billing address to JSON
		JsonObjectBuilder billingAddressBuilder = Json.createObjectBuilder();
		billingAddressBuilder.add("fullName", request.getParameter("billingFullName"));
		billingAddressBuilder.add("addressLine1", request.getParameter("billingAddressLine1"));
		billingAddressBuilder.add("addressLine2", request.getParameter("billingAddressLine2"));
		billingAddressBuilder.add("city", request.getParameter("billingCity"));
		billingAddressBuilder.add("province", request.getParameter("billingProvince"));
		billingAddressBuilder.add("country", request.getParameter("billingCountry"));
		billingAddressBuilder.add("zip", request.getParameter("billingZip"));
		billingAddressBuilder.add("phone", request.getParameter("billingPhone"));
		billingAddressBuilder.add("type", "BILLING");
		billingAddressBuilder.add("id", -1);
		builder.add("billingAddress", billingAddressBuilder);

		// Add shipping address to JSON
		JsonObjectBuilder shippingAddressBuilder = Json.createObjectBuilder();
		shippingAddressBuilder.add("fullName", request.getParameter("shippingFullName"));
		shippingAddressBuilder.add("addressLine1", request.getParameter("shippingAddressLine1"));
		shippingAddressBuilder.add("addressLine2", request.getParameter("shippingAddressLine2"));
		shippingAddressBuilder.add("city", request.getParameter("shippingCity"));
		shippingAddressBuilder.add("province", request.getParameter("shippingProvince"));
		shippingAddressBuilder.add("country", request.getParameter("shippingCountry"));
		shippingAddressBuilder.add("zip", request.getParameter("shippingZip"));
		shippingAddressBuilder.add("phone", request.getParameter("shippingPhone"));
		shippingAddressBuilder.add("type", "SHIPPING");
		shippingAddressBuilder.add("id", -1);
		builder.add("shippingAddress", shippingAddressBuilder);
		
		// Build JSON object for the post
		String input = builder.build().toString();
		session.setAttribute("purchaseOrderDetail", input);
		
		// Set payer details
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		// Set redirect URLs
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("https://localhost:8443/CSI5380FrontEnd/payment");
		redirectUrls.setReturnUrl("https://localhost:8443/CSI5380FrontEnd/paypalResp");

		PurchaseOrder po = (PurchaseOrder) session.getAttribute("po");
		BigDecimal shipTotal = po.getShippingTotal().setScale(2, BigDecimal.ROUND_CEILING);

		BigDecimal taxTotal = po.getTaxTotal().setScale(2, BigDecimal.ROUND_CEILING);
		BigDecimal subTotal = po.getSubTotal().setScale(2, BigDecimal.ROUND_CEILING);
		
		// Set payment details
		Details details = new Details();
		details.setShipping(shipTotal.toPlainString());
		details.setSubtotal(subTotal.toPlainString());
		details.setTax(taxTotal.toPlainString());

		// Payment amount
		Amount amount = new Amount();
		amount.setCurrency("CAD");
		// Total must be equal to sum of shipping, tax and subtotal.
		BigDecimal grandTotal = shipTotal.add(subTotal).add(taxTotal);
		amount.setTotal(grandTotal.toPlainString());
		amount.setDetails(details);

		// Transaction information
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("This is for order "+ po.getId() + " in I Music. (tax included)");
		
		// Add transaction to a list
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Add payment details
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setRedirectUrls(redirectUrls);
		payment.setTransactions(transactions);
		try {
			  Payment createdPayment = payment.create(PayPalHelper.getAPIContext());

			  Iterator<Links> links = createdPayment.getLinks().iterator();
			  while (links.hasNext()) {
			    Links link = links.next();
			    if (link.getRel().equalsIgnoreCase("approval_url")) {
			    	response.sendRedirect(link.getHref());
			    }
			  }
			} catch (PayPalRESTException e) {
			    System.err.println(e.getDetails());
			}
	}

}
