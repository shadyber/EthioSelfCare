package com.ethioroot.selfcare.ethioselfcare;

/**
 * Created by Lincoln on 18/05/16.
 */
public class MainMenu {
    private String name;
    private int numOfSongs;
    private int thumbnail;

    public MainMenu() {
    }

    public MainMenu(String name, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
