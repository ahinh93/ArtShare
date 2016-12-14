/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.User;

/**
 *
 * @author it353F629
 */

@ManagedBean
@SessionScoped
public class LoginController {
    private User profile;
    private String email;
    private String password;
    private String lostEmail;

    public String getLostEmail() {
        return lostEmail;
    }

    public void setLostEmail(String lostEmail) {
        this.lostEmail = lostEmail;
    }
    private String lostPassword;

    public String getLostPassword() {
        return lostPassword;
    }

    public void setLostPassword(String lostPassword) {
        this.lostPassword = lostPassword;
    }
    private String response;
    private int row = 0;

    public LoginController()
    {
        profile = new User();
    }

    public User getUser() {
        return getProfile();
    }

    public void setProfile(User profile) {
        this.profile = profile;
    }

    public void killProfile(){                
        this.profile = null;
        this.email = null;
        row = 0;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public String findUserEmail() {
        UserDAO dao = new UserDAOImpl(); 
        User user = dao.findUserEmail(email, password);
        setProfile(user);
        
        if(getProfile() != null) {
            System.out.println("admin?: "+ user.getUserlevel());
            if(user.getUserlevel().toLowerCase().equals("admin"))
                return "adminDashboard.xhtml";
            else
                return "dashboard.xhtml";
        } else {
            response = "FAILED LOG IN.";          
            return "";
        }
    }
    public String correctDashboard() {
        
        //System.out.println("admin?: "+ this.profile.getUserlevel());
        if(this.profile == null)
        {
            return "error.xhtml";
        }
        if(this.profile.getUserlevel().toLowerCase().equals("admin"))
            return "adminDashboard.xhtml";
        else
            return "dashboard.xhtml";
        
    }
    public User getProfile() {
        return profile;
    }
    
    public String retrievePassword() {
        UserDAO dao = new UserDAOImpl();       
        lostPassword=dao.retrievePassword(lostEmail);
        
        System.out.println("lostpass: "+lostPassword);
        return lostPassword;
    }
}
