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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class Dates {
    private Date date;
    private static PreparedStatement selectAllDate;
     private static PreparedStatement insertNewDate;
    
    
    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return date;
    }
    public static int addDate(Date date){
        Connection c2 = DBConnection.getDBConnection();
        int result = 0;
        try{
            insertNewDate = c2.prepareStatement("INSERT INTO DATE ( DATE )" + "VALUES(?)") ;
            insertNewDate.setDate(1,date);
        result = insertNewDate.executeUpdate();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
           
        }
        return result;
    
    }
    public static ArrayList<Dates> getDateList() {
        Connection c = DBConnection.getDBConnection();
        ArrayList<Dates> results = null;
        ResultSet rs = null;
        try{
        selectAllDate = c.prepareStatement("SELECT * FROM DATE");
        rs = selectAllDate.executeQuery();
        results = new ArrayList<Dates>();
        while (rs.next()){
             Dates d = new Dates();
            d.setDate(rs.getDate("DATE"));
            results.add(d);
        }
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
                }
        return results;
}
}
