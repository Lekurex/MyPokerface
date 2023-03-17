package com.example.mypokerface.pkgData;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TreeMap;

public class GameSet implements Serializable {

    private Calendar localCalendar;
    private LocalDateTime ldt;
    private String timeAsString;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
    private int points = 0;

    private TreeMap<String, Game> treeGameSet = new TreeMap<>();
    ArrayList<String> entries;


    public GameSet(int points){
        this.points += points;
        ldt = LocalDateTime.now();
        initializeGameSet();

    }

    public GameSet(){
        initializeGameSet();

    }



    private void initializeGameSet() {
        treeGameSet.put("1x TWEEN", null);
        treeGameSet.put("2x TWEEN", null);
        treeGameSet.put("3x EQUAL", null);
        treeGameSet.put("4x EQUAL", null);
        treeGameSet.put("FULL HOUSE", null);
        treeGameSet.put("HIT", null);
        treeGameSet.put("JOKER", null);
        treeGameSet.put("LARGE STRAIGHT", null);
        treeGameSet.put("LITTLE STRAIGHT", null);
    }

    public ArrayList<String> getEntries() {
        entries = new ArrayList<>();
        for(String entryKey : treeGameSet.keySet()){
            entries.add(entryKey + " ==> " + treeGameSet.get(entryKey));
        }
        return entries;
    }





    public TreeMap<String, Game> getTreeMap(){
        return this.treeGameSet;
    }


    @Override
    public String toString() {
        return "GameSet: " + ldt + ", total points =>" + points;
    }
}
