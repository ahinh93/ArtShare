/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Media;
import model.WeeklyWinners;

/**
 *
 * @author Jesse
 */
public class WeeklyWinnersDAOImpl implements WeeklyWinnersDAO{

    @Override
    public ArrayList<WeeklyWinners> findAllWinners() {
    
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select * from weeklywinners";
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
        ArrayList<WeeklyWinners> allWinners = new ArrayList<WeeklyWinners>();
        WeeklyWinners winner = null;
        
        try{
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Date date;
            int winnerid;
            boolean paid;
            
            while(rs.next()){
                date = rs.getDate("win_date");
                winnerid = rs.getInt("winner_media_id");
                paid = rs.getBoolean("beenpaid");
                
                winner = new WeeklyWinners(date,winnerid,paid);
                getAuthorsForWinners(winner, winnerid);
                allWinners.add(winner);
            }
            rs.close();
            stmt.close();
            
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
        return allWinners;
    }

    @Override
    public void payout(WeeklyWinners winner) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void getAuthorsForWinners(WeeklyWinners winner, int id){
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select * from media where uid="+id;
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
       
        
        try{
            Statement stmt1 = DBConn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(query);
            String author;
            
            while(rs1.next()){
                author = rs1.getString("author");
                 winner.setAuthor(author);
            }
            
            rs1.close();
            stmt1.close();
            
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
       
    }
    
    @Override
    public void payoutForWinner(Date date){
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "update weeklywinners set beenpaid=true WHERE win_date=('"+date.toString()+"')";
        
        int row = -453;
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
       
        try{
            Statement stmt1 = DBConn.createStatement();
            row = stmt1.executeUpdate(query);
            stmt1.close();  
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
    }
}
