package db;
//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.commons.dbcp2.BasicDataSource;
import entity.*;

public class DBAgent {

   private static final String DB_URL = "db_url";
   private static final String PASSWORD = "password";
   private static final String USER = "user";
   private static final String DRIVER_CLASS = "driver_class";
   private static final String DB_CONFIG_FILE = "DBConfig";
   private static final String QUERY_CONFIG_FILE = "queries";
   private static final String REMOVE_ABANDON_TIMEOUT = "removeAbandonedTimeout";
   private static final String INIT_CONN_SIZE = "initialSize";
   private static final String MAX_CONN = "maxTotal";
   private static final String MAX_IDLE = "maxIdle";
   private static final String MIN_IDLE = "minIdle";
   public static final String GET_ALL_PRODUCT_QUERY = "getAllProdList";
   public static final String GET_CAT_LIST_QUERY = "getCatList";
   public static final String GET_PROD_LIST_BY_CAT_QUERY = "getProdListByCat";
   public static final String GET_PROD_INFO_QUERY = "getProdInfo";
   
   private static DBAgent instance;
   private final BasicDataSource ds;
   
   private DBAgent() {
	   ResourceBundle dbConfig = ResourceBundle.getBundle(DB_CONFIG_FILE);
	   
	   ds = new BasicDataSource();
	   ds.setDriverClassName(dbConfig.getString(DRIVER_CLASS));
	   ds.setUsername(dbConfig.getString(USER));
       ds.setPassword(dbConfig.getString(PASSWORD));
       ds.setUrl(dbConfig.getString(DB_URL));
       ds.setRemoveAbandonedOnBorrow(true);
       ds.setRemoveAbandonedOnMaintenance(true);
       Integer timeout = Integer.parseInt(dbConfig.getString(REMOVE_ABANDON_TIMEOUT));
       ds.setRemoveAbandonedTimeout(timeout);
       
       /**
        * Change Parameters to make DBAgent 
        */
       Integer initSize = Integer.parseInt(dbConfig.getString(INIT_CONN_SIZE));
       ds.setInitialSize(initSize);
       
       Integer maxTotal = Integer.parseInt(dbConfig.getString(MAX_CONN));
       ds.setMaxTotal(maxTotal);
       
       Integer maxIdle = Integer.parseInt(dbConfig.getString(MAX_IDLE));
       ds.setMaxIdle(maxIdle);
       
       Integer minIdle = Integer.parseInt(dbConfig.getString(MIN_IDLE));
       ds.setMinIdle(minIdle);
   }
   
   public static DBAgent getInstance() {
	   if (instance == null)
		   instance = new DBAgent();
	   return instance;
   }
   
   /**
    * @param queryId
    * @param parameters
    * @return
    */
   private int executeSQL(String queryId, List<String> parameters) {
	   int result = -1;
	   ResourceBundle dbConfig = ResourceBundle.getBundle(QUERY_CONFIG_FILE);
	   String query = dbConfig.getString(queryId);
	   Connection connection = null;
	   PreparedStatement  statement = null;
	   try {
		   connection = ds.getConnection();
	       statement = connection.prepareStatement(query);
	       int i = 1;
	       for (String param : parameters) {
	    	   statement.setString(i, param);
	       }
	       result = statement.executeUpdate();
	   }  catch (Exception e) {
		   e.printStackTrace();
	   } finally {
	       try {   
	    	   if (statement != null) {
	    		   statement.close(); 
	    	   }
	    	   
	    	   if (connection != null) {
	    		   connection.close();
	    	   }
	       } catch (SQLException e) {
	    	   e.printStackTrace();
           }
	   }
	   return result;
   }
   
   /**
    * Execute Query based on connection pool.
    * @param queryId
    * @param parameters
    * @return
    */
   private List<Map<String, String>> getQueryResult(String queryId, List<String> parameters) {
	   ResourceBundle dbConfig = ResourceBundle.getBundle(QUERY_CONFIG_FILE);
	   String query = dbConfig.getString(queryId);
	   
	   Connection connection = null;
	   PreparedStatement  statement = null;
	   ResultSet resultSet = null;
	   List<Map<String, String>> results = new ArrayList<Map<String, String>>();
	   
	   try {
	       connection = ds.getConnection();
	       statement = connection.prepareStatement(query);
	       
	       int index = 1;
	       for (String param : parameters) {
	    	   statement.setString(index, param);
	       }
	       
	       resultSet = statement.executeQuery();
	       
	       while (resultSet.next()) {
	    	   Map<String, String> tmpMap = new HashMap<String, String>();
	    	   for (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++) {
	    		   String key = resultSet.getMetaData().getColumnLabel(i);
	    		   String val = resultSet.getString(key);
	    		   tmpMap.put(key, val);
	    	   }
	    	   results.add(tmpMap);
	       }
	   } catch (SQLException e) {
	       e.printStackTrace();
	   } finally {
	       try { 
	    	   if (resultSet != null) {
	    		   resultSet.close();
	    	   }
	    	   
	    	   if (statement != null) {
	    		   statement.close(); 
	    	   }
	    	   
	    	   if (connection != null) {
	    		   connection.close();
	    	   }
	       } catch (SQLException e) {
	    	   e.printStackTrace();
           }
	   }
	   return results;
   }
   
   public List<String> getAllCatList() {
	   List<Map<String, String>> catList = this.getQueryResult(DBAgent.GET_CAT_LIST_QUERY, new ArrayList());
	   List<String> tmpList = new ArrayList<String>();
	   for (Map<String, String> tmpMap : catList) {
		   tmpList.add(tmpMap.get("category"));
	   }
	   return tmpList;
   }
   
   public List<CD> getAllProdList() {
	   List<Map<String, String>> prodList = this.getQueryResult(DBAgent.GET_ALL_PRODUCT_QUERY, new ArrayList());
	   List<CD> tmpList = convertMapToCD(prodList);
	   return tmpList;
   }

   private List<CD> convertMapToCD(List<Map<String, String>> prodList) {
	   List<CD> tmpList = new ArrayList<CD>();
	   for (Map<String, String> tmpMap : prodList) {
		   String cdId = (String) tmpMap.get("cdid");
		   String title = (String) tmpMap.get("title");
		   String priceStr = (String) tmpMap.get("price");
		   int price = Integer.parseInt(priceStr);
		   String category = (String) tmpMap.get("category");
		   String imgUrl = (String) tmpMap.get("img_url");
		   CD tmpCD = new CD(cdId, title, price, category, imgUrl);
		   tmpList.add(tmpCD);
	   }
	   return tmpList;
   }
   
   public List<CD> getProdListByCat(String category) {
	   List<String> params = new ArrayList<String>();
	   params.add(category);
	   List<Map<String, String>> prodList = this.getQueryResult(DBAgent.GET_PROD_LIST_BY_CAT_QUERY, params);
	   List<CD> tmpList = convertMapToCD(prodList);
	   return tmpList;
   }
   
   public List<CD> getProdInfo(String cdid) {
	   List<String> params = new ArrayList<String>();
	   params.add(cdid);
	   List<Map<String, String>> cdInfoList = this.getQueryResult(DBAgent.GET_PROD_INFO_QUERY, params);
	   List<CD> tmpList = convertMapToCD(cdInfoList);
	   return tmpList;
   }
}