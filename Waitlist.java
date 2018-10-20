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
import java.util.Calendar;
import java.sql.Date;
import java.sql.ResultSet;
public class Waitlist {
     private static PreparedStatement getWaitList;
     private static PreparedStatement statusWaitlistDay;
      private static PreparedStatement removeWaitlist;
       private static PreparedStatement addtoBooking;
       private static PreparedStatement customerStatusWaitlist;
       private static PreparedStatement w_dropFlight_name;
    // declare a timestamp field.

// use the timestamp in an insert to the waitlist.
             public static int getWaitList(String flight, Date date, String name) {
            Connection c3 = DBConnection.getDBConnection();
            int count = 0;
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
            try{
            getWaitList = c3.prepareStatement("insert into WAITLIST (flight, day, customerName, timeStamp) values (?, ?, ?,?)");
            getWaitList.setString(1, flight);
            getWaitList.setDate(2, date);
            getWaitList.setString(3, name);
            getWaitList.setTimestamp(4, currentTimestamp);
            count = getWaitList.executeUpdate();
            }
            
            catch(SQLException sqlException){
            sqlException.printStackTrace();
            }

             return count;
}
      public static String addtoBooking(String flight,Date flightDate ) {
          Connection c6 = DBConnection.getDBConnection();
          ResultSet rs = null;
          String addP = null;
          try{
              addtoBooking = c6.prepareStatement("select CUSTOMERNAME from WAITLIST where FLIGHT = ? and DAY = ? order by TIMESTAMP");
              addtoBooking.setString(1,flight);
              addtoBooking.setDate(2, flightDate);
              rs = addtoBooking.executeQuery();
              if (rs.next()) {
                Booking.addBooking(flightDate,rs.getString("CUSTOMERNAME"), flight);
                addP = rs.getString("CUSTOMERNAME");
                removeWaitlist(flightDate,rs.getString("CUSTOMERNAME"));
                
            }
            else{
            }
          }
               catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
          
           return addP;           
      }

             public static void removeWaitlist(Date flightDate, String passengerName){
         Connection c5 = DBConnection.getDBConnection();
         try{
             removeWaitlist = c5.prepareStatement("DELETE FROM WAITLIST where DAY = ? and CUSTOMERNAME = ? ");
             removeWaitlist.setDate(1, flightDate);
             removeWaitlist.setString(2, passengerName);
             removeWaitlist.executeUpdate();
         }
         catch(SQLException sqlException){
             sqlException.printStackTrace();
         }
    }
             public static ArrayList<String> statusWaitlistDay(Date flightDate){
                 Connection c4 = DBConnection.getDBConnection();
                 ArrayList<String> results = null;
                ResultSet rs = null;
        try{
        statusWaitlistDay = c4.prepareStatement("select CUSTOMERNAME,FLIGHT from WAITLIST where DAY = ? order by TIMESTAMP");
        statusWaitlistDay.setDate(1,flightDate);
        rs = statusWaitlistDay.executeQuery();
        results = new ArrayList<String>();
        while (rs.next()){
            results.add(new String(rs.getString("CUSTOMERNAME")+ " " + "on" + " " + rs.getString("FLIGHT")));
           
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return results;
             }
   public static ArrayList<String> customerStatusWaitlist(String passengerName){
        Connection c8 = DBConnection.getDBConnection();
        ArrayList<String> results = null;
        ResultSet rs = null;
        try{
        customerStatusWaitlist = c8.prepareStatement("select FLIGHT, DAY from WAITLIST where CUSTOMERNAME = ?");
        customerStatusWaitlist.setString(1,passengerName);
        rs = customerStatusWaitlist.executeQuery();
        results = new ArrayList<String>();
        while (rs.next()){
            //System.out.print(rs.getString("PASSENGER"));
            results.add(new String(rs.getString("FLIGHT")+ " " + "on" + " " + rs.getString("DAY")));
        }
    }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return results;
    } 

     public static ArrayList<String> waitlist_dropFlight_name(String name){
        Connection c1 = DBConnection.getDBConnection();
        ResultSet rs = null;
        ArrayList<String> results = null;
        try{
        w_dropFlight_name = c1.prepareStatement("select CUSTOMERNAME, DAY from WAITLIST where FLIGHT = ?");
        w_dropFlight_name.setString(1, name);
        rs = w_dropFlight_name.executeQuery();
        results = new ArrayList<String>();
         while (rs.next()){
            //System.out.print(rs.getString("PASSENGER"));
            results.add(new String(rs.getString("CUSTOMERNAME")+ " " + "gets cancelled from waitlist " + " " + rs.getString("DAY")));
            removeWaitlist(rs.getDate("DAY"),rs.getString("CUSTOMERNAME"));
        }
    }
        catch(SQLException sqlException){
             sqlException.printStackTrace();
         }
        return results;
                }
     
}





