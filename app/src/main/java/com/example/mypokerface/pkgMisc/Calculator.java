package com.example.mypokerface.pkgMisc;

import com.example.mypokerface.pkgData.Dice;
import com.example.mypokerface.pkgData.Game;


import java.util.ArrayList;

import java.util.Comparator;


public class Calculator {

    private static int cntDicePoints[] = new int[Dice.MAX_POINTS + 1];
    private static int FULLHOUSE = 25;
    private static int SMALL_STRAIGHT = 30;
    private static int LARGE_STRAIGHT = 40;
    private static int HIT = 50;

    private static int points;
    private static String remarks;



    public static Game getResults(ArrayList<Dice> dices) throws Exception {
        dices.sort(Comparator.comparingInt(Dice::getPoints));

        resetAllValues();

        if(checkFullHouse(dices)){
            points = 25;
            remarks = "FULL HOUSE";
        }
        else if(checkStreetSmall(dices)){
            points = 30;
            remarks = "LITTLE STRAIGHT";
        }
        else if(checkStreetLarge(dices)){
            points = 40;
            remarks = "LARGE STRAIGHT";

        }
        else if(checkHit(dices)){
            points = 50;
            remarks = "HIT";
        }
        else{
            checkEqualDices(dices);
        }

        return new Game(points, remarks);
    }

    private static void resetAllValues() {
        points = 0;
        remarks = "";
    }


    private static void checkEqualDices(ArrayList<Dice> collDices) {
        int[] freq = new int[Dice.MAX_POINTS + 1];
        int maxFreq = 0;
        boolean foundTwin = false;

        for (Dice dice : collDices) {
            freq[dice.getPoints()]++;
        }

        for (int i = 1; i <= Dice.MAX_POINTS; i++) {
            if (freq[i] > maxFreq) {
                maxFreq = freq[i];
                foundTwin = false;

            } else if (freq[i] == 2) {
                foundTwin = true;
            }
        }

        if (maxFreq == 2 && foundTwin) {
            remarks += "2x TWEEN";

        } else if (maxFreq == 2) {
            remarks += "1x TWEEN";

        } else if (maxFreq == 3) {
            remarks += "3x EQUAL";

        } else if (maxFreq == 4) {
            remarks += "4x EQUAL";

        }
        for (int i = 1; i <= Dice.MAX_POINTS; i++) {
            if (freq[i] >= 2) {
                points += i * freq[i];
            }
        }
    }


    private static boolean checkFullHouse(ArrayList<Dice> collDices) {

            boolean isFullHouse = false;
            int[] count = new int[6];
            int threeOfAKind = 0;
            int twoOfAKind = 0;


            for (Dice dice : collDices) {
                count[dice.getPoints() - 1]++;
            }

            for (int i = 0; i < count.length; i++) {

                if (count[i] == 3) {
                    threeOfAKind++;
                }
                if (count[i] == 2) {
                    twoOfAKind++;
                }
            }

            if (threeOfAKind == 1 && twoOfAKind == 1) {
                isFullHouse = true;
            }

            return isFullHouse;
    }

    public static boolean checkStreetSmall(ArrayList<Dice> collDices) {

        boolean isStreetSmall = false;
        int[] count = new int[5];
        int cnt = 0;

        for (Dice dice : collDices) {
            int currPoints = dice.getPoints();

            if (currPoints >= 1 && currPoints <= 5) {
                count[currPoints-1]++;
            }
        }


        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                cnt++;
            } else {
                cnt = 0;
            }

            if (cnt == 5) {
                isStreetSmall = true;
            }
        }
        return isStreetSmall;
    }

    public static boolean checkStreetLarge(ArrayList<Dice> collDices) {

        boolean isStreetLarge = false;
        int[] count = new int[6];
        int cnt = 0;



        for (Dice dice : collDices) {
            int currPoints = dice.getPoints();

            if (currPoints >= 2 && currPoints <= 6) {
                count[currPoints - 2]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                cnt++;
            } else {
                cnt = 0;
            }

            if (cnt == 5) {
                isStreetLarge = true;
            }
        }
        return isStreetLarge;
    }
    private static boolean checkHit(ArrayList<Dice> collDices) {

        boolean isHit = true;
        int startValue = collDices.get(0).getPoints();

        for(int i = 0; i <= collDices.size(); i++){

            if(collDices.get(i++).getPoints() != startValue){
                isHit = false;
            }
        }
        return isHit;
    }
}