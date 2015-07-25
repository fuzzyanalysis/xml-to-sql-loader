/**
 * 
 */
package com.donkeyenough.loader;

// select dbms_xmlgen.getxml('select * from cars where make = ''Abarth''') xml from dual

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * @author Shan
 *
 */
public class DBManagerOracle {
	
	private Connection connection;
	private String DB_CONNECTION_STRING = "jdbc:oracle:thin:@chen-pc:1521:xe";
	private String DRIVER_CLASS_NAME = "oracle.jdbc.driver.OracleDriver";
	private String DB_USER_NAME = "*****";
	private String DB_PASSWORD = "*****";
	private String resultString = null;
	private boolean connected = false;
	
	public boolean openConnection(){
			
		try {
				Class.forName(DRIVER_CLASS_NAME);
		}
		catch (ClassNotFoundException cnfe) {
			resultString = cnfe.getMessage();
			cnfe.printStackTrace();
		}
		
		try {			
			//DriverManager.registerDriver(new Driver(DRIVER_CLASS_NAME));
			//System.out.println("Connecting to the database...");
			connection = DriverManager.getConnection(DB_CONNECTION_STRING,  DB_USER_NAME, DB_PASSWORD);
	        Statement statement = connection.createStatement();  
	        ResultSet resultset = statement.executeQuery("select 'Connected' from dual");  
	        resultset.next();  
	        resultString = resultset.getString(1);  
	        //System.out.println(resultString);  
	        statement.close();
	        if(resultString.equals("Connected")) {
		        connected = true;	
	        }
		}
		catch (SQLException sqex) {
			resultString = sqex.getMessage();
			sqex.printStackTrace();		
		}
		catch(Exception ex){
			System.out.println("Error in DBManager.Connection(): The exception raised is:" + ex);  
		}
		return connected;
	}
	
	public void closeConnection()
	{
		try {
			if(connection != null && !connection.isClosed()){
				connection.close();	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String query){
		
		return executeQuery(connection, query);
	}
	
	public ResultSet executeQuery(Connection connection, String query)
	{		
        Statement statement;
        ResultSet resultSet = null;
		try {
				connection = DriverManager.getConnection(DB_CONNECTION_STRING,  DB_USER_NAME, DB_PASSWORD);
				statement = connection.createStatement();
		        resultSet = statement.executeQuery(query);
		        statement.close();  
		        connection.close();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
        return resultSet;
	}
		
}
