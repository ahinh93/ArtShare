package Controller;

import dao.DashDAO;
import dao.DashDAOImpl;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.*;

/**
 * @author Randall DeVitto
 */
@ManagedBean
@SessionScoped
public class AdminDashController {
    private User userBean; 
    private final DashDAO dao = new DashDAOImpl();    
    private boolean loggedIn; 
    private String searchToken;
    private String response; 
    
    public String login() {
        this.setLoggedIn(true);
        return "dashboard.xhtml";
    }

    public String logout() {
        this.setLoggedIn(false);
        return "index.xhtml";
    }

    public void payRoyalties(Media media, double payment) {
        
    }
    
    //--------------------------------------------------

    public User getUserBean() {
        return userBean;
    }

    public void setUserBean(User userBean) {
        this.userBean = userBean;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getSearchToken() {
        return searchToken;
    }

    public void setSearchToken(String searchToken) {
        this.searchToken = searchToken;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
