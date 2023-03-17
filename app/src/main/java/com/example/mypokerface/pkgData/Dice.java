package com.example.mypokerface.pkgData;

import java.util.Random;

public class Dice {

    public static int MIN_POINTS = 1;
    public static int MAX_POINTS = 6;
    private int points;
    private boolean isDicingAllowed;

    public Dice(int points) {
        this.points = points;
        this.isDicingAllowed = true;
    }

    public Dice() {
        this(0);
    }

    public int getPoints() {
        return points;
    }

    /*
    public void setPoints(int points) {
        this.points = points;
    }
     */

    public boolean isDicingAllowed() {
        return isDicingAllowed;
    }

    /*
    public void setDicingAllowed(boolean dicingAllowed) {
        isDicingAllowed = dicingAllowed;
    }
     */

    @Override
    public String toString() {
        return "Dice{" +
                "points=" + points +
                '}';
    }


    public void doDicing(){
        if(isDicingAllowed){
            Random rand = new Random();
            points = rand.nextInt((MAX_POINTS - MIN_POINTS) + 1) + MIN_POINTS;
        }
    }

    public void lockDie() {
        isDicingAllowed = false;
    }

    public void openDie() {
        isDicingAllowed = true;
    }


    /*
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
     */



}
