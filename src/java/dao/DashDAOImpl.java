package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import model.*;

/**
 * @author Randall DeVitto
 */
public class DashDAOImpl implements DashDAO {
    
    private final String driverName = "org.apache.derby.jdbc.ClientDriver";
    private final String connStr = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";

    public DashDAOImpl() { 
    }   
    
    @Override
    public ArrayList<WeeklyWinners> getAllWinners() {
        String query = "SELECT * FROM weeklywinners ORDER BY date DESC";
        ArrayList<WeeklyWinners> winners = new ArrayList();
        Connection DBConn = null;
        WeeklyWinners winner = null;
        
        try {
            DBHelper.loadDriver(driverName);
            DBConn = DBHelper.connect2DB(connStr, "admin1", "password");
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Date date; int winnerid; boolean paid;
            while (rs.next()) {
                date = rs.getDate("win_date");
                winnerid = rs.getInt("winner_media_id");
                paid = rs.getBoolean("beenpaid");
                
                winner = new WeeklyWinners(date,winnerid,paid);
                //getAuthorsForWinners(winner, winnerid);
                winners.add(winner);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }
        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return winners;
    }
    
    @Override
    public int updateSponsers(String sponser) {
        Connection DBConn = null;
        int status = 0;
        
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
            DBConn = DBHelper.connect2DB(myDB, "admin1", "password");

            //String sponsersList = getSponsers();   
            //sponsersList = sponsersList.concat(" / " + sponser);
            //String query = "UPDATE ADMIN1.SPONSERS SET URL_LIST = '" + sponsersList + "'";
            
            String query = "INSERT INTO SPONSERS VALUES'" + sponser + "'";
            
            Statement stmt = DBConn.createStatement();
            status = stmt.executeUpdate(query);            
            
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }

        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        
        return status;
    }

    @Override
    public String getSponsers() {
        Connection DBConn = null;
        String sponserList = "";
        
        try {
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
            DBConn = DBHelper.connect2DB(myDB, "admin1", "password");

            //String query = "SELECT URL_LIST from ADMIN1.SPONSERS";
            String query = "select * from SPONSERS";
            
            
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);  
            
            while(rs.next())
            {
                sponserList += rs.getString("url_list") + " ";
                
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.err.println("ERROR: Problems with SQL select");
            e.printStackTrace();
        }

        try {
            DBConn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //System.out.println("sponsors: " +sponserList);
        return sponserList;
    }
}
