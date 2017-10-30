package com.store.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class for adding cd to the shopping cart
 * 
 * @author Yicong Li
 *
 */
@WebServlet(description = "add one cd into the cart", urlPatterns = { "/addToCart" })
public class AddToCartControllerServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -4595855403457375148L;

    /**
     * default constructor
     */
    public AddToCartControllerServlet() {
        // TODO Auto-generated constructor stub
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String id = request.getParameter("id");

        // get session object
        HttpSession session = request.getSession(true);

        // get the list of cd in the cart
        String cdList = (String) session.getAttribute("cdList");
        // create a new list of cd in the cart if there is no such list
        if (cdList == null) {
            cdList = "";
        }
        // do nothing if there is a cdList

        // get the session data vaule of cd counter
        Integer cdCount = (Integer) session.getAttribute(id + ".counter");
        if (cdCount == null) {
            // create a new cd counter
            cdCount = Integer.valueOf(1);
            // add this cd to list
            cdList += (id + " ");
        } else {
            // add counter
            cdCount++;
            // cdList has no change
        }

        // set session data
        session.setAttribute(id + ".counter", cdCount);
        session.setAttribute("cdList", cdList);

        // set response data
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Success!");
    }
}
