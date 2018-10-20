/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hanaa
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
     private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBPufanShenpvs5412";
     private static final String USERNAME = "java";
     private static final String PASSWORD = "java";
    //var
    static Connection ct = null;
    public static Connection getDBConnection(){
        if (ct == null){
      try{
          ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      }
      catch(SQLException sqlException){
          sqlException.printStackTrace();
          System.exit(1);
      }
      return ct;
}
        else{
            return ct;
    }

    }
    
   }
