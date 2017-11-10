/**
 * 
 */
package com.store.controllers;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
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

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.PayPalRESTException;
import com.store.utils.Handshake;
import com.store.utils.Paths;
import com.store.utils.PayPalHelper;

/**
 * @author Die JIN
 *
 */
/**
 * Servlet implementation class for handling paypal response
 * 
 * @author Die JIN
 */
@WebServlet(description = "Paypal Response handle", urlPatterns = { "/paypalResp" })
public class PayPalRespControllerServlet extends HttpServlet {
    
	public PayPalRespControllerServlet() {
        super();
    }
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Payment payment = new Payment();
		payment.setId(request.getParameter("paymentId"));

		PaymentExecution paymentExecution = new PaymentExecution();
		paymentExecution.setPayerId(request.getParameter("PayerID"));
		try {
		  Payment createdPayment = payment.execute(PayPalHelper.getAPIContext(), paymentExecution);
		  System.out.println(createdPayment);
		} catch (PayPalRESTException e) {
		  System.err.println(e.getDetails());
		}
		
		// Get the session and PO id attribute
		HttpSession session = request.getSession();
		Long poId = (long) session.getAttribute("poId");
		
		// Build JSON object for the post
		String input = (String) session.getAttribute("purchaseOrderDetail");

		// Create the API client and invoke the request
		ServletContext sc = this.getServletContext();
		Client client = ClientBuilder.newBuilder().sslContext(Handshake.getSslContext(sc)).build();
		Response resp = client.target(Paths.CONFIRM_ORDER + poId)
				.queryParam("card", "")
	        	.request(MediaType.APPLICATION_JSON)
	        	.accept(MediaType.APPLICATION_JSON)
	        	.post(Entity.json(input));
		
		// Check the response
        int code = resp.getStatus();

		// Confirm or deny order, otherwise send the error message to the client
	        if (code == 200 || code == 201) {
	        	String success = resp.readEntity(String.class);
	        	if (success.equals("true")) {
	        		session.setAttribute("message", "Order successfully completed.");
	        		// Remove the PO id session attribute after order confirmation
	        		session.removeAttribute("poId");
	        		response.sendRedirect(request.getContextPath() + "/store");
	        	} else {
	        		session.setAttribute("message", "Credit card authorization failed.");
	        		response.sendRedirect(request.getContextPath() + "/payment");
	        	}
	        } else if (code == 400) {
	        	session.setAttribute("message", "Bad request.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 401) {
	        	session.setAttribute("message", "Unauthorized.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 403) {
	        	session.setAttribute("message", "Forbidden.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 404) {
	        	session.setAttribute("message", "Not found.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else if (code == 500) {
	        	session.setAttribute("message", "Database or server error occurred.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
	        } else {
	        	session.setAttribute("message", "Something went wrong.");
	        	response.sendRedirect(request.getContextPath() + "/payment");
		}
        resp.close();
    }

	/** If Paypal payment is success,
	 * Pass the account id, credit card number and address info to the order confirmation service
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
