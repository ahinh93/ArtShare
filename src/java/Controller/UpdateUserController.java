/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dao.UserDAO;
import dao.UserDAOImpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import model.User;

/**
 *
 * @author it353f629
 */
@ManagedBean
@SessionScoped
public class UpdateUserController {
    private String email;
    private String password;
    private String fullname;
    private String userlevel;
    private String phoneNo;
    private boolean subscribed;
    private String updateStatus = "";
    private User bean = new User();
    @ManagedProperty("#{loginController}")
    private LoginController lc;

    public UpdateUserController()
    {
        bean = new User();
    }
    public UpdateUserController(String email, String password, String fullname, String userlevel, String phoneNo, boolean subscribed)
    {
        UserDAO dao = new UserDAOImpl();
        bean = dao.findUserEmail(lc.getEmail(), lc.getPassword());
		
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.userlevel = userlevel;
        this.phoneNo = phoneNo;
        this.subscribed = subscribed;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNo
     */
    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * @param phoneNo the phoneNo to set
     */
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    /**
     * @return the subscribed
     */
    public boolean getSubscribed() {
        return subscribed;
    }

    /**
     * @param subscribed the subscribed to set
     */
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    /**
     * @return the updateStatus
     */
    public String getUpdateStatus() {
        return updateStatus;
    }

    /**
     * @param updateStatus the updateStatus to set
     */
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }
    
    public void updateUser() {
        UserDAO dao = new UserDAOImpl();        
        //bean = lc.getProfile();
        //setBean(new User(lc.getEmail(), password, fullname, phoneNo, subscribed, userlevel)); 
        bean.setEmail(lc.getEmail());
        int status = dao.updateUser(bean); 
        if (status != 0) {
            setUpdateStatus("Profile updated successfully");
        } else {
            setUpdateStatus("Profile update failed");
        }
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the userlevel
     */
    public String getUserlevel() {
        return userlevel;
    }

    /**
     * @param userlevel the userlevel to set
     */
    public void setUserlevel(String userlevel) {
        this.userlevel = userlevel;
    }

    /**
     * @return the lc
     */
    public LoginController getLc() {
        return lc;
    }

    /**
     * @param lc the lc to set
     */
    public void setLc(LoginController lc) {
        this.lc = lc;
    }
	
	public String retrieveUser()
    {
        System.out.print("@@@@@@@@@@@@@@@@@@@@");
        UserDAO dao = new UserDAOImpl();    // Creating a new object each time.
        bean = dao.findUserEmail(lc.getEmail(), lc.getPassword()); // Doing anything with the object after this?
        if (bean != null) 
            return "update.xhtml"; // navigate to "update2.xhtml"
        else
            return "error.xhtml"; 
    }
	
	    /**
     * @return the bean
     */
    public User getBean() {
        return bean;
    }

    /**
     * @param bean the bean to set
     */
    public void setBean(User bean) {
        this.bean = bean;
    }
    
}
