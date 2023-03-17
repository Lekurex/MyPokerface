package com.example.mypokerface.pkgData;


import java.io.Serializable;

public class Game implements Serializable{

    private int points;
    private String remarks;

    private static int numberGame = 1;
    private int id;

    public Game(int points, String remarks){
        this.points = points;
        this.remarks = remarks;
        id = numberGame;
        numberGame++;
    }

    public static void resetNumberGame(){
        numberGame = 1;
    }

    public void setNumberGame(){
        numberGame++;
    }

    public int getPoints(){
        return points;
    }

    public void setPoints(int points){
        this.points = points;
    }

    public String getRemarks(){
        return remarks;
    }

    @Override
    public String toString(){
        return id + ".Game: " + points + "pts," + "remarks=" + remarks;
    }



}
