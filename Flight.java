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
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Flight {
    private String name;
    private int seat;
    private static PreparedStatement selectAllFlights;
    private static PreparedStatement insertNewFlight;
   private static PreparedStatement f_dropFlight;
    public void setFlightName(String name){
        this.name = name;
    }
    public String getFlightName(){
        return name;
    }
    public void setFlightSeat(int seat){
        this.seat = seat;
    }
    public int getFlightSeat(){
        return seat;
    }
    public static ArrayList<Flight> getFlightList() {
        Connection c = DBConnection.getDBConnection();
        ArrayList<Flight> results = null;
        ResultSet rs = null;
        try{
        selectAllFlights = c.prepareStatement("SELECT * FROM FLIGHTS");
        rs = selectAllFlights.executeQuery();
        results = new ArrayList<Flight>();
        while (rs.next()){
            Flight f = new Flight();
            f.setFlightName(rs.getString("NAMES"));
            f.setFlightSeat(rs.getInt("SEATS"));
            results.add(f);
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return results;
    }
    public static int addFlight(String name, int seat){
        Connection c2 = DBConnection.getDBConnection();
        int result = 0;
        try{
            insertNewFlight = c2.prepareStatement("INSERT INTO FLIGHTS ( NAMES, SEATS )" + "VALUES(?,?)") ;
            insertNewFlight.setString(1,name);
            insertNewFlight.setInt(2,seat);
        result = insertNewFlight.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
        return result;
    }
   
   
    public static void flight_dropFlight(String name){
         Connection c4 = DBConnection.getDBConnection();
         try{
             f_dropFlight = c4.prepareStatement("delete from FLIGHTS where NAMES = ?");
             f_dropFlight.setString(1, name);
             f_dropFlight.executeUpdate();
             
         }
         catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
   
}