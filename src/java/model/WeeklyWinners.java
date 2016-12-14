/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import dao.WeeklyWinnersDAOImpl;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jesse
 */
@ManagedBean(name = "WeeklyWinners")
@SessionScoped
public class WeeklyWinners {
    private Date date;
    private int mediaid;
    private boolean beenpaid;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public WeeklyWinners(){
    
    }

    public WeeklyWinners(Date date, int mediaid, boolean beenpaid){
        this.date = date;
        this.mediaid = mediaid;
        this.beenpaid = beenpaid;
    }
    
    public WeeklyWinners(Date date, int mediaid, boolean beenpaid, String author){
        this.date = date;
        this.mediaid = mediaid;
        this.beenpaid = beenpaid;
        this.author = author;
    }
    
    public boolean isBeenpaid() {
        return beenpaid;
    }

    public void setBeenpaid(boolean beenpaid) {
        this.beenpaid = beenpaid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public int getMediaid() {
        return mediaid;
    }

    public void setMediaid(int mediaid) {
        this.mediaid = mediaid;
    }
  
}
