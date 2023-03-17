package com.example.mypokerface.pkgData;

public class Challenge {
    private int rounds;
    private int points;
    private String challengInfo;
    private int numberChallenge;



    public Challenge (int rounds, int points, String challengInfo, int numberChallenge) {
        this.rounds = rounds;
        this.points = points;
        this.challengInfo = challengInfo;
        this.numberChallenge = numberChallenge;
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

}
