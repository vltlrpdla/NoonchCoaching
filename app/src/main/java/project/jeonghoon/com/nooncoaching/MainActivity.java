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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    // view pager
    private static final String LOG_TAG = "MainActivity";
    RecommendFragment fragment1;
    SearchFragment searchFragment;
    ThirdFragment thirdFragment;
    //Fragment4 fragment4;
    public static Context mContext;
    public static ArrayList<Item> ThemaItem = new ArrayList<Item>();
    private GpsInfo gps;
    TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        if(ThemaItem.isEmpty()) {
            make_dummy();
        }
        Log.d(LOG_TAG, "앱 진입");

        gps = new GpsInfo(MainActivity.this);

        if (gps.isGetLocation()) {

            staticMerge.latitude = gps.getLatitude();
            staticMerge.longitude = gps.getLongitude();

            registerAlarm rA = new registerAlarm(getApplicationContext());
            rA.testAM2("ACTION.GET.NORMAL", 1);
            Toast.makeText(this, "새 추천이 도착했습니다.", Toast.LENGTH_SHORT).show();

           // Toast.makeText(
           //         getApplicationContext(),
           //         "당신의 위치 - \n위도: " + staticMerge.latitude + "\n경도: " + staticMerge.longitude,
           //         Toast.LENGTH_LONG).show();
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }

        System.setProperty("http.keepAlive", "false");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setIcon(R.drawable.noon_icon);


        fragment1 = new RecommendFragment();
        //fragment2 = new Fragment2();
        searchFragment = new SearchFragment();
        thirdFragment = new ThirdFragment();
        //fragment4 = new Fragment4();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();


        tabs = (TabLayout) findViewById(R.id.tabs);

        //tabs.addTab(tabs.newTab().setIcon(tabIcons[0]));
        //tabs.addTab(tabs.newTab().setIcon(tabIcons[1]));
        //tabs.addTab(tabs.newTab().setIcon(tabIcons[2]));
        //tabs.addTab(tabs.newTab().setText("카테고리"));


        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("추천");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_store_white_18dp, 0, 0);
        tabs.addTab(tabs.newTab().setCustomView(tabOne));

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("검색");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_search_white_18dp, 0, 0);
        tabs.addTab(tabs.newTab().setCustomView(tabTwo));

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("맛집");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_star_rate_white_18dp, 0, 0);
        tabs.addTab(tabs.newTab().setCustomView(tabThree));


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d(LOG_TAG, "선택된 탭 : " + position);

                Fragment selected = null;

                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = searchFragment;
                } else if (position == 2) {
                    selected = thirdFragment;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
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
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // Handle action buttons


        int id = item.getItemId();

        if (id == R.id.action_alarm) {
            Intent intent = new Intent(MainActivity.this, optionActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_refresh){

            gps = new GpsInfo(getApplicationContext());

            staticMerge.latitude = gps.getLatitude();
            staticMerge.longitude = gps.getLongitude();

            registerAlarm rA = new registerAlarm(getApplicationContext());
            rA.testAM2("ACTION.GET.NORMAL", 1);
            Toast.makeText(this, "새 추천이 도착했습니다.", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_anniversary){
            Intent intent = new Intent(MainActivity.this, AnniActivity.class);
            startActivity(intent);

            return true;
        }



        return super.onOptionsItemSelected(item);

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

        for (int i=1; i<21;i++) {
            Item item = new Item();
            item.title = "(X)title"+i;
            item.category = "(X)category"+i;
            item.address = "(X) add ress"+i;
            item.imageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
            item.phone = "010-2043-5392";
            ThemaItem.add(i-1,item);
        }

    }

}
