package dao;

//import java.util.Date;
import java.util.ArrayList;
import model.*;

/**
 * @author Randall DeVitto
 */
public interface DashDAO {
    public ArrayList<WeeklyWinners> getAllWinners();
    public int updateSponsers(String sponser);
    public String getSponsers();
}
