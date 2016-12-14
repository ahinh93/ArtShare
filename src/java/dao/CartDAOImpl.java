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
import java.util.ArrayList;
import model.Cart;

/**
 *
 * @author Jesse
 */
public class CartDAOImpl implements CartDAO{

    @Override
    public int createCart() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Cart> getCartByEmail(String uid) {
        String query = "select * from cart where email = '"+ uid+"'";
        ArrayList<Cart> myCart = new ArrayList<Cart>();
        DBHelper.loadDriver("org.aoache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");
        
        Cart item = null;
        
        try{
            
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String email;
            int mediaID;
            double price;           
            
            while(rs.next()){
                //uid = rs.getInt("cartUID");
                email = rs.getString("email");
                mediaID = rs.getInt("media_ID");
                
                item = new Cart(email,mediaID);
                getItemPrice(item, mediaID);
                myCart.add(item);
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
        return myCart;

    }
    private void getItemPrice(Cart item, int mediaID)
    {
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select * from media where uid="+mediaID;
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
       
        
        try{
            Statement stmt1 = DBConn.createStatement();
            ResultSet rs1 = stmt1.executeQuery(query);
            double price;
            
            while(rs1.next()){
                price = rs1.getDouble("price");
                 item.setPrice(price);
            }
            
            rs1.close();
            stmt1.close();
            
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
       
    }
}
