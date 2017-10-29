package com.store.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.utils.Paths;

/**
 * Servlet implementation class for forwarding payment to order servlet
 * @author Yicong Li
 * @version 2017-10-22
 *
 */
@WebServlet(description = "Pay a order", urlPatterns = {"/payOrder"})
public class PayOrderControllerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1909637730441113379L;
	
	/**
	 * default constructor
	 */
	public PayOrderControllerServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String postData = request.getParameter("data");
				
		// step 1: get the session object
		boolean create = true;
		HttpSession session = request.getSession(create);
		
		// step 2: set the session data value
		session.setAttribute("session.order", postData);
		
		// step 3: redirect to account order?
		String redirectURL = Paths.CHECKOUT;

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(redirectURL);
	}

}
