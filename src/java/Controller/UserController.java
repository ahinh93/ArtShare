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
public class UserController {

    private String response;
    private User bean;

    public UserController() {
        bean = new User();
    }

    public User getUser() {
        return getBean();
    }

    public void setUser(User bean) {
        this.setBean(bean);
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the bean
     */
    public String createUser() {
        UserDAO dao = new UserDAOImpl();    // Creating a new object each time.
        int rowCount = dao.createUser(getBean()); // Doing anything with the object after this?
        if (rowCount == 1) {
            return "logIn.xhtml"; // navigate to "response.xhtml"
        } else {
            return "error.xhtml";
        }
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
