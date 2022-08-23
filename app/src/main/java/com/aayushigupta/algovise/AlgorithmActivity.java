package com.aayushigupta.algovise;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.aayushigupta.algovise.algorithms.AlgorithmFactory;
import com.aayushigupta.algovise.algorithms.model.Algorithm;
import com.aayushigupta.algovise.algorithms.model.DataNode;
import com.aayushigupta.algovise.tools.CountDownTimerFactory;
import com.aayushigupta.algovise.tools.CountDownTimerListener;
import com.aayushigupta.algovise.ui.home.VisualFragment;
import com.aayushigupta.algovise.visualiser.AlgorithmLogSingleton;
import com.aayushigupta.algovise.visualiser.SearchVisualiser;
import com.aayushigupta.algovise.visualiser.SearchVisualiserSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgorithmActivity extends AppCompatActivity {

    public static AlgorithmActivity algorithmActivity;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    private DrawerLayout mDrawerLayout;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView nvDrawer;
    private NavController navController;
    private BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm);
        algorithmActivity = this;

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        bottomNavView = (BottomNavigationView) findViewById(R.id.nav_view);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        bottomNavView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavView, navController);

        Algorithm algorithm = (Algorithm) AlgorithmFactory.getCurrentAlgorithm();
        CountDownTimerFactory.setStepCount(algorithm.getMaxStepCount());
        CountDownTimerListener listener = new AlgoAnimationListener();
        CountDownTimerFactory.setCountDownTimerListener(listener);
        AlgorithmLogSingleton.clearLogs();

        prepareListData();
        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);

        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                AlgorithmFactory.setResources(getResources());
                String algorithmTitle = null;
                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case 0:
                                algorithmTitle = AlgorithmFactory.LINEAR_SEARCH;
                                break;
                            case 1:
                                algorithmTitle = AlgorithmFactory.BINARY_SEARCH;
                                break;
                        }
                        break;
                    case 1:
                        switch (childPosition) {
                            case 0:
                                algorithmTitle = AlgorithmFactory.BUBBLE_SORT;
                                break;
                            case 1:
                                algorithmTitle = AlgorithmFactory.INSERTION_SORT;
                                break;
                            case 2:
                                algorithmTitle = AlgorithmFactory.MERGE_SORT;
                                break;
                            case 3:
                                algorithmTitle = AlgorithmFactory.QUICK_SORT;
                                break;
                        }
                        break;
                    case 2:
                        switch (childPosition) {
                            case 0:
                                algorithmTitle = AlgorithmFactory.LINKED_LIST;
                                break;
                            case 1:
                                algorithmTitle = AlgorithmFactory.LINEAR_STACK;
                                break;
                        }
                        break;
                    case 3:
                        switch (childPosition) {
                            case 0:
                                algorithmTitle = AlgorithmFactory.BST_INSERT;
                                break;
                            case 1:
                                algorithmTitle = AlgorithmFactory.BST_SEARCH;
                                break;
                        }
                        break;
                }
                if(algorithmTitle != null) {

                    AlgorithmFactory.setCurrentAlgorithm(algorithmTitle);
                    Toast toast = Toast.makeText(algorithmActivity.getApplicationContext(),
                            "Algorithm " + algorithmTitle + " selected!!",
                            Toast.LENGTH_SHORT);
                    toast.show();

                    // Stop Animation Timer if running
                    CountDownTimerFactory.stop();

                    // Reload navigation component
                    NavDestination destination = navController.getCurrentDestination();
                    navController.popBackStack(destination.getId(), true);
                    navController.navigate(destination.getId());

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return false;
            }
        });

        showSideNavigation();
    }

    public void showSideNavigation() {

        // This will display an Up icon (<-), we will replace it with hamburger later
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        // Setup drawer view
        setupDrawerContent(nvDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        mDrawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setName("Search");
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setName("Sort");
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setName("Linear");
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setName("Non Linear");
        listDataHeader.add(item4);

        List<String> heading1 = new ArrayList<>();
        heading1.add("Linear Search");
        heading1.add("Binary search");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Bubble Sort");
        heading2.add("Insertion Sort");
        heading2.add("Merge Sort");
        heading2.add("Quicksort");

        List<String> heading3 = new ArrayList<String>();
        heading3.add("Linked List");
        heading3.add("Stack");

        List<String> heading4 = new ArrayList<String>();
        heading4.add("BST Insert");
        heading4.add("BST Search");

        listDataChild.put(listDataHeader.get(0), heading1);
        listDataChild.put(listDataHeader.get(1), heading2);
        listDataChild.put(listDataHeader.get(2), heading3);
        listDataChild.put(listDataHeader.get(3), heading4);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                return true;
//        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private class AlgoAnimationListener implements CountDownTimerListener {

        @Override
        public void onTick(long timeToFinishInMillisecond) {
            CountDownTimerFactory.timeLeftInMillisecond = timeToFinishInMillisecond;
            VisualFragment.visualFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Algorithm algorithm = (Algorithm) AlgorithmFactory.getCurrentAlgorithm();
                    algorithm.executeNextStep();
                    SearchVisualiser view = SearchVisualiserSingleton.getView();
                    view.setDataStructure(algorithm.getDataStrucure());
                    view.setShowArrow(algorithm.getShowArrow());
                    view.refreshDrawableState();
                    view.invalidate();
                    if (algorithm.isAlgoComplete()) {
                        VisualFragment.playBtn.setAlpha(1.0f);
                        VisualFragment.stopBtn.setAlpha(0.9f);
                        CountDownTimerFactory.stop();
                        Drawable res = getResources().getDrawable(R.drawable.ic_play);
                        ImageView playPauseImg = findViewById(R.id.img_PlayPause);
                        playPauseImg.setImageDrawable(res);
                        List<String> params = new ArrayList<String>();
                        switch(algorithm.getName()) {
                            case AlgorithmFactory.LINEAR_STACK:
                                params.add("PEEK");
                                break;
                            case AlgorithmFactory.LINKED_LIST:
                                params.add("TRAVERSE");
                                break;
                        }
                        algorithm.setAlgoParams(params);
                    }
                }
            });
        }

        @Override
        public void onFinish() {
            VisualFragment.visualFragment.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    VisualFragment.playBtn.setAlpha(1.0f);
                    VisualFragment.stopBtn.setAlpha(0.9f);
                    Drawable res = getResources().getDrawable(R.drawable.ic_play);
                    ImageView playPauseImg = findViewById(R.id.img_PlayPause);
                    playPauseImg.setImageDrawable(res);
                    Algorithm algorithm = (Algorithm) AlgorithmFactory.getCurrentAlgorithm();
                    List<String> params = new ArrayList<String>();
                    switch(algorithm.getName()) {
                        case AlgorithmFactory.LINEAR_STACK:
                            params.add("PEEK");
                            break;
                        case AlgorithmFactory.LINKED_LIST:
                            params.add("TRAVERSE");
                            break;
                    }
                    algorithm.setAlgoParams(params);
                }
            });
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
