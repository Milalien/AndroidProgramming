package com.example.android_harjoitukset;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.Random;

import io.reactivex.rxjava3.core.Single;

public class GameActivity extends AppCompatActivity {

    public static final String TAG = "GameActivity";
    private int rndInt;
    private int score;
    private int tries;

    private ImageButton playBtn1;
    private ImageButton playBtn2;
    private ImageButton playBtn3;
    private ImageButton playBtn4;
    private FloatingActionButton restartBtn;

    public Toolbar toolbar;
    TextView scoreValue;
    RxDataStore<Preferences> dataStore;

    Preferences.Key<Integer> KEY_HS;

    Random rand = new Random();
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        playBtn1 = findViewById(R.id.playBtn1);
        playBtn2 = findViewById(R.id.playBtn2);
        playBtn3 = findViewById(R.id.playBtn3);
        playBtn4 = findViewById(R.id.playBtn4);
        restartBtn = findViewById(R.id.restartBtn);
        scoreValue = findViewById(R.id.scoreValue);

        rndInt = rand.nextInt(4);
        score = 5;
        tries = 0;
        restartBtn.setOnClickListener(this::handleOnClickEvents);

        for (ImageButton imageButton : Arrays.asList(playBtn1, playBtn2, playBtn3, playBtn4)) {
            imageButton.setOnClickListener(this::handleOnClickEvents);
        }
        KEY_HS = PreferencesKeys.intKey("Highscore");

        DataStoreSingleton dataStoreSingleton = DataStoreSingleton.getInstance();
        if (dataStoreSingleton.getDataStore() == null) {
            dataStore = new RxPreferenceDataStoreBuilder(this, "highscores").build();
        } else {
            dataStore = dataStoreSingleton.getDataStore();
        }
        dataStoreSingleton.setDataStore(dataStore);

        scoreValue.setText(getStringValue(KEY_HS));

    }

    public void putValue(Preferences.Key<Integer> Key, int value) {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(Key, value);
            return Single.just(mutablePreferences);
        });
    }

    String getStringValue(Preferences.Key<Integer> Key) {
        Single<Integer> value = dataStore.data().firstOrError().map(prefs -> prefs.get(Key)).onErrorReturnItem(0);
        return value.blockingGet().toString();
    }

    private void handleOnClickEvents(View v) {
        score -= tries;
        tries += 1;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        int btnIndex;
        if (v.getId() == R.id.playBtn1) {
            btnIndex = 0;
            playBtn1.startAnimation(animation);
            playBtn1.setVisibility(View.INVISIBLE);
            if (btnIndex == rndInt) {
                playBtn1.setImageResource(R.drawable.darkstar);
                playBtn1.setBackgroundColor(Color.WHITE);
                playBtn1.setVisibility(View.VISIBLE);
                putValue(KEY_HS, score);
            }
        } else if (v.getId() == R.id.playBtn2) {
            btnIndex = 1;
            playBtn2.startAnimation(animation);
            playBtn2.setVisibility(View.INVISIBLE);
            if (btnIndex == rndInt) {
                playBtn2.setImageResource(R.drawable.darkstar);
                playBtn2.setBackgroundColor(Color.WHITE);
                playBtn2.setVisibility(View.VISIBLE);
                putValue(KEY_HS, score);
            }
        } else if (v.getId() == R.id.playBtn3) {
            btnIndex = 2;
            playBtn3.startAnimation(animation);
            playBtn3.setVisibility(View.INVISIBLE);
            if (btnIndex == rndInt) {
                playBtn3.setImageResource(R.drawable.darkstar);
                playBtn3.setBackgroundColor(Color.WHITE);
                playBtn3.setVisibility(View.VISIBLE);
                putValue(KEY_HS, score);
            }
        } else if (v.getId() == R.id.playBtn4) {
            btnIndex = 3;
            playBtn4.startAnimation(animation);
            playBtn4.setVisibility(View.INVISIBLE);
            if (btnIndex == rndInt) {
                playBtn4.setImageResource(R.drawable.darkstar);
                playBtn4.setBackgroundColor(Color.WHITE);
                playBtn4.setVisibility(View.VISIBLE);
                putValue(KEY_HS, score);
            }
        } else if (v.getId() == R.id.restartBtn) {
            finish();
            startActivity(getIntent());
        }


    }
}