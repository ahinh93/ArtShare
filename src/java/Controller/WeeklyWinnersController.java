/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import dao.WeeklyWinnersDAOImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.WeeklyWinners;

/**
 *
 * @author Jesse
 */
@ManagedBean
@SessionScoped
public class WeeklyWinnersController {
    
    private WeeklyWinners winner;
    private ArrayList<WeeklyWinners> winners;
    private String payoutTo;
    private List<WeeklyWinners> listsAvailDates;

    public List<WeeklyWinners> getListsAvailDates() {
        if(winners == null || winners.isEmpty()){
            getWinners();
        }
        listsAvailDates = new ArrayList<WeeklyWinners>();
        for(WeeklyWinners weekWin : winners){
            if(!weekWin.isBeenpaid()){
                listsAvailDates.add(weekWin);
            }
        }
        
        return listsAvailDates;
    }

    public void setListsAvailDates(List<WeeklyWinners> listsAvailDates) {
        this.listsAvailDates = listsAvailDates;
    }

    public String getPayoutTo() {
        return payoutTo;
    }

    public void setPayoutTo(String payoutTo) {
        this.payoutTo = payoutTo;
    }

    public ArrayList<WeeklyWinners> getWinners() {
        WeeklyWinnersDAOImpl daoImp = new WeeklyWinnersDAOImpl();
        
        this.winners = daoImp.findAllWinners();
        return winners;
    }

    public WeeklyWinners getWinner() {
        return winner;
    }

    public void setWinner(WeeklyWinners winner) {
        this.winner = winner;
    }
        
    public WeeklyWinnersController(){
        winner = new WeeklyWinners();
    }
    

    public String payoutWinner(){
        
        if(payoutTo == null){
            return "failedupload.xhtml";
        }
        //need to get the email address of the author of the weeklywinner
        //then calculate their prize amount 
        //then send them an email with this information.
        
        List<WeeklyWinners> list = getListsAvailDates();
        String winnerEmail = "";
        Date toUpdateDate = null;
//        System.out.println("size: "+list.size());
        for(int i=0;i<list.size();i++){
//            System.out.println(list.get(i).getDate().toString()+" == "+payoutTo);
//            System.out.println(list.get(i).getDate().toString().equals(payoutTo));
           if(list.get(i).getDate().toString().equals(payoutTo)){
               toUpdateDate = list.get(i).getDate();
               winnerEmail = list.get(i).getAuthor();
               break;
           }
        }
        
      
        System.out.println("winner email "+winnerEmail);
        //email is now winner.author
        EmailController ec = new EmailController();
        
        double min = 10.00;
        double max = 1000.99;
        Random r = new Random();
        double pmt = min + (max - min) * r.nextDouble();
        
        if("success".equals(ec.emailWinner(winnerEmail, pmt))){
            //update this entry to show it has been paid
            updatePaidForDate(toUpdateDate);
        }
        
        
        return "prizesent.xhtml";
    }
    
    public void updatePaidForDate(Date date){
        
        WeeklyWinnersDAOImpl winnerDAO = new WeeklyWinnersDAOImpl();
        winnerDAO.payoutForWinner(date);
        
        
    }
}