/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dao.DBHelper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jesse
 */
@WebServlet(urlPatterns = {"/image"})
public class ImageServlet extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
            byte [] imageBytes = null;
            System.out.println(request.getParameter("uid"));
            DBHelper.loadDriver("org.apache.derby.jdbc.ClientDriver");
            String myDB = "jdbc:derby://gfish2.it.ilstu.edu:1527/jalltop_Fa2016_it353finalproject";

            Connection con = DBHelper.connect2DB(myDB, "admin1", "password");
            
            PreparedStatement ps = con.prepareStatement("Select url from media where uid="+request.getParameter("uid"));
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                imageBytes = rs.getBytes("url");
            }
            
            con.close();
            response.getOutputStream().write(imageBytes);
            response.getOutputStream().close();
            
        }catch(Exception e){
            response.getWriter().write(e.getMessage());
            response.getWriter().close();
        }
    }

    
}
