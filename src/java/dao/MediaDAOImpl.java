/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Media;

/**
 *
 * @author Jesse
 */
public class MediaDAOImpl implements MediaDAO{

    @Override
    public int createMedia(Media mediaId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Media getMediaById(int id) {
        String query = "select * from it353finalproject.media where uid = "+ id;
        
        Media aMedia;
        try{
            aMedia = loadMediaFromDB(query);
        }catch(Exception e){
            aMedia = new Media();
        }
        
        return aMedia;

    }
    
    private static Media loadMediaFromDB(String query){
        DBHelper.loadDriver("org.aoache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");
        
        Media aMedia = null;
        
        try{
            
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int uid,rating;
            String url, author,tags,youtubeURL;
            double price;
            
            while(rs.next()){
                uid = rs.getInt("uid");
                url = rs.getString("url");
                price = rs.getDouble("price");
                author = rs.getString("author");
                rating = rs.getInt("rating");
                tags = rs.getString("tags");
                youtubeURL = rs.getString("youtubelink");
                
                aMedia = new Media(uid,url,price,author,rating,tags,youtubeURL);
                
                
            }
            
            rs.close();
            stmt.close();
            
            
        } catch (Exception e){
            System.err.println("Error: Problem with the SQL select");
            e.printStackTrace();
        }
        
        try{
            DBConn.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return aMedia;
    }
    
}
