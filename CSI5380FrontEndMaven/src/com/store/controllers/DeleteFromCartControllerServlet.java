package com.store.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for deleting cd from the cart
 * @author Yicong Li
 *
 */
@WebServlet(description = "delete all items of a cd in the cart", 
			urlPatterns = "/deleteFromCart")
public class DeleteFromCartControllerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2196228614723800272L;
	
	/**
	 * default constructor
	 */
	public DeleteFromCartControllerServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id =  request.getParameter("id");
		
		// get session
		HttpSession session = request.getSession(true);
		
		// get the session data of a cd
		Integer cdCount = (Integer) session.getAttribute(id + ".counter");
		if (cdCount != null) {
			// delete cd counter
			session.removeAttribute(id + ".counter");
			String cdList = (String) session.getAttribute("cdList");
			if (cdList != null && cdList.contains(id)) {
				String newCdList = cdList.replaceAll(id, "");
				// update cd list in session
				session.setAttribute("cdList", newCdList);
			}
		}
	}
}
