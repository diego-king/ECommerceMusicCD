package com.store.controllers;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonObject;
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

import com.store.utils.Handshake;

/**
 * Servlet implementation class Payment
 */
@WebServlet("/payment")
public class PaymentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the session
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String encodedPassword = (String) session.getAttribute("password");
		
		// Create the API client and invoke the request to get the account information
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response accountResponse = client.target("https://localhost:8444/api/account")
        	.queryParam("username", username)
        	.queryParam("password", encodedPassword)
        	.request(MediaType.TEXT_PLAIN_TYPE)
        	.accept(MediaType.APPLICATION_JSON)
            .get();
		
		// Read the responses
        int accountStatus = accountResponse.getStatus();
        JsonObject account = accountResponse.readEntity(JsonObject.class);
        
        // Get customer info
        JsonObject customer = account.getJsonObject("customer");
        request.setAttribute("email", customer.getString("email"));
        request.setAttribute("firstName", customer.getString("firstName"));
        request.setAttribute("lastName", customer.getString("lastName"));
	    
	    // Get billing info
	    JsonObject billingAddress = account.getJsonObject("defaultAddressInfo").getJsonObject("billingAddress");
        request.setAttribute("billingFullName", billingAddress.getString("fullName"));
        request.setAttribute("billingAddressLine1", billingAddress.getString("addressLine1"));
        request.setAttribute("billingAddressLine2", billingAddress.getString("addressLine2"));
        request.setAttribute("billingCity", billingAddress.getString("city"));
        request.setAttribute("billingProvince", billingAddress.getString("province"));
        request.setAttribute("billingCountry", billingAddress.getString("country"));
        request.setAttribute("billingZip", billingAddress.getString("zip"));
        request.setAttribute("billingPhone", billingAddress.getString("phone"));
        
        // Get shipping info
	    JsonObject shippingAddress = account.getJsonObject("defaultAddressInfo").getJsonObject("shippingAddress");	
		request.setAttribute("shippingFullName", shippingAddress.getString("fullName"));
        request.setAttribute("shippingAddressLine1", shippingAddress.getString("addressLine1"));
        request.setAttribute("shippingAddressLine2", shippingAddress.getString("addressLine2"));
        request.setAttribute("shippingCity", shippingAddress.getString("city"));
        request.setAttribute("shippingProvince", shippingAddress.getString("province"));
        request.setAttribute("shippingCountry", shippingAddress.getString("country"));
        request.setAttribute("shippingZip", shippingAddress.getString("zip"));
        request.setAttribute("shippingPhone", shippingAddress.getString("phone"));
		
        // Continue with the request if status code is 200, otherwise send an error message to the client
        if (accountStatus == 200) {
        	// Must have a PO id in the session
        	if (session.getAttribute("poId") == null) {
	        	session.setAttribute("message", "You must checkout a cart first.");
	        	response.sendRedirect(request.getContextPath() + "/store");
	        } else {
	        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/payment.jsp");  
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the session and PO id attribute
		HttpSession session = request.getSession();
		String poId = (String) session.getAttribute("poId");
		
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
		JsonObject jsonObject = builder.build();

		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target("https://localhost:8444/api/order/confirm/" + poId)
				.queryParam("card", request.getParameter("creditCard"))
	        	.request(MediaType.APPLICATION_JSON)
	        	.accept(MediaType.APPLICATION_JSON)
	        	.post(Entity.json(jsonObject));
		
		// Check the response
        int code = resp.getStatus();
        System.out.println(code);

		// Confirm or deny order, otherwise send the error message to the client
	        if (code == 200 || code == 201) {
	        	String success = resp.readEntity(String.class);
	        	System.out.println(success);
	        	if (success.equals("true")) {
	        		session.setAttribute("message", "Order successfully completed.");
	        		// Need to invalidate the cart session attributes at this point
	        		session.removeAttribute("poId");
	        		response.sendRedirect(request.getContextPath() + "/store");
	        	} else {
	        		session.setAttribute("message", "Credit card authorization failed.");
	        		response.sendRedirect(request.getContextPath() + "/payment");
	        	}
	        } else if (code == 400) {
	        	session.setAttribute("message", "Bad request.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 401) {
	        	session.setAttribute("message", "Unauthorized.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 403) {
	        	session.setAttribute("message", "Forbidden.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 404) {
	        	session.setAttribute("message", "Not found.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 500) {
	        	session.setAttribute("message", "Database or server error occurred.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else {
	        	session.setAttribute("message", "Something went wrong.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
		}

	}

}
