package com.store.controllers;

import java.io.IOException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.store.utils.Handshake;

/**
 * Servlet implementation class OrderControllerServlet
 */
@WebServlet("/order")
public class OrderControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderControllerServlet() {
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
		
		// Invoke the request for the shipping options
		Response shippingResponse =
            client.target("https://localhost:8444/api/order/shipping")
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get();
		
		// Read the responses
        int accountStatus = accountResponse.getStatus();
        int shippingStatus = shippingResponse.getStatus();
        JsonObject account = accountResponse.readEntity(JsonObject.class);
		List<JsonArray> shippingList = shippingResponse.readEntity(new GenericType<List<JsonArray>>(){});
        
        // Get customer info
        JsonObject customer = account.getJsonObject("customer");
        request.setAttribute("email", customer.getString("email"));
        request.setAttribute("firstName", customer.getString("firstName"));
        request.setAttribute("lastName", customer.getString("lastName"));
        
        // Get shipping options
        request.setAttribute("shippingOptions", shippingList);
        
        // Item list
        JsonArray items = Json.createArrayBuilder()
				        	     .add(Json.createObjectBuilder()
			        	            .add("cdid", "cd001")
			        	            .add("title", "16 Biggest Hits")
				        	     	.add("artist", "Johnny Cash")
				        	     	.add("quantity", 1)
				        	     	.add("price", 15.99))
			        	         .add(Json.createObjectBuilder()
			        	        	.add("cdid", "cd002")
					        	    .add("title", "Greatest Hits (& Some That Will Be)")
						        	.add("artist", "Willie Nelson")
						        	.add("quantity", 2)
						        	.add("price", 17.99))
			        	         .build();
        
        // Get item list
        request.setAttribute("items", items);
		
        // Continue with the request if status codes are 200, otherwise send an error message to the client
        if (accountStatus == 200 && shippingStatus == 200) {
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/order.jsp");  
    	    dispatcher.forward(request, response);
        } else if (accountStatus == 401 || shippingStatus == 401) {
        	session.setAttribute("message", "Unauthorized.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 403 || shippingStatus == 403) {
        	session.setAttribute("message", "Forbidden.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 404 || shippingStatus == 404) {
        	session.setAttribute("message", "Not found.");
        	response.sendRedirect(request.getContextPath() + "/store");
        } else if (accountStatus == 500 || shippingStatus == 500) {
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
		
		// Get the session
		HttpSession session = request.getSession();
		
		// Create JSON object for post
		JsonObjectBuilder builder = Json.createObjectBuilder();
		
		// Add PO items to JSON
		JsonArrayBuilder itemsListBuilder = Json.createArrayBuilder();
		int numOfCD = request.getParameterValues("cdid").length;
		for (int i = 0; i < numOfCD; i++) {
			String cdid = request.getParameterValues("cdid")[i];
			String quantity = request.getParameterValues("quantity")[i];
			String price = request.getParameterValues("price")[i];
			JsonObjectBuilder itemBuilder = Json.createObjectBuilder();
			itemBuilder.add("cdId", cdid);
			itemBuilder.add("numOrdered", quantity);
			itemBuilder.add("poId", 0);
			itemBuilder.add("unitPrice", price);
			itemsListBuilder.add(itemBuilder);
		}
		builder.add("poItems", itemsListBuilder);
		builder.add("shippingInfoId", request.getParameter("ship"));
		builder.add("customerEmail",  request.getParameter("email"));
		
		// Build JSON object for the post
		JsonObject jsonObject = builder.build();
		
		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target("https://localhost:8444/api/order/create")
        	.request(MediaType.APPLICATION_JSON)
        	.accept(MediaType.APPLICATION_JSON)
            .post(Entity.json(jsonObject));
		
		// Check the response
        int code = resp.getStatus();
        System.out.println(code);
        
        // If the response is 200/201, otherwise send the error message to the client
        if (code == 200 || code == 201) { 
        	session.setAttribute("message", "Order created successfully.");
        	String poId = resp.readEntity(String.class);
        	session.setAttribute("poId", poId);
        	response.sendRedirect(request.getContextPath() + "/payment");
        } else if (code == 400) {
        	String message = resp.readEntity(JsonObject.class).getString("message");
        	session.setAttribute("message", message);
        	response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 401) {
        	session.setAttribute("message", "Unauthorized.");
        	response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 403) {
        	session.setAttribute("message", "Forbidden.");
        	response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 404) {
        	session.setAttribute("message", "Not found.");
        	response.sendRedirect(request.getContextPath() + "/order");
        } else if (code == 500) {
        	session.setAttribute("message", "Database or server error occurred.");
        	response.sendRedirect(request.getContextPath() + "/order");
        } else {
        	session.setAttribute("message", "Something went wrong.");
        	response.sendRedirect(request.getContextPath() + "/order");
        }
	}
}