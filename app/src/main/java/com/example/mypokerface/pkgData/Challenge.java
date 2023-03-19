package com.example.mypokerface.pkgData;

public class Challenge {
    private int rounds;
    private int points;
    private String challengInfo;
    private String difficulty;
    private static int numberChallenge=0;



    public Challenge (int rounds, int points, String challengInfo,String difficulty ) {
        this.rounds = rounds;
        this.points = points;
        this.challengInfo = challengInfo;
        this.numberChallenge = numberChallenge+1;
        this.difficulty = difficulty;
        numberChallenge++;
    }


    public int getRounds() {
        return rounds;
    }

    public int getPoints() {
        return points;
    }

    public String getChallengInfo() {
        return challengInfo;
    }

    public int getNumberChallenge() {
        return numberChallenge;
    }
    public String getDifficulty() {return difficulty;}

}
