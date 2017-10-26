package com.store.controllers;

import java.io.IOException;

import javax.net.ssl.SSLContext;
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

import org.glassfish.jersey.SslConfigurator;

import com.store.utils.Paths;

/**
 * Servlet implementation class for initializing the cart
 * @author Yicong Li
 * @version 2017-10-26
 *
 */
@WebServlet(description = "go to shopping cart", urlPatterns = {"/goToCart"})
public class GoToCartControllerServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8401566769847653494L;
	
	/**
	 * default constructor
	 */
	public GoToCartControllerServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// form result begins
		// initialize result
		String result = "[";
		
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
		
		// get session data
		HttpSession session = request.getSession(true);
		String cdList = (String) session.getAttribute("cdList");
		if (cdList != null) {
			String[] cdIds = cdList.split(" ");
			for (int i = 0; i < cdIds.length; i++) {
				Integer cdCount = (Integer) session.getAttribute(cdIds[i] + ".counter");
				if (cdCount != null) {	
					String aResult = 
							client.target(Paths.CD_INFO + cdIds[i] + "/" + cdCount.intValue())
							.request(MediaType.APPLICATION_JSON)
							.get(String.class);
					
					// add this cd result to final result
					result += aResult;
					// if this is not the last one
					if (i < cdIds.length - 1) {
						result += ",";
					}
				}
			}
		}
		
		// form result ends
		result += "]";
				
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}

}
