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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
public class Passengers {
    private String name;
     private static PreparedStatement insertNewPassenger;
     private static PreparedStatement selectAllPassenger;
      private static PreparedStatement p_dropFlight;
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public static ArrayList<Passengers> getPassengerList() {
        Connection c = DBConnection.getDBConnection();
        //ArrayList<Passengers> results = null;
        ArrayList<Passengers> namee = null;
        ResultSet rs = null;
        try{
        selectAllPassenger = c.prepareStatement("SELECT * FROM PASSENGERS");
        rs = selectAllPassenger.executeQuery();
        //results = new ArrayList<Passengers>();
        namee =  new ArrayList<Passengers>();
        while (rs.next()){
            Passengers p = new Passengers();
            p.setName(rs.getString("NAME"));
            namee.add(p);
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return namee;
    }
    public static int addPassenger(String name){
        Connection c2 = DBConnection.getDBConnection();
        int result = 0;
        try{
            insertNewPassenger = c2.prepareStatement("INSERT INTO PASSENGERS(  NAME )" + "VALUES(?)") ;
            insertNewPassenger.setString(1,name);
        result = insertNewPassenger.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
        return result;
       

        
    }
     public static ArrayList<String>  passenger_dropFlight(String name){
        Connection c3 = DBConnection.getDBConnection();
        ResultSet rs = null;
        ArrayList<String> results = null;
        try{
        p_dropFlight = c3.prepareStatement("select PASSENGER, DATE from BOOKING where FLIGHT = ? order by TIMESTAMP");
        p_dropFlight.setString(1, name);
        rs = p_dropFlight.executeQuery();
        results = new ArrayList<String>();
        while (rs.next()){
            //System.out.print(rs.getString("PASSENGER"));
            results.add(new String(rs.getString("PASSENGER") + ","  + rs.getString("DATE")));
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return results;
        }
   
}
