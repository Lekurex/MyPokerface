package com.example.mypokerface.pkgActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.mypokerface.R;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mypokerface.pkgData.Database;

public class ChallengeActivity extends AppCompatActivity {

    private Database db = null;
    private ListView listViewChallengeGames;
    private ArrayAdapter<String> adapterChallengeGames;

    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        getAllViews();
        initOtherThings();
        registrateEventHandlers();
    }
    
    private void getAllViews(){
        
    }
    
    private void initOtherThings(){

    }
    
    private void registrateEventHandlers(){
        
    }

}
