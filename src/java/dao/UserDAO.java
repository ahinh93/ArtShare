/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import model.User;

/**
 *
 * @author it353F629
 */
public interface UserDAO {

    public int createUser(User dao);
    
    public int updateUser(User dao);

    public User findUserEmail(String email, String password);
    //public ArrayList findAll();

    //public ArrayList findByName(String aName); // either get one back or several if multiple same name allowed  

    public String retrievePassword(String email);

}
