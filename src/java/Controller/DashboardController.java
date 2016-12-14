package Controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.*;
import dao.*;
import java.util.ArrayList;
import javax.faces.bean.ManagedProperty;

/**
 * @author Randall DeVitto
 */
@ManagedBean
@SessionScoped
public class DashboardController {
    //Class Variables
    //private final DashDAO dao = new DashDAOImpl();     
    @ManagedProperty("#{loginController}")
    private LoginController lc;
    
    //Page Variables
    private ArrayList<WeeklyWinners> pastWinners;
    private ArrayList<WeeklyWinners> currentWinners;
    private String inputToken;
    private String response;
    private String userName;
    private String sponser = "";

    
    public DashboardController() {
    }

    public String logout() {
        lc.killProfile();
        return "logIn.xhtml";
    }
    
    public void getWinners() {
        /*
        ArrayList<WeeklyWinners> list = dao.getAllWinners();    
        
        for(int i = 0; i < list.size()/2; i++)
            currentWinners.add(list.get(i));        
        
        for(int k = list.size()/2; k < list.size(); k++)
            pastWinners.add(list.get(k)); 
                */
    }
    
    public void addSponser() {

        DashDAO dao = new DashDAOImpl();
        int status = dao.updateSponsers(inputToken);
        
        if(status == 1)
            response = "Sponsors Updated";
        else
            response = "Error: Sponsor Update Failed";
                
    }
    
    //---------------------Getters and Setters----------------------------

    public ArrayList<WeeklyWinners> getPastWinners() {
        return pastWinners;
    }

    public void setPastWinners(ArrayList<WeeklyWinners> pastWinners) {
        this.pastWinners = pastWinners;
    }
    
    public ArrayList<WeeklyWinners> getCurrentWinners() {
        return currentWinners;
    }

    public void setCurrentWinners(ArrayList<WeeklyWinners> currentWinners) {
        this.currentWinners = currentWinners;
    }

    public String getInputToken() {
        return inputToken;
    }

    public void setInputToken(String inputToken) {
        this.inputToken = inputToken;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUserName() {
        return "Hello " + lc.getEmail();
    }
    
    public void setUsername(String username) {
        this.userName = username;
    }
    
    public String getSponser() {
        
        DashDAO dao = new DashDAOImpl();
        String out = dao.getSponsers();
        return out;
    }

    public void setSponser(String sponser) {
        this.sponser = sponser;
    }  
    
    public LoginController getLc() {
        return lc;
    }

    public void setLc(LoginController lc) {
        this.lc = lc;
    }
}