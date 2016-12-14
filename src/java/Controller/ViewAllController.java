package Controller;

import dao.DBHelper;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Media;
import org.primefaces.event.RateEvent;

/**
 *
 * @author Jesse
 */
@ManagedBean
@SessionScoped
public class ViewAllController {
    private List<Media> images;
    private List<Media> videos;
    private int rating;
    private int uid;
    private String searchTerms;
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd";
    
    public String getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(String searchTerms) {
        this.searchTerms = searchTerms;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
    public List getImages() {
        if(searchTerms == null || searchTerms.equals("")){
            getAllImages();
        }else
        {
            searchFor();
        }
        
        ArrayList<Media> tempRemove = new ArrayList<Media>();
        for(Media media : images){
            if(media.getUrl() == null){
                tempRemove.add(media);
            }
        }
        
        images.removeAll(tempRemove);
        
        
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }
    
    public void getAllImages(){
        
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
       String query = "select * from media";
      Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");
     images = new ArrayList<Media>();
     videos = new ArrayList<Media>();
      try{
      
           Statement stmt = DBConn.createStatement();
           ResultSet rs = stmt.executeQuery(query);
           
           while(rs.next()){
              Media media = new Media(rs.getInt("uid"),rs.getString("url"),rs.getDouble("price"),
                      rs.getString("author"),rs.getInt("rating"),rs.getString("tags"),rs.getString("youtubelink"));
              
              images.add(media);
              videos.add(media);
           }
  
            rs.close();
            DBConn.close();
        } catch (Exception e) {
             System.err.println("Got an exception!");
             System.err.println(e.getMessage());
        } 
    }
   
    public void handleRating(int rating, String uid){
      System.out.println("handle rating called with rating: "+rating+" and uid of "+uid);
      if(rating==0)return;
      DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
      String query = "update media set rating = "+rating+" where uid = "+uid;
      Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");
      
      try{
          Statement stmt = DBConn.createStatement();
          boolean result = stmt.execute(query);
          
          if(!result){
              throw new Exception();
          }
          
      }catch(Exception e){
          System.err.println("Got an exception");
          System.err.println(e.getStackTrace());
      }
     
      
    }
  
    
    
   public void onrate(RateEvent rateEvent){
       int givenRating = ((Integer) rateEvent.getRating()).intValue();
       String uid = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uidKey");
       int oldRating = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("oldRatingKey"));
       
       double newRating = ((double)givenRating+(double)oldRating)/2;
       
       double test = Math.round(newRating);
       int test1 = (int) test;
       
       handleRating(test1,uid);
   
   
   }
   
   public void searchFor(){
       //searchTerms is what we need to look for in the db.
       
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
       String query = "select * from media where tags like '%"+searchTerms+"%'";
      Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");
     images = new ArrayList<Media>();
     videos = new ArrayList<Media>();
      try{
      
           Statement stmt = DBConn.createStatement();
           ResultSet rs = stmt.executeQuery(query);
           
           while(rs.next()){
              Media media = new Media(rs.getInt("uid"),rs.getString("url"),rs.getDouble("price"),
                      rs.getString("author"),rs.getInt("rating"),rs.getString("tags"),rs.getString("youtubelink"));
              
              images.add(media);
              videos.add(media);
           }
  
            rs.close();
            DBConn.close();
        } catch (Exception e) {
             System.err.println("Got an exception!");
             System.err.println(e.getMessage());
        } 
       searchTerms = "";
   }

  
    public List<Media> getVideos() {
        if(searchTerms.equals("")){
            getAllImages();
        }else
        {
            searchFor();
        }
        
        ArrayList<Media> tempRemove = new ArrayList<Media>();
        for(Media media : videos){
            if(media.getUrl() != null){
                tempRemove.add(media);
            }
        }
        
        videos.removeAll(tempRemove);
        
        return videos;
    }

   
    public void setVideos(List<Media> videos) {
        this.videos = videos;
    }
    
    public void makeWinner(){
       DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
       
       String mediaID = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("uidKey");
       
       
       String query = "insert into weeklywinners values ('"+now()+"',?,false)";
      Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");
 
    
    try{
        PreparedStatement preparedStmt = DBConn.prepareStatement(query);
            preparedStmt.setInt(1,Integer.parseInt(mediaID));
            // execute the preparedstatement
            preparedStmt.execute();
            DBConn.close();            
        }catch(Exception e){
            System.err.println("Error: Problem with SQL.");
            e.printStackTrace();
        }
    }
    
    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    
    
    
    }
    
    

