package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    // view pager

    RecommendFragment fragment1;
    SearchFragment searchFragment;
    ThirdFragment thirdFragment;
    Fragment4 fragment4;
    public static Context mContext;

    public static ArrayList<Item> ThemaItem = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if(ThemaItem.isEmpty()) {
            make_dummy(); }

        //GPS service cause of speed
        Intent intentGpsService = new Intent(this, GpsService.class);
        startService(intentGpsService);




        System.setProperty("http.keepAlive", "false");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        fragment1 = new RecommendFragment();
        //fragment2 = new Fragment2();
        searchFragment = new SearchFragment();
        thirdFragment = new ThirdFragment();
        fragment4 = new Fragment4();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();


        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("추천"));
        tabs.addTab(tabs.newTab().setText("검색"));
        tabs.addTab(tabs.newTab().setText("맛집"));
        tabs.addTab(tabs.newTab().setText("카테고리"));



        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);

                Fragment selected = null;

                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = searchFragment;
                }else if (position == 2) {
                    selected = thirdFragment;
                }else if (position == 3) {
                    selected = fragment4;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(MainActivity.this, optionActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        SaveData svData = new SaveData(this);
        if(svData.isFood()){
            ThemaItem = svData.getFood("SharedFood");
            Log.i("aaaa", "222222222222222222222222222" + ThemaItem.get(0).title);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SaveData svData = new SaveData(this);
        svData.save("SharedNews");
        svData.save("SharedFood");
    }

    public void make_dummy() {

        for (int i=1; i<13;i++) {
            Item item = new Item();
            item.title = "(X)title"+i;
            item.category = "(X)category"+i;
            item.address = "(X) add ress"+i;
            item.imageUrl = "http://222.116.135.76:8080/Noon/images/noon.png";
            item.phone = "010-2043-5392";
            ThemaItem.add(i-1,item);
        }

    }

}
