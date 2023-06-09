package com.example.mypokerface.pkgData;

import android.content.Context;

import com.example.mypokerface.pkgActivity.MainActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private static int MAX_DICES = 5;
    private static final String FILENAME = "dice.ser";
    private static final String FILENAMECHALLENGE = "challenge.ser";
    private static int cnt = 0;
    private static Database db = null;
    private ArrayList<Dice> collDices = new ArrayList<>();
    private ArrayList<Game> collGames = new ArrayList<>();
    private ArrayList<Challenge> collChallenge = new ArrayList<>();
    private int totalChallengePoints = 0;


    private Game currGame;


    private ArrayList<GameSet> collGameSet = new ArrayList<>();
    private GameSet gameSet = new GameSet();




    private Database(){
        collDices.clear();
        collGames.clear();
        collGameSet.clear();
        initDices();
    }

    public static Database getInstance(){
        if(db == null){
            db = new Database();
        }

        return db;
    }
    private void initDices(){
        for(int cnt = 0; cnt < MAX_DICES; cnt++){
            collDices.add(new Dice());
        }
    }

    public void doDicing() throws Exception{
        for(Dice dice : collDices){
            dice.doDicing();
        }
    }




    public void addGame(Game game){
        currGame = game;
        collGames.add(game);
    }

    public Game getCurrGame(){
        return currGame;
    }


    public void addGameSet(GameSet gameSet){
        collGameSet.add(gameSet);
    }

    public GameSet getCurrentGameSet() {

        if (collGameSet.isEmpty()) {
            gameSet = new GameSet();
            collGameSet.add(gameSet);
        } else {
            gameSet = collGameSet.get(collGameSet.size()-1);
        }
        return gameSet;
    }





    public ArrayList<Game> getCollGames(){
        return this.collGames;
    }

    public Dice getNthDice(int n) throws Exception{
        if ((n < 0) || (n >= MAX_DICES)){
            throw new Exception("check n(" + n + ")");
        }
        return collDices.get(n);
    }

    public GameSet getGameSet(){
        collGameSet.add(gameSet);
        return gameSet;
    }




    public void lockDie(Dice currDice) throws Exception {
        currDice.lockDie();
    }

    public void openDie(Dice currDice) throws Exception {
        currDice.openDie();
    }

    public void openDice() throws Exception {
        for(Dice dice : collDices) {
            dice.openDie();
        }
    }

    public ArrayList<Dice> getCollDices(){
        return collDices;
    }

    public ArrayList<GameSet> getCollGameSet(){
        return collGameSet;
    }

    public void serializeData(Context context) throws Exception {
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(collGameSet);
        oos.flush();
        oos.close();
        fos.close();
    }

    public void deserializeData(Context context) throws Exception{
        FileInputStream fis = context.openFileInput(FILENAME);
        ObjectInputStream ois = new ObjectInputStream(fis);
        collGameSet = (ArrayList<GameSet>) ois.readObject();
        ois.close();
        fis.close();
    }

    public void deserializeDataChallenge(Context context) throws Exception{
        FileInputStream fis = context.openFileInput(FILENAMECHALLENGE);
        ObjectInputStream ois = new ObjectInputStream(fis);
        totalChallengePoints = (int) ois.readObject();
        ois.close();
        fis.close();
    }

    public void serializeDataChallenge(Context context) throws Exception {
        FileOutputStream fos = context.openFileOutput(FILENAMECHALLENGE, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(totalChallengePoints);
        oos.flush();
        oos.close();
        fos.close();
    }

    public int getTotalChallengePoints () {
        return totalChallengePoints;
    }

    public void setTotalChallengePoints (int totalChallengePoints) {
        this.totalChallengePoints = totalChallengePoints;
    }

    public void insertChallenges () {
        collChallenge.add(new Challenge(3, 20, "score 20 points in 3 games","easy",2));
        collChallenge.add(new Challenge(3, 30, "score 30 points in 3 games","middle",3));
        collChallenge.add(new Challenge(3, 40, "score 40 points in 3 games","hard",4));
        collChallenge.add(new Challenge(2, 15, "score 15 points in 2 games","easy",2));
        collChallenge.add(new Challenge(2, 25, "score 25 points in 2 games","middle",3));
        collChallenge.add(new Challenge(2, 35, "score 35 points in 2 games","hard",4));
        collChallenge.add(new Challenge(2, 15, "score 15 points in 2 games","easy",2));
        collChallenge.add(new Challenge(2, 25, "score 25 points in 2 games","middle",3));
        collChallenge.add(new Challenge(1, 20, "score 20 points in 1 games","hard",4));
        collChallenge.add(new Challenge(5, 80, "score 80 points in 5 games","super hard",5));
        collChallenge.add(new Challenge(6, 100, "score 100 points in 6 games","super hard",5));
    }

    public ArrayList<Challenge> getCollChallenge () {
        return collChallenge;
    }


}
