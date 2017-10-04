//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import entity.*;

public class DBConnection {
   // JDBC driver name and database URL
   private static final String DB_URL = "jdbc:mysql://localhost:3306/database1";

   //  Database credentials
   private static final String USER = "root";
   private static final String PASS = "password";
   
   public List<CD> getAllCDs() {
	   Connection conn = null;
	   Statement stmt = null;
	   List<CD> tmpList = new ArrayList<CD>();
	   
	   // load and register JDBC driver for MySQL 
	   try {
		   Class.forName("com.mysql.jdbc.Driver");
	   } catch (ClassNotFoundException e1) {
		   e1.printStackTrace();
	   } 

	   try{
		   conn = DriverManager.getConnection(DB_URL,USER,PASS);
		   stmt = conn.createStatement();
		   String sql= "SELECT * FROM CD";
		   ResultSet rs = stmt.executeQuery(sql);

		   while(rs.next()){
			   String id  = rs.getString("cdid");
			   String title = rs.getString("title");
			   Integer price = rs.getInt("price");
			   String category = rs.getString("category");
			   CD tmp = new CD(id, title, price, category);
			   tmpList.add(tmp);
		   }
		   rs.close();
		   stmt.close();
		   conn.close();
	   } catch(SQLException se) {
		   se.printStackTrace();
	   } catch(Exception e) {
		   e.printStackTrace();
	   } finally {
		   try {
			   if(stmt!=null) {
				   stmt.close();
			   }
			   if(conn!=null) {
				   conn.close();
			   }
		   } catch(SQLException se2) {	
		   }
	   }
   		return tmpList;
   }
}