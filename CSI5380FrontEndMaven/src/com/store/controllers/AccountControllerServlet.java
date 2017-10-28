package com.store.controllers;

import java.io.IOException;
import java.util.Base64;

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
 * Servlet implementation class AccountControllerServlet
 */
@WebServlet("/account")
public class AccountControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/account.jsp");  
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the email and password form inputs
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		
		// Get the session
		HttpSession session = request.getSession();
		
		// Create JSON object for post
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		// Add customer info to JSON
		JsonObjectBuilder customerInfoBuilder = Json.createObjectBuilder();
		customerInfoBuilder.add("firstName", request.getParameter("firstName"));
		customerInfoBuilder.add("lastName", request.getParameter("lastName"));
		customerInfoBuilder.add("email", email);
		customerInfoBuilder.add("password", encodedPassword);
		customerInfoBuilder.add("defaultBillingAddressId", -1);
		customerInfoBuilder.add("defaultShippingAddressId", -1);
		builder.add("customer", customerInfoBuilder);
		
		// Add addresses to JSON
		JsonObjectBuilder addressBuilder = Json.createObjectBuilder();
		builder.add("defaultAddressInfo", addressBuilder);
		
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
		addressBuilder.add("billingAddress", billingAddressBuilder);
		addressBuilder.add("defaultAddressInfo", billingAddressBuilder);
		
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
		addressBuilder.add("shippingAddress", shippingAddressBuilder);
		
		// Build JSON object for the post
		JsonObject jsonObject = builder.build();
		
		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target("https://localhost:8444/api/account/create")
        	.request(MediaType.APPLICATION_JSON)
        	.accept(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonObject));
		
		// Check the response
		System.out.println(resp);
        int code = resp.getStatus();
        System.out.println(code);
        
        // Login if the code is 200/201, otherwise send the error message to the client
        if (code == 200 || code == 201) { 
        	session.setAttribute("message", "Account created successfully.");
        	session.setAttribute("username", email);
        	session.setAttribute("password", encodedPassword);
        	// Check if the sign up request came from an order checkout attempt and redirect accordingly
        	String checkout = (String) session.getAttribute("checkout");
        	if (checkout != null && checkout.equals("true")) {
        		session.removeAttribute("checkout");
        		response.sendRedirect(request.getContextPath() + "/order");
        	} else {
        		response.sendRedirect(request.getContextPath() + "/store");
        	}
        } else if (code == 400) {
        	String message = resp.readEntity(JsonObject.class).getString("message");
        	session.setAttribute("message", message);
        	response.sendRedirect(request.getContextPath() + "/account");
        } else if (code == 401) {
        	session.setAttribute("message", "Unauthorized.");
        	response.sendRedirect(request.getContextPath() + "/account");
        } else if (code == 403) {
        	session.setAttribute("message", "Forbidden.");
        	response.sendRedirect(request.getContextPath() + "/account");
        } else if (code == 404) {
        	session.setAttribute("message", "Not found.");
        	response.sendRedirect(request.getContextPath() + "/account");
        } else if (code == 500) {
        	session.setAttribute("message", "Database or server error occurred.");
        	response.sendRedirect(request.getContextPath() + "/account");
        } else {
        	session.setAttribute("message", "Something went wrong.");
        	response.sendRedirect(request.getContextPath() + "/account");
        }
	}

}
