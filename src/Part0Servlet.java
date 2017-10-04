import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.CD;

import java.util.List;

public class Part0Servlet extends HttpServlet { 
	
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  RequestDispatcher rd=request.getRequestDispatcher("index.jsp");  
	  DBConnection DBConn = new DBConnection();
	  List<CD> cd_List = DBConn.getAllCDs();
	  request.setAttribute("cd_list", cd_List);
	  rd.forward(request, response); 
  }  
}