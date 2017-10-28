package com.store.controllers;

import java.io.IOException;
import java.util.Base64;

import javax.json.JsonObject;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.store.utils.Handshake;

/**
 * Servlet implementation class RegisterControllerServlet
 */
@WebServlet("/login")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/login.jsp");  
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the form inputs
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
		
		// Get the session
		HttpSession session = request.getSession();
		
		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target("https://localhost:8444/api/account/login")
        	.queryParam("username", email)
        	.queryParam("password", encodedPassword)
        	.request(MediaType.TEXT_PLAIN_TYPE)
        	.accept(MediaType.APPLICATION_JSON)
            .get();
		
		// Check the response
        int code = resp.getStatus();
        System.out.println(code);
        
        // Login if the code is 200, otherwise send the error message to the client
        if (code == 200) {
        	session.setAttribute("message", "Login successful");
        	session.setAttribute("username", email);  
        	session.setAttribute("password", encodedPassword);
        	// Check if the login request came from an order checkout attempt and redirect accordingly
        	String checkout = (String) session.getAttribute("checkout");
        	if (checkout != null && checkout.equals("true")) {
        		session.setAttribute("checkout", null);
        		response.sendRedirect(request.getContextPath() + "/order");
        	} else {
        		response.sendRedirect(request.getContextPath() + "/store");
        	}
        } else if (code == 400 || code == 404) {
        	JSONParser parser = new JSONParser();
        	String body = resp.readEntity(String.class);
        	JSONObject json = null;
			try {
				json = (JSONObject) parser.parse(body);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        	session.setAttribute("message", json.get("message").toString());
        	response.sendRedirect(request.getContextPath() + "/login");
        } else if (code == 401) {
        	session.setAttribute("message", "Unauthorized.");
        	response.sendRedirect(request.getContextPath() + "/login");
        } else if (code == 403) {
        	session.setAttribute("message", "Forbidden.");
        	response.sendRedirect(request.getContextPath() + "/login");
        } else if (code == 500) {
        	session.setAttribute("message", "Database or server error occurred.");
        	response.sendRedirect(request.getContextPath() + "/login");
        } else {
        	session.setAttribute("message", "Something went wrong.");
        	response.sendRedirect(request.getContextPath() + "/login");
        }
	}

}