package com.aayushigupta.algovise;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.visualiser.SearchVisualiser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_ALGO_PER_TYPE = 6;
    // Menu Elements
    private static String CURRENT_ALGO_TYPE = null;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private final List<CardView> algoCards = new ArrayList<CardView>();
    private final List<LinearLayout> algoItems = new ArrayList<LinearLayout>();
    private final List<ImageView> algoIcons = new ArrayList<ImageView>();
    private final List<TextView> algoTextViews = new ArrayList<TextView>();
    private final AlgorithmFactory algorithmFactory = new AlgorithmFactory();
    private MainActivity mainActivity;

    public static void setImageResourceBasedOnIconString(ImageView imageView, String iconString) {
        switch (iconString) {
            case AlgorithmFactory.SEARCH_ALGO_ICON:
                imageView.setImageResource(R.drawable.ic_search_algo);
                break;
            case AlgorithmFactory.SORT_ALGO_ICON:
                imageView.setImageResource(R.drawable.ic_sort_algo);
                break;
            case AlgorithmFactory.LINEAR_ALGO_ICON:
                imageView.setImageResource(R.drawable.ic_linear_algo);
                break;
            case AlgorithmFactory.NON_LINEAR_ALGO_ICON:
                imageView.setImageResource(R.drawable.ic_non_linear_algo);
                break;
        }
    }

    public static int getAlgorithmIndex(int cardId) {
        int algoIndex = -1;
        switch (cardId) {
            case R.id.algoItem_1:
                algoIndex = 0;
                break;
            case R.id.algoItem_2:
                algoIndex = 1;
                break;
            case R.id.algoItem_3:
                algoIndex = 2;
                break;
            case R.id.algoItem_4:
                algoIndex = 3;
                break;
            case R.id.algoItem_5:
                algoIndex = 4;
                break;
            case R.id.algoItem_6:
                algoIndex = 5;
                break;
        }
        return algoIndex;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;

        // on below line we are creating and initializing
        // variable for display metrics.
        DisplayMetrics displayMetrics = new DisplayMetrics();

        // on below line we are getting metrics for display using window manager.
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // on below line we are getting height
        // and width using display metrics.
        SearchVisualiser.screenHeight = displayMetrics.heightPixels;
        SearchVisualiser.screenWidth = displayMetrics.widthPixels;

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        algoCards.add(findViewById(R.id.algoCard_1));
        algoCards.add(findViewById(R.id.algoCard_2));
        algoCards.add(findViewById(R.id.algoCard_3));
        algoCards.add(findViewById(R.id.algoCard_4));
        algoCards.add(findViewById(R.id.algoCard_5));
        algoCards.add(findViewById(R.id.algoCard_6));
        algoItems.add(findViewById(R.id.algoItem_1));
        algoItems.add(findViewById(R.id.algoItem_2));
        algoItems.add(findViewById(R.id.algoItem_3));
        algoItems.add(findViewById(R.id.algoItem_4));
        algoItems.add(findViewById(R.id.algoItem_5));
        algoItems.add(findViewById(R.id.algoItem_6));
        algoIcons.add(findViewById(R.id.ic_algo_1));
        algoIcons.add(findViewById(R.id.ic_algo_2));
        algoIcons.add(findViewById(R.id.ic_algo_3));
        algoIcons.add(findViewById(R.id.ic_algo_4));
        algoIcons.add(findViewById(R.id.ic_algo_5));
        algoIcons.add(findViewById(R.id.ic_algo_6));
        algoTextViews.add(findViewById(R.id.tv_algo_1));
        algoTextViews.add(findViewById(R.id.tv_algo_2));
        algoTextViews.add(findViewById(R.id.tv_algo_3));
        algoTextViews.add(findViewById(R.id.tv_algo_4));
        algoTextViews.add(findViewById(R.id.tv_algo_5));
        algoTextViews.add(findViewById(R.id.tv_algo_6));
        for (int i = 0; i < MAX_ALGO_PER_TYPE; i++) {
            algoItems.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout cardView = (LinearLayout) v;
                    int algoIndex = getAlgorithmIndex(cardView.getId());
//                    Toast toast = Toast.makeText(mainActivity,
//                            "Algorithm No." + algoIndex + " selected!!",
//                            Toast.LENGTH_SHORT);
//                    toast.show();
                    if (CURRENT_ALGO_TYPE == null) {
                        CURRENT_ALGO_TYPE = algorithmFactory.getAlgorithmType(algoIndex);
                        renderMenuAlgos();
                    } else {
                        // Start the algorithm visualizations requested by user
                        AlgorithmFactory.setResources(getResources());
                        AlgorithmFactory.setCurrentAlgorithm(CURRENT_ALGO_TYPE, algoIndex);
                        Intent i = new Intent(MainActivity.this, AlgorithmActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
        renderMenuAlgos();

        // Skipping Main Activity
        CURRENT_ALGO_TYPE = AlgorithmFactory.SEARCH_ALGORITHM;
        AlgorithmFactory.setResources(getResources());
        AlgorithmFactory.setCurrentAlgorithm(CURRENT_ALGO_TYPE, 0);
        Intent i = new Intent(MainActivity.this, AlgorithmActivity.class);
        startActivity(i);
        finish();

    }

    public void renderMenuAlgos() {
        for (int i = 0; i < MAX_ALGO_PER_TYPE; i++) {
            algoCards.get(i).setVisibility(View.VISIBLE);
        }
        if (CURRENT_ALGO_TYPE == null) {
            algoIcons.get(0).setImageResource(R.drawable.ic_search_algo);
            algoTextViews.get(0).setText(AlgorithmFactory.SEARCH_ALGORITHM);
            algoIcons.get(1).setImageResource(R.drawable.ic_sort_algo);
            algoTextViews.get(1).setText(AlgorithmFactory.SORT_ALGORITHM);
            algoIcons.get(2).setImageResource(R.drawable.ic_linear_algo);
            algoTextViews.get(2).setText(AlgorithmFactory.LINEAR_ALGORITHM);
            algoIcons.get(3).setImageResource(R.drawable.ic_non_linear_algo);
            algoTextViews.get(3).setText(AlgorithmFactory.NON_LINEAR_ALGORITHM);
            for (int i = 4; i < MAX_ALGO_PER_TYPE; i++) {
                algoCards.get(i).setVisibility(View.GONE);
            }
        } else {
            List<AlgorithmFactory.AlgoModel> algoModels = algorithmFactory.getAlgorithmsByType(CURRENT_ALGO_TYPE);
            int algoIndex = 0;
            for (AlgorithmFactory.AlgoModel algoModel : algoModels) {
                algoCards.get(algoIndex).setVisibility(View.VISIBLE);
                setImageResourceBasedOnIconString(algoIcons.get(algoIndex), algoModel.algorithmIcon);
                algoTextViews.get(algoIndex).setText(algoModel.algorithmName);
                algoIndex++;
            }
            while (algoIndex < MAX_ALGO_PER_TYPE) {
                algoCards.get(algoIndex).setVisibility(View.GONE);
                algoIndex++;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        CURRENT_ALGO_TYPE = null;
        renderMenuAlgos();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}