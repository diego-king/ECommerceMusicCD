package com.store.controllers;

import java.io.IOException;
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
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.store.utils.Handshake;
import com.google.gson.Gson;
import com.store.model.*;

import com.store.utils.Paths;

/**
 * Controller servlet to handle processing of orders
 * 
 * @author Mike Kreager
 * @version 2017-10-28
 *
 */
@WebServlet("/order")
public class OrderControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderControllerServlet() {
        super();
    }

	/**
	 * Get the account info and shipping options, and forward the user to the order page
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the session
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
		
        int accountStatus = accountResponse.getStatus();
        accountResponse.close();
        
		// Invoke the request for the shipping options
		Response shippingResponse =
            client.target(Paths.GET_SHIPPING)
            .request()
            .accept(MediaType.APPLICATION_JSON)
            .get();
		
        int shippingStatus = shippingResponse.getStatus();
		List<ShippingInfo> shippingList = shippingResponse.readEntity(new GenericType<List<ShippingInfo>>(){});
		shippingResponse.close();
		
		// Get shipping options
		request.setAttribute("shippingOptions", shippingList);
		
        // Get customer email
		String email = (String) session.getAttribute("username");
        request.setAttribute("email", email);
               
        // Continue with the request if status codes are 200, otherwise send an error message to the client
        if (accountStatus == 200 && shippingStatus == 200) {
        	// Redirect the user if the cart was empty
    		if (session.getAttribute("session.order") == null) {
    			session.setAttribute("message", "Your cart is empty.");
            	response.sendRedirect(request.getContextPath() + "/store");
    		} else {
    			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/order.jsp");
    			dispatcher.forward(request, response);
    		}
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
	 * Pass the carts items, account id and shipping info to the order creation service
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the session and attributes
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("username");
		String cart = (String) session.getAttribute("session.order");
        request.setAttribute("email", email);
		
		// Build JSON object for post
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("customerEmail",  email);
		builder.add("shippingInfoId", request.getParameter("ship"));
		String input = builder.build().toString() + '"' + "poItems" + '"' + ':' + cart + '}';
		
		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target(Paths.CREATE_ORDER)
        	.request(MediaType.APPLICATION_JSON)
        	.accept(MediaType.APPLICATION_JSON)
            .post(Entity.json(input));
		
        int code = resp.getStatus();
        System.out.println(code);
        
        // If the response is 200/201, otherwise send the error message to the client
        if (code == 200 || code == 201) { 
        	Gson gson = new Gson();
        	String poId = resp.readEntity(String.class);
        	String json = gson.fromJson(poId, String.class);
        	session.setAttribute("poId", json);
        	session.setAttribute("message", "Order number " + json + " created successfully.");
        	session.removeAttribute("session.order");
        	response.sendRedirect(request.getContextPath() + "/payment");
        } else if (code == 400) {
        	String message = resp.readEntity(String.class);
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
        resp.close();
	}
}