 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Jesse
 */
public class User {
    private String email;
    private String password;
    private String fullName;
    private boolean subscribed;
    private String phoneNumber;
    private String userlevel;
    
    
    public User()
    {
        
    }
    
    public User(String email, String password, String fullname, String phoneNo,
            boolean subscribed, String userlevel)
    {
        this.email = email;
        this.password = password;
        this.fullName = fullname;
        this.phoneNumber = phoneNo;
        this.subscribed = subscribed;
        this.userlevel = userlevel;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
