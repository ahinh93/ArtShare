/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dao.CartDAO;
import dao.CartDAOImpl;
import dao.DBHelper;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Cart;
import model.Media;
/**
 *
 * @author it353f608
 */ 
@ManagedBean(name = "CartController")
@SessionScoped
public class CartController {

    @ManagedProperty("#{loginController}")
    private LoginController lc;
    private ArrayList<Cart> myCart;    
    CartDAOImpl myDAO = new CartDAOImpl();
    private String message;
    
    
    /**
     * Creates a new instance of ShoppingController
     */
    public CartController() {
        
        myCart = new ArrayList<Cart>();
    }
    
    public LoginController getLc() {
        return lc;
    }
    
    public void addToCart()
    {
        String mediaID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uidKey");
        //System.out.println("Adding media: " + mediaID +" to the cart table");
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "insert into cart (email,media_id) values (?,?)";
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
        try{
            PreparedStatement preparedStmt = DBConn.prepareStatement(query);
            preparedStmt.setString (1, lc.getEmail());
            preparedStmt.setInt(2,Integer.parseInt(mediaID));
            // execute the preparedstatement
            preparedStmt.execute();
            DBConn.close();            
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
    }    
    /**
     * TODO:
     * 1.insert all entries with that email into a string array and double array
     * 2.pass arrays into email controller --> emailReceipt(media_id,price)
     * 3.delete entries from database
     * @return 
     */
    public String checkout()
    {
        System.out.println("@@@@@@@@@@"+lc.getEmail()+" just hit the checkout button");
        //insert media id's and prices to arrays
        ArrayList<Integer> media_ids = new ArrayList<Integer>();
        ArrayList<Double> prices = new ArrayList<Double>();
        HashMap<String,ArrayList<Media>> myMap = new HashMap<String,ArrayList<Media>>();
        
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select * from cart where email = '" + lc.getEmail()+"'";
        System.out.println("query: " + query);
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
        try{
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int tempID;
            double tempPrice;
            String tempAuthor;
            while(rs.next()){
                tempID = rs.getInt("media_id");
                tempPrice = findPriceById(tempID);
                tempAuthor = findAuthorById(tempID);
                //save values to arraylist
                media_ids.add(tempID);
                prices.add(tempPrice);
                
                Media temp = new Media(tempID,tempPrice);
                if(myMap.containsKey(tempAuthor))
                {
                    myMap.get(tempAuthor).add(temp);
                }
                else
                {
                    myMap.put(tempAuthor, new ArrayList<Media>());
                    myMap.get(tempAuthor).add(temp);
                }
            }
            rs.close();
            stmt.close();
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
        
        //email user receipt using arraylist info
        prepareReceipt(media_ids,prices);
        
        //email author for royalty
        prepareRoyalty(myMap);
        
        //remove from database
        return deleteFromCartTable();
    }
    
    public String findAuthorById(int mediaID)
    {
        String tempAuthor;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select author from media where uid = " + mediaID;
        System.out.println("query: " + query);
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
        try{
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                tempAuthor = rs.getString("author");
                return tempAuthor;
            }
            rs.close();
            stmt.close();
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
        return "null";
    }
    public double findPriceById(int mediaID)
    {
        double itemprice;
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select price from media where uid = " + mediaID;
        System.out.println("query: " + query);
        Connection DBConn = DBHelper.connect2DB(myDB,"admin1","password");
        
        try{
            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                itemprice = rs.getDouble("price");
                return itemprice;
            }
            rs.close();
            stmt.close();
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
        
        return -1;
    }
    public void prepareRoyalty(HashMap<String,ArrayList<Media>> myMap)
    {
        for (HashMap.Entry<String, ArrayList<Media>> entry : myMap.entrySet()) {
            String author = entry.getKey();
            ArrayList<Media> items = entry.getValue();
            EmailController ec = new EmailController();
            ec.emailRoyalty(author, items);
        }
    }
    public void prepareReceipt(ArrayList<Integer> ids, ArrayList<Double> prices)
    {        
        System.out.println("id array size: " + ids.size() + " prices array size: " + prices.size());
        
        Iterator it = ids.iterator();
        Iterator it2 = prices.iterator();
        while(it.hasNext())
        {
            System.out.println("id: " + it.next() + " price: " + it2.next());
        }
        
        
        //create controller and call its email method
        EmailController ec = new EmailController();
        ec.emailReceipt(lc.getEmail(), ids, prices);
    }
    public String deleteFromCartTable()
    {
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        //DELETE FROM CART WHERE email = 'ahinh@ilstu.edu' and media_id = 202;
        String query = "DELETE FROM CART WHERE EMAIL = ?";
        System.out.println("query: " + query);
        Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");

        try{      
            PreparedStatement preparedStmt = DBConn.prepareStatement(query);
            preparedStmt.setString (1, lc.getEmail());
            // execute the preparedstatement
            preparedStmt.execute();
            DBConn.close();
            } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return "failedpurchase.xhtml";
        }
        return "successfulcheckout.xhtml";
    }
    public void setLc(LoginController lc) {
        this.lc = lc;
    }
    /**
     * @return the myCart
     */
    public ArrayList<Cart> getMyCart() {
        CartDAOImpl daoImp = new CartDAOImpl();
        
        this.myCart = daoImp.getCartByEmail(lc.getEmail());
        return myCart;
    }

    /**
     * @param myCart the myCart to set
     */
    public void setMyCart(ArrayList<Cart> myCart) {
        this.myCart = myCart;
    }     

    /**
     * @return the message
     */
    public String getMessage() {
        message = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uidKey");
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    public void showMessageGrowl() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Success!!",  "You've added this image to your cart!"));
    }
    public void showVideoGrowl() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        context.addMessage(null, new FacesMessage("Success!!",  "You've added this video to your cart!"));
    }
    public void showWinnerGrowl() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        context.addMessage(null, new FacesMessage("Success!!",  "This media is now the weekly winner!"));
    }
    
}
