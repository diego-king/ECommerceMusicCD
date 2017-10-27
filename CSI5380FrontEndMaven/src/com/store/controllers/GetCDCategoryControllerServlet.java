package com.store.controllers;

import java.io.IOException;

import javax.net.ssl.SSLContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.SslConfigurator;

import com.store.utils.Paths;

/**
 * Servlet implementation class for getting cd data of a category
 * @author Yicong Li
 * @version 2017-10-25
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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String category = request.getParameter("category");
		// refer to AccountControllerServlet
		// load ssl configuration
		ServletContext servletContext = this.getServletContext();
		String keyPath = servletContext.getRealPath("/keystore.p12");
		SslConfigurator sslConfigurator = SslConfigurator.newInstance()
				.trustStoreFile(keyPath)
				.trustStorePassword("password");
		SSLContext sslContext = sslConfigurator.createSSLContext();
		
		// create the api client and invoke the request
		Client client = ClientBuilder.newBuilder().sslContext(sslContext).build();
		String result = client.target(Paths.CD_CATEGORY + category)
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}

}
