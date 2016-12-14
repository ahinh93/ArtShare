/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dao.DBHelper;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Jesse
 */
@ManagedBean
@SessionScoped
public class UploadController {

    private String response;
    private Part media;
    private String latestID;
    private String latestYouTube;

    public String getLatestYouTube() {
        return latestYouTube;
    }

    public void setLatestYouTube(String latestYouTube) {
        this.latestYouTube = latestYouTube;
    }

    private String price;
    private String tags;
    private String youtubeURL;

    public String getYoutubeURL() {
        getLatestID();
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }
    @ManagedProperty("#{loginController}")
    private LoginController lc;

    public LoginController getLc() {
        return lc;
    }

    public void setLc(LoginController lc) {
        this.lc = lc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getLatestID() {
        latestID = "";
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "select * from media";
        Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");

        try {

            Statement stmt = DBConn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                latestID = "" + rs.getInt("uid");
                latestYouTube = "" + rs.getString("youtubelink");
            }

            rs.close();
            DBConn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return latestID;
    }

    public void setLatestID(String latestID) {
        this.latestID = latestID;
    }

    public String upload() {

        System.out.println("price: " + price);
        System.out.println("tags: " + tags);
//        if(user == null){
//            return "failedupload.xhtml";
//        }
        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "insert into media (url, price, author, tags)"
                + " values (?, ?, ?, ?)";
        Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");

        try {

            PreparedStatement preparedStmt = DBConn.prepareStatement(query);

            preparedStmt.setDouble(2, Double.parseDouble(price));
            preparedStmt.setString(3, lc.getEmail());
            preparedStmt.setString(4, tags);

            InputStream inputStream = null; // input stream of the upload file

            if (media != null) {
                // obtains input stream of the upload file
                inputStream = media.getInputStream();
            }

            if (inputStream != null) {
//                // fetches input stream of the upload file for the blob column
                preparedStmt.setBlob(1, inputStream);
            }

            // execute the preparedstatement
            preparedStmt.execute();

            DBConn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return "failedupload.xhtml";
        }
        return "successfulupload.xhtml";
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Part getMedia() {
        return media;
    }

    public void setMedia(Part media) {
        this.media = media;
    }

    public String uploadDes() {

        if (media == null && youtubeURL.equalsIgnoreCase("") || media != null && !youtubeURL.equals("")) {
            return "noinputuploaderror.xhtml";
        }
        if (media != null) {
            return upload();
        }
        if (youtubeURL != null) {
            return uploadYouTube();
        }

        return "noinputuploaderror.xhtml";
    }

    public String uploadYouTube() {
//       String temp = "hS5CfP8n_js";

        String url = "https://www.googleapis.com/youtube/v3/videos?id=" + youtubeURL + "&key=AIzaSyAuzP7wRcINqg_RqrowasqEBLiqt3lraKE&part=snippet,contentDetails,statistics,status";
        Client client = ClientBuilder.newClient();
        WebTarget wt = client.target(url);

        String result = wt.request(MediaType.TEXT_PLAIN).get(String.class);
        try {
            result = result.substring(result.indexOf("duration"));
            result = result.substring(0, result.indexOf(','));
            result = result.substring(12, result.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return "notvalidyoutube.xhtml";
        }
        System.out.println("result trim: " + result);
        int min;
        if (result.contains("M")) {
            StringTokenizer st = new StringTokenizer(result, "PT M S");
            min = Integer.parseInt(st.nextToken());
        } else {
            StringTokenizer st = new StringTokenizer(result, "PT S");
            min = 0;
        }

        System.out.println("min: " + min);
        if (min < 2) {
            //write the url to the db
            return youtubeHelper();
        }
        //kick out an error that the file is to long.
        return "notvalidyoutube.xhtml";

    }

    public String youtubeHelper() {

        DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
        String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";
        String query = "insert into media (youtubelink, price, author, tags)"
                + " values (?, ?, ?, ?)";
        Connection DBConn = DBHelper.connect2DB(myDB, "admin1", "password");

        try {

            PreparedStatement preparedStmt = DBConn.prepareStatement(query);

            preparedStmt.setDouble(2, Double.parseDouble(price));
            preparedStmt.setString(3, lc.getEmail());
            preparedStmt.setString(4, tags);
            preparedStmt.setString(1, youtubeURL);

            // execute the preparedstatement
            preparedStmt.execute();

            DBConn.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
            return "faileduploadyoutube.xhtml";
        }
        return "successfuluploadyoutube.xhtml";

    }

}
