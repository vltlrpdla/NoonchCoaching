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


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    // view pager
    private static final String LOG_TAG = "MainActivity";
    RecommendFragment fragment1;
    SearchFragment searchFragment;
    ThirdFragment thirdFragment;
    //Fragment4 fragment4;
    public static Context mContext;
    private GpsInfo gps;
    TabLayout tabs;
    String acceptedData[] = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //여기서 위치와 날씨를 받아서 각 프래그먼트 생성시에 번들값으로 넘겨줘서 필터링으로 쓸수 있도록 전역변수를 사용하지 않아야한다.
        Log.d(LOG_TAG, "앱 진입");

        gps = new GpsInfo(MainActivity.this);

        if (gps.isGetLocation()) {

            staticMerge.latitude = gps.getLatitude();
            staticMerge.longitude = gps.getLongitude();

            Toast.makeText(this, "새 추천이 도착했습니다.", Toast.LENGTH_SHORT).show();

            //여기서 위치와 날씨를 가져왔으면 한다.
            GetDataTask d = new GetDataTask(this);
            try {
                acceptedData = d.execute(gps.getLatitude(), gps.getLongitude()).get();
            }catch(Exception e){
                e.printStackTrace();
            }

            showToast( "날씨 " + acceptedData[0] + " 위치 " + acceptedData[1]);

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

        Bundle argument = new Bundle();
        argument.putString("address",acceptedData[1]);
        argument.putString("weather",acceptedData[0]);


        fragment1 = new RecommendFragment();
        fragment1.setArguments(argument);
        //fragment2 = new Fragment2();
        searchFragment = new SearchFragment();
        searchFragment.setArguments(argument);
        thirdFragment = new ThirdFragment();
        thirdFragment.setArguments(argument);
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

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void showToast(final String text) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
