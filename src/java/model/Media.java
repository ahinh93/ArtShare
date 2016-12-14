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
public class Media {
    private int uid;
    private String url;
    private double price;
    private String author;
    private int rating;
    private String tags;
    private String youtubeURL;

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
    }

    public Media(int uid, double price)
    {
        this.uid = uid;
        this.price = price;
    }    
    
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Media(){
    }
    
    
    public Media(int uid, String url, double price, String author,int rating,String tags,String youtubeURL){
        this.uid = uid;
        this.url = url;
        this.price = price;
        this.author = author;
        this.rating = rating;
        this.tags = tags;
        this.youtubeURL = youtubeURL;
    }
    
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
