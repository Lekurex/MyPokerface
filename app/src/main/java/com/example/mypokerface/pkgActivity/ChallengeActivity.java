package com.example.mypokerface.pkgActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypokerface.R;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mypokerface.pkgData.Challenge;
import com.example.mypokerface.pkgData.Database;
import com.example.mypokerface.pkgData.Dice;
import com.example.mypokerface.pkgData.Game;
import com.example.mypokerface.pkgData.GameSet;
import com.example.mypokerface.pkgMisc.Calculator;

import java.util.ArrayList;

public class ChallengeActivity extends AppCompatActivity implements View.OnClickListener {

    private Database db = null;
    private ListView listViewChallenge;
    private TextView txtViewTotalPoints;
    private TextView txtViewInfo;
    private TextView txtViewPoints;
    private TextView txtViewMode;
    private static String newline = System.getProperty("line.separator");


    private ArrayAdapter<String> adapterChallengeGame;

    private ArrayList<Game> collGames = new ArrayList<>();

    private ArrayList<ImageView> collDices;
    private Dice currDice;
    private Toast toast;
    private Button btn1stDicing;
    private Button btn2ndDicing;
    private Button btn3rdDicing;
    private Button btnNewGame;
    private ArrayList<Challenge> collChallenges;
    private Challenge currChallenge;
    private int totalPoints = 0;
    private int challengeRounds = 0;

    private static int cntGame = 0;
    private boolean resetListView = false;

    private ImageView ivDice1, ivDice2, ivDice3, ivDice4, ivDice5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        getAllViews();
        registrateEventHandlers();
        initOtherThings();
        db.insertChallenges();
        collChallenges = db.getCollChallenge();

        try {
            db.deserializeDataChallenge(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getCurrentChallenge();
    }

    private void getCurrentChallenge() {
        int  idx = (int) (Math.random() * ((collChallenges.size() - 1) - 0));
        currChallenge = collChallenges.get(idx);

        txtViewMode.setText("CHALLENGE difficulty: "+currChallenge.getDifficulty());
        txtViewInfo.setText("Challenge-Info: "+currChallenge.getChallengInfo());
        totalPoints = 0;
        txtViewPoints.setText("current total points: "+totalPoints);
        challengeRounds = currChallenge.getRounds();
        txtViewTotalPoints.setText("your total Challenge-Points: "+db.getTotalChallengePoints());
    }


    private void registrateEventHandlers() {
        btn1stDicing.setOnClickListener(this);
        btn2ndDicing.setOnClickListener(this);
        btn3rdDicing.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        ivDice1.setOnClickListener(this);
        ivDice2.setOnClickListener(this);
        ivDice3.setOnClickListener(this);
        ivDice4.setOnClickListener(this);
        ivDice5.setOnClickListener(this);
    }

    private void getAllViews() {
        btn1stDicing = findViewById(R.id.button1stdicing);
        btn2ndDicing = findViewById(R.id.button2nddicing);
        btn3rdDicing = findViewById(R.id.button3rddicing);
        btnNewGame = findViewById(R.id.buttonNewGame);
        ivDice1 = findViewById(R.id.ImageViewDice1);
        ivDice2 = findViewById(R.id.ImageViewDice2);
        ivDice3 = findViewById(R.id.ImageViewDice3);
        ivDice4 = findViewById(R.id.ImageViewDice4);
        ivDice5 = findViewById(R.id.ImageViewDice5);
        listViewChallenge = (ListView) findViewById(R.id.listViewChallengeGames);
        txtViewMode = (TextView) findViewById(R.id.textViewMode);
        txtViewPoints = (TextView) findViewById(R.id.textViewPoints);
        txtViewInfo = (TextView) findViewById(R.id.textViewInfo);
        txtViewTotalPoints = (TextView) findViewById(R.id.textViewTotalPoints);


    }

    private void initOtherThings() {
        try {
            db = Database.getInstance();
            collDices = new ArrayList<>();
            collDices.add(ivDice1);
            collDices.add(ivDice2);
            collDices.add(ivDice3);
            collDices.add(ivDice4);
            collDices.add(ivDice5);
            adapterChallengeGame = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            listViewChallenge.setAdapter(adapterChallengeGame);
            setClickable(false);

        }catch (Exception ex) {
            toast = Toast.makeText(this, "error" + ex.getMessage(), Toast.LENGTH_LONG);
            toast.show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        try {

            switch (view.getId()) {
                case R.id.buttonNewGame:
                    setDiceZero();
                    setClickable(false);
                    btn1stDicing.setEnabled(true);
                    btn2ndDicing.setEnabled(true);
                    btn3rdDicing.setEnabled(true);
                    db.openDice();
                    setPaddings();
                    if(cntGame == challengeRounds){

                        db.deserializeDataChallenge(this);
                        int currentTotalPoints = db.getTotalChallengePoints();

                        if(totalPoints >= currChallenge.getPoints()){
                            currentTotalPoints += currChallenge.getPoints();
                            db.setTotalChallengePoints(currentTotalPoints);
                            db.serializeDataChallenge(this);
                        }
                        getCurrentChallenge();
                        adapterChallengeGame.clear();
                        cntGame = 0;
                        Game.resetNumberGame();

                    }

                    //toast = Toast.makeText(this, "new game started => do 1st dicing!", Toast.LENGTH_LONG);
                    //toast.show();
                    break;

                case R.id.button1stdicing:
                    setClickable(true);
                    db.doDicing();
                    repaintDices();
                    btn1stDicing.setEnabled(false);
                    //toast = Toast.makeText(this, "1. dicing done", Toast.LENGTH_LONG);
                    //toast.show();
                    break;

                case R.id.button2nddicing:

                    if(!btn1stDicing.isEnabled()){
                        db.doDicing();
                        repaintDices();
                        btn2ndDicing.setEnabled(false);

                    }

                    break;

                case R.id.button3rddicing:
                    db.doDicing();

                    if(!btn1stDicing.isEnabled() && !btn2ndDicing.isEnabled()){
                        repaintDices();
                        btn3rdDicing.setEnabled(false);
                        Game game = Calculator.getResults(db.getCollDices());
                        adapterChallengeGame.add(game.toString());
                        totalPoints += game.getPoints();
                        redrawPoints();
                        cntGame++;

                    }
                    else{
                        // = Toast.makeText(this, "error: first or second dicing not done", Toast.LENGTH_LONG);
                        //toast.show();
                    }
                    break;

                case R.id.ImageViewDice1:
                    //toast = Toast.makeText(this, "1. dice selected", Toast.LENGTH_LONG);
                    //toast.show();
                    currDice = db.getNthDice(0);
                    if(currDice.isDicingAllowed()){
                        db.lockDie(currDice);
                        ivDice1.setPadding(0,10,0,10);
                    }else {
                        db.openDie(currDice);
                        ivDice1.setPadding(0,0,0,0);
                    }
                    break;

                case R.id.ImageViewDice2:
                    //toast = Toast.makeText(this, "2. dice selected", Toast.LENGTH_LONG);
                    //toast.show();
                    currDice = db.getNthDice(1);
                    if(currDice.isDicingAllowed()){
                        db.lockDie(currDice);
                        ivDice2.setPadding(0,10,0,10);
                    }else {
                        db.openDie(currDice);
                        ivDice2.setPadding(0,0,0,0);
                    }
                    break;


                case R.id.ImageViewDice3:
                    //toast = Toast.makeText(this, "3. dice selected", Toast.LENGTH_LONG);
                    //toast.show();
                    currDice = db.getNthDice(2);
                    if(currDice.isDicingAllowed()){
                        db.lockDie(currDice);
                        ivDice3.setPadding(0,10,0,10);
                    }else {
                        db.openDie(currDice);
                        ivDice3.setPadding(0,0,0,0);
                    }
                    break;


                case R.id.ImageViewDice4:
                    //toast = Toast.makeText(this, "4. dice selected", Toast.LENGTH_LONG);
                    //toast.show();
                    currDice = db.getNthDice(3);
                    if(currDice.isDicingAllowed()){
                        db.lockDie(currDice);
                        ivDice4.setPadding(0,10,0,10);
                    }else {
                        db.openDie(currDice);
                        ivDice4.setPadding(0,0,0,0);
                    }
                    break;


                case R.id.ImageViewDice5:
                    //toast = Toast.makeText(this, "5. dice selected", Toast.LENGTH_LONG);
                    //toast.show();
                    currDice = db.getNthDice(4);
                    if(currDice.isDicingAllowed()){
                        db.lockDie(currDice);
                        ivDice5.setPadding(0,10,0,10);
                    }else {
                        db.openDie(currDice);
                        ivDice5.setPadding(0,0,0,0);
                    }
                    break;
            }
        }catch (Exception ex) {
            toast = Toast.makeText(this, "error" + ex.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void redrawPoints() {
        txtViewPoints.setText("current total points: "+totalPoints);
    }

    private void setPaddings() {
        ivDice1.setPadding(0, 0, 0, 0);
        ivDice2.setPadding(0, 0, 0, 0);
        ivDice3.setPadding(0, 0, 0, 0);
        ivDice4.setPadding(0, 0, 0, 0);
        ivDice5.setPadding(0, 0, 0, 0);
    }

    private void setDiceZero(){
        ivDice1.setImageResource(R.drawable.dice0);
        ivDice2.setImageResource(R.drawable.dice0);
        ivDice3.setImageResource(R.drawable.dice0);
        ivDice4.setImageResource(R.drawable.dice0);
        ivDice5.setImageResource(R.drawable.dice0);

    }

    private void setClickable(boolean clickable){
        ivDice1.setClickable(clickable);
        ivDice2.setClickable(clickable);
        ivDice3.setClickable(clickable);
        ivDice4.setClickable(clickable);
        ivDice5.setClickable(clickable);
    }



    private void repaintDices() throws Exception {
        int i = 0;
        for(ImageView iv: collDices) {
            int points = db.getNthDice(i).getPoints();
            // set dice-image
            switch (points) {
                case 1: iv.setImageResource(R.drawable.dice1); break;
                case 2: iv.setImageResource(R.drawable.dice2); break;
                case 3: iv.setImageResource(R.drawable.dice3); break;
                case 4: iv.setImageResource(R.drawable.dice4); break;
                case 5: iv.setImageResource(R.drawable.dice5); break;
                case 6: iv.setImageResource(R.drawable.dice6); break;
            }
            i++;
            iv.invalidate();
        }
    }
}
