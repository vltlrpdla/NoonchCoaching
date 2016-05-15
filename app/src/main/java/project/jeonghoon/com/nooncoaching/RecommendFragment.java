package project.jeonghoon.com.nooncoaching;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class RecommendFragment extends Fragment {



    private String[] tabs = {"기념일추천", "맞춤추천", "가까운거리추천", "무작위추천"};
    MapView mapView;
    TextView nameTv = null;
    TextView telTv  = null;
    TextView cateTv = null;
    TextView addrTv = null;
    ImageView foodImg = null;
    Spinner SP2 =null;
    Button SelectBtn = null;
    ViewGroup mapViewContainer = null;
    ViewGroup rootView;


    //Data


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recommend, container, false);



        SharedInit SI = new SharedInit(MainActivity.mContext);
        registerAlarm rA = new registerAlarm(MainActivity.mContext);
        if(!SI.getSharedTrue("isCreate")){
            SI.Init();
            rA.registerInit();
            rA.registerWT("Weather.a");
            rA.registerDong("Detailaddr");
            rA.registerNews(10);
            rA.registerOneWeek();
            rA.registerpattern();
            rA.registerplace();
        }



        nameTv = (TextView)rootView.findViewById(R.id.nameView);
        telTv = (TextView)rootView.findViewById(R.id.telView);
        cateTv = (TextView)rootView.findViewById(R.id.cateView);
        addrTv = (TextView)rootView.findViewById(R.id.addrView);
        foodImg = (ImageView)rootView.findViewById(R.id.cookImage);
        SP2 = (Spinner)rootView.findViewById(R.id.spinner2);
        SelectBtn = (Button)rootView.findViewById(R.id.check);
        final ArrayList<String> arraylist2 = new ArrayList<String>();
        arraylist2.add("추천1");
        arraylist2.add("추천2");
        arraylist2.add("추천3");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.mContext,
                android.R.layout.simple_spinner_dropdown_item, arraylist2);
        SP2.setAdapter(adapter);

        mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map_view);
        mapViewContainer.removeAllViews();
        mapView = null;
        mapView = new MapView(getActivity());
        mapView.setDaumMapApiKey("9db6272582177f1d7b0643e35e1993e9");
        mapViewContainer.addView(mapView);


        SetFoodViewItem(0);
        TabLayout tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("기념일"));
        tabs.addTab(tabs.newTab().setText("맞춤"));
        tabs.addTab(tabs.newTab().setText("거리"));
        tabs.addTab(tabs.newTab().setText("무작위"));

        tabs.setOnTabSelectedListener(new TabListen());

        return rootView;
    }



    public class TabListen implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            int size = MainActivity.ThemaItem.size();
            Log.d("MainActivity", "선택된 탭 : " + position);

            switch (position) {
                case 0:
                    if(size>=0) {
                        SetFoodViewItem(0);
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position){
                                        case 0:
                                            SetFoodViewItem(0);
                                            break;
                                        case 1:
                                            SetFoodViewItem(1);
                                            break;
                                        case 2:
                                            SetFoodViewItem(2);
                                            break;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    SetFoodViewItem(0);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0, true);
                    break;
                case 1:
                    if(size>=3) {
                        SetFoodViewItem(3);
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position){
                                        case 0:
                                            SetFoodViewItem(3);
                                            break;
                                        case 1:
                                            SetFoodViewItem(4);
                                            break;
                                        case 2:
                                            SetFoodViewItem(5);
                                            break;

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    SetFoodViewItem(3);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0);
                    break;
                case 2:
                    if(size>=6) {
                        SetFoodViewItem(6);
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    switch (position){
                                        case 0:
                                            SetFoodViewItem(6);
                                            break;
                                        case 1:
                                            SetFoodViewItem(7);
                                            break;
                                        case 2:
                                            SetFoodViewItem(8);
                                            break;

                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    SetFoodViewItem(6);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0);
                    break;
                case 3:
                    if(size>=9) {
                        SetFoodViewItem(9);
                        if(MainActivity.ThemaItem.size()>0){
                            SP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    switch (position){
                                        case 0:
                                            SetFoodViewItem(9);
                                            break;
                                        case 1:
                                            SetFoodViewItem(10);
                                            break;
                                        case 2:
                                            SetFoodViewItem(11);
                                            break;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                    SetFoodViewItem(9);
                                }
                            });
                        }
                    }
                    SP2.setSelection(0);
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        SaveData svData = new SaveData(MainActivity.mContext);
        if(svData.isFood()){
            MainActivity.ThemaItem = svData.getFood("SharedFood");
            Log.i("aaaa", "222222222222222222222222222" + MainActivity.ThemaItem.get(0).title);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SaveData svData = new SaveData(MainActivity.mContext);
        svData.save("SharedNews");
        svData.save("SharedFood");
    }
    public void SetFoodViewItem(final int index){
        mapView.removeAllPOIItems();
        Item in1 = MainActivity.ThemaItem.get(index);
        MapPOIItem marker = new MapPOIItem();
        nameTv.setText("" + in1.title);
        telTv.setText("" + in1.phone);
        cateTv.setText("" + in1.category);
        addrTv.setText("" + in1.address);
        if(in1.imageUrl.equals("")){
            in1.imageUrl ="http://222.116.135.79:8080/Noon/images/noon.png";
            new DownloadImageTask(foodImg).execute(in1.imageUrl);
        }else{
            new DownloadImageTask(foodImg).execute(in1.imageUrl);
        }
        telTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item in1 = MainActivity.ThemaItem.get(index);
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+in1.phone));
                startActivity(intent);
            }
        });
        SelectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"선택 되었습니다.", Toast.LENGTH_SHORT).show();
                Item in1 = MainActivity.ThemaItem.get(index);
                DBHandler dbHandler = DBHandler.open(MainActivity.mContext, in1);
                dbHandler.click_time();
                boolean flag = dbHandler.insertFavorItem();
                if ( flag ){
                    Log.d("wert3738","입력 완료");
                }else{
                    Log.d("wert3738","입력 실패");
                }
                dbHandler.stored_data_insert();
                dbHandler.selectFood();
                dbHandler.close();

            }
        });
        marker.setItemName("Default Marker");
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(in1.latitude, in1.longitude), true);
        mapView.addPOIItem(marker);
    }
}
