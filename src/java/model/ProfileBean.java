/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javax.faces.bean.ManagedBean;

/**
 *
 * @author it353F629
 */
@ManagedBean
public class ProfileBean {
    
    private String email;
    private String password;
    private String fullname;
    private String userlevel;
    private String phoneNo;
    private String subscribed;
    
    public ProfileBean()
    {
        
    }
    
    public ProfileBean(String email, String password, String fullname, String phoneNo,
            String subscribed, String userlevel)
    {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phoneNo = phoneNo;
        this.subscribed = subscribed;
        this.userlevel = userlevel;
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
    public String getSubscribed() {
        return subscribed;
    }

    /**
     * @param subscribed the subscribed to set
     */
    public void setSubscribed(String subscribed) {
        this.subscribed = subscribed;
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
    
}
