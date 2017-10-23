package com.store.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class for getting cd data of a category
 * @author Yicong Li
 * @version 2017-10-22
 *
 */
@WebServlet(description = "get cd data of a category", urlPatterns = {"/getCDs"})
public class GetCDCategoryControllerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 321707570128467932L;
	
	/**
	 * default constructor
	 */
	public GetCDCategoryControllerServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String category = request.getParameter("category");
		String cdData = "hello web page";
		
		
		
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(cdData);
	}

}
