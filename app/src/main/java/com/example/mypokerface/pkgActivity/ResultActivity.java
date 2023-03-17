package com.example.mypokerface.pkgActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mypokerface.R;
import com.example.mypokerface.pkgData.Database;
import com.example.mypokerface.pkgData.Game;
import com.example.mypokerface.pkgData.GameSet;
import java.util.TreeMap;

public class ResultActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapterResults;
    private ListView listViewResults;

    private TextView txtGames;
    private TextView txtPoints;
    private Toast toast;

    private static int points = 0;
    private static int clickCnt = 0;

    private TreeMap<String, Game> gameTreeMap = new TreeMap<>();

    GameSet gameSet = new GameSet();
    private Database db = null;
    private int oldPoints;
    private static boolean[] posArr = {false,false,false,false,false,false,false,false,false};

    private Game lastSelectedGame = null;
    private int selectedPos = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getAllViews();
        initOtherThings();
        registrateEventHandlers();
        refillList();
    }


    private void getAllViews() {
        listViewResults = (ListView) findViewById(R.id.listViewResults);
        txtGames = (TextView) findViewById(R.id.txtGames);
        txtPoints = (TextView) findViewById(R.id.txtPoints);
    }

    public static void resetPoints(){
        points = 0;
    }






    private void registrateEventHandlers() {
        listViewResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int selectedPos = -1;

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                String[] result = listViewResults.getItemAtPosition(pos).toString().split(" ==> ");
                String remark = db.getCurrGame().getRemarks();

                if (result[1].equals("null") || pos == selectedPos) {

                    if (pos == selectedPos) {

                        listViewResults.setItemChecked(pos, false);
                        selectedPos = -1;
                        gameTreeMap.replace(result[0], null);
                        points -= db.getCurrGame().getPoints();
                        txtPoints.setText("total points:" + points);

                    } else if (selectedPos == -1) {
                        selectedPos = pos;

                        if (result[0].equals(remark) || result[0].equals("JOKER")) {

                            db.getCurrGame().setPoints(oldPoints);
                            gameTreeMap.replace(result[0], db.getCurrGame());
                            points += db.getCurrGame().getPoints();
                        } else {

                            db.getCurrGame().setPoints(0);
                            gameTreeMap.replace(result[0], db.getCurrGame());

                        }

                        posArr[pos] = true;
                        txtPoints.setText("total points:" + points);
                    } else {
                        listViewResults.setItemChecked(selectedPos, false);
                    }
                    refreshListView();
                }
            }
        });
    }

            private void refreshListView() {
                adapterResults.clear();
                adapterResults.addAll(db.getGameSet().getEntries());
            }


            private void showToast(String msg) {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }


            private void refillList() {
                adapterResults.clear();
                adapterResults.addAll(db.getGameSet().getEntries());
                for (int i = 0; i < posArr.length; i++) {
                    listViewResults.setItemChecked(i, posArr[i]);
                }
            }


            private void initOtherThings() {
                try {
                    db = Database.getInstance();
                    txtGames.setText(db.getCurrGame().toString());
                    txtPoints.setText("total points:" + points);
                    adapterResults = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice);
                    adapterResults.addAll(db.getCurrentGameSet().getEntries());
                    gameSet = db.getGameSet();
                    oldPoints = db.getCurrGame().getPoints();
                    gameTreeMap = gameSet.getTreeMap();
                    listViewResults.setAdapter(adapterResults);
                } catch (Exception ex) {
                    toast = Toast.makeText(this, "error" + ex.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    ex.printStackTrace();
                }
            }


}

