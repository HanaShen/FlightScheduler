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
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
public class Booking {
    private static PreparedStatement insertNewBooking;
     private static PreparedStatement selectFlightDay;
     private static PreparedStatement getFlightSeats;
     private static PreparedStatement removeBooking;
     private static PreparedStatement checkBooking;
     private static PreparedStatement getFlight;
    private static PreparedStatement customerStatusBooking;
    public static int addBooking(Date flightDate, String passengerName,String flightName){
        Connection c = DBConnection.getDBConnection();
        int result = 0;
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
        try{
            insertNewBooking = c.prepareStatement("INSERT INTO BOOKING (DATE,  PASSENGER, FLIGHT,timeStamp )"  + "VALUES(?,?,?,?)") ;
            insertNewBooking.setDate(1,flightDate);
            insertNewBooking.setString(2, passengerName);
            insertNewBooking.setString(3,flightName);
            insertNewBooking.setTimestamp(4, currentTimestamp);
        result = insertNewBooking.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
        return result;
       
    }
     public static ArrayList<String> customerStatusBooking(String passengerName){
        Connection c8 = DBConnection.getDBConnection();
        ArrayList<String> results = null;
        ResultSet rs = null;
        try{
        customerStatusBooking = c8.prepareStatement("select FLIGHT, DATE from BOOKING where PASSENGER = ?");
        customerStatusBooking.setString(1,passengerName);
        rs = customerStatusBooking.executeQuery();
        results = new ArrayList<String>();
        while (rs.next()){
            //System.out.print(rs.getString("PASSENGER"));
            results.add(new String(rs.getString("FLIGHT")+ " " + "on" + " " + rs.getString("DATE")));
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return results;
    } 
     

     public static String getFlight(Date flightDate, String passengerName){
         Connection c6 = DBConnection.getDBConnection();
         ResultSet rs = null;
         String flight = null;
         try{
            getFlight = c6.prepareStatement("SELECT FLIGHT from BOOKING where DATE = ? and PASSENGER = ? ") ;
            getFlight.setDate(1, flightDate);
            getFlight.setString(2, passengerName);
             rs = getFlight.executeQuery();
             if (rs.next()) {
     flight = rs.getString("FLIGHT");}
     }
         catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
         return flight;
     }
    public static boolean checkBooking(Date flightDate, String passengerName){
        Connection c5 = DBConnection.getDBConnection();
        ResultSet rs = null;
        boolean result = false;
        try{
            checkBooking = c5.prepareStatement("SELECT FLIGHT from BOOKING where DATE = ? and PASSENGER = ? ") ;
            checkBooking.setDate(1, flightDate);
            checkBooking.setString(2, passengerName);
             rs = checkBooking.executeQuery();
            if (!rs.next()) {                            
                result = false; 
            }
            else{
                result = true;
            }
        }
         catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
        return result;
    //System.out.println("No records found");

    }
    public static void removeBooking(Date flightDate, String passengerName){
         Connection c4 = DBConnection.getDBConnection();
         try{
             removeBooking = c4.prepareStatement("DELETE FROM BOOKING where DATE = ? and PASSENGER = ? ");
             removeBooking.setDate(1, flightDate);
             removeBooking.setString(2, passengerName);
            removeBooking.executeUpdate();
         }
         catch(SQLException sqlException){
             sqlException.printStackTrace();
         }
    }
    public static ArrayList<String> statusFlightDay(Date flightDate, String flightName){
        Connection c2 = DBConnection.getDBConnection();
        ArrayList<String> results = null;
        ResultSet rs = null;
        try{
        selectFlightDay = c2.prepareStatement("select PASSENGER from BOOKING where FLIGHT = ? and DATE = ?");
        selectFlightDay.setString(1, flightName);
        selectFlightDay.setDate(2,flightDate);
        rs = selectFlightDay.executeQuery();
        results = new ArrayList<String>();
        while (rs.next()){
            //System.out.print(rs.getString("PASSENGER"));
            results.add(new String(rs.getString("PASSENGER")));
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return results;
    }

    public static int getFlightSeats(String flight, Date date){
         Connection c3 = DBConnection.getDBConnection();
         int seatsBooked = 0;
         ResultSet resultSet = null;
         try{
            getFlightSeats = c3.prepareStatement("select count(FLIGHT) AS total from BOOKING where FLIGHT = ? and DATE= ?"); 
            getFlightSeats.setString(1, flight); 
            getFlightSeats.setDate(2, date); 
            resultSet = getFlightSeats.executeQuery(); 
             resultSet.next();
             seatsBooked = resultSet.getInt("total");
         }
         catch(SQLException sqlException){
            sqlException.printStackTrace();
}
         return seatsBooked;
}
}
    
