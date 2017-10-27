package com.store.controllers;

import java.io.IOException;

import javax.net.ssl.SSLContext;
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

import org.glassfish.jersey.SslConfigurator;

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
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/account.jsp");  
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the form inputs
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String billingFullName = request.getParameter("billingFullName");
		String billingAddressLine1 = request.getParameter("billingAddressLine1");
		String billingAddressLine2 = request.getParameter("billingAddressLine2");
		String billingCity = request.getParameter("billingCity");
		String billingProvince = request.getParameter("billingProvince");
		String billingCountry = request.getParameter("billingCountry");
		String billingZip = request.getParameter("billingZip");
		String billingPhone = request.getParameter("billingPhone");
		String shippingFullName = request.getParameter("shippingFullName");
		String shippingAddressLine1 = request.getParameter("shippingAddressLine1");
		String shippingAddressLine2 = request.getParameter("shippingAddressLine2");
		String shippingCity = request.getParameter("shippingCity");
		String shippingProvince = request.getParameter("shippingProvince");
		String shippingCountry = request.getParameter("shippingCountry");
		String shippingZip = request.getParameter("shippingZip");
		String shippingPhone = request.getParameter("shippingPhone");
		
		// Get the session
		HttpSession session = request.getSession();
		
		// The input to post
		String input = String.format("{\"addressList\": [{\"addressLine1\": \"%s\",\"addressLine2\":\"%s\","
						+"\"city\": \"%s\",\"country\": \"%s\",\"customerId\": 0,\"fullName\": \"%s\","
						+"\"id\": 0,\"phone\": \"%s\",\"province\": \"%s\",\"type\": \"BILLING\","
						+ "\"zip\": \"%s\"},{\"addressLine1\": \"%s\",\"addressLine2\": \"%s\","
						+ "\"city\": \"%s\",\"country\": \"%s\",\"customerId\": 0,"
				      	+ "\"fullName\": \"%s\",\"id\": 0,\"phone\": \"%s\",\"province\": \"%s\","
				      	+ "\"type\": \"SHIPPING\",\"zip\": \"%s\"}],\"customer\": {\"email\": \"%s\","
				      	+ "\"firstName\": \"%s\",\"id\": 0,\"lastName\": \"%s\",\"password\": \"%s\"}}", billingAddressLine1, 
				      	billingAddressLine2, billingCity, billingCountry, billingFullName, billingPhone, billingProvince, billingZip,
				      	shippingAddressLine1, shippingAddressLine2, shippingCity, shippingCountry, shippingFullName, shippingPhone, shippingProvince,
				      	shippingZip, username, firstName, lastName, password);
		
		// Load SSL key to perform handshake with the server
		ServletContext sc = this.getServletContext();
		String kpath = sc.getRealPath("/keystore.p12");
		SslConfigurator sslConfig = SslConfigurator.newInstance()
			.trustStoreFile(kpath)
			.trustStorePassword("password");
		SSLContext sslContext = sslConfig.createSSLContext();
		
		// Create the API client and invoke the request
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
		Response resp = client.target("https://localhost:8444/api/account/create")
        	.request(MediaType.APPLICATION_JSON)
            .post(Entity.json(input));
		
		// Test
		System.out.println(input);
		System.out.println(resp);
		
		// Check the response
        int code = resp.getStatus();
        System.out.println(code);
        
        if (code == 200 || code == 201) { 
        	session.setAttribute("session.username", username);
        } else {
        	session.setAttribute("message", "Action failed.");
        	RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/account.jsp");  
    	    dispatcher.forward(request, response);
        }
	}

}
