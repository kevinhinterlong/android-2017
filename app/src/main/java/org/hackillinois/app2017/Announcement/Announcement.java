package org.hackillinois.app2017.Announcement;

/**
 * Created by tommypacker for HackIllinois' 2016 Clue Hunt
 */
public class Announcement {

    private String title;
    private String message;
    private int category; // Will denote each category by a const int

    public Announcement(String title, String message){
        this.title = title;
        this.message = message;
        this.category = 1;
    }

    public Announcement(String title, String message, int category){
        this.title = title;
        this.message = message;
        this.category = category;
    }

    public String getTitle(){
        return title;
    }

    public String getMessage(){
        return message;
    }

    public int getCategory(){return category;}
}
