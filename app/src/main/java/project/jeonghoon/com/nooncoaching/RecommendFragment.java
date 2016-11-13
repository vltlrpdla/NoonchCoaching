package project.jeonghoon.com.nooncoaching;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecommendFragment extends Fragment {

    private String[] tabs = {"기념일추천", "맞춤추천", "가까운거리추천", "무작위추천"};
    ViewGroup rootView;
    //Data
    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private List<Item> ItemList;
    double latitude;
    double longitude;
    int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
    int page = 1;
    String apikey = MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY;
    String defaultImageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
    private GpsInfo gps;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_recommend, container, false);


        SharedInit SI = new SharedInit(MainActivity.mContext);
        registerAlarm rA = new registerAlarm(MainActivity.mContext);

        //rA.registerWT("Weather.a");
        rA.registerDong("Detailaddr");

        if(!SI.getSharedTrue("isCreate")){
            SI.Init();
            rA.registerplace();
        }

        gps = new GpsInfo(getActivity());
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        ItemList = new ArrayList<>();
        adapter = new ItemsAdapter(getActivity(), ItemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareItems(0);

        TabLayout tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("기념일"));
        tabs.addTab(tabs.newTab().setText("맞춤"));
        tabs.addTab(tabs.newTab().setText("거리"));
        tabs.addTab(tabs.newTab().setText("무작위"));

        tabs.setOnTabSelectedListener(new TabListen());

        return rootView;
    }

    private void prepareItems(int type) {
        //여기서 AsyncTask의 리턴값을 List로 받아야함 받아 온 후 화면에 적용
        // 맞춤 추천 같은 경우는 상위 5가지의 태그값을 스트링으로 가져와서 검색어로 입력하면 되고--> 굳이 api를 사용하는데 Asynk를 중첩해서 쓸 필요가 없다.
        // 무작위나 거리같은 경우는 그냥 쓰면 된다.
        // 기념일만 따로 처리해주면 될듯 하다.
        switch (type) {

            case 0:

                break;
            case 1:

                //처음에 내부 db에 질의 후에 키워드가 있는지 없는지 ...--- 날씨와 위치를 가져오는것이 이미 완료 돼 있어야함 ---로딩 시간을 주고 날씨와 위치를 가져오는것이 확실시 돼야
                //
                DBHandler dh = DBHandler.open(MainActivity.mContext);

                String foodList[] = new String[0];

                foodList = dh.selectFood();

                if(foodList == null || foodList.equals("empty")){
                    showToast("검색된 아이템이 없습니다.");
                }else{

                    String query = foodList[0];
                    Searcher searcher2 = new Searcher();
                    searcher2.searchKeyword(MainActivity.mContext,query , latitude, longitude, radius, page,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                        @Override
                        public void onSuccess(List<Item> itemList) {
                            showResult(itemList);
                        }
                        @Override
                        public void onFail() {
                            showToast("검색된 아이템이 없습니다.");
                        }
                    });
                }

                break;
            case 2:
                OldSearcher distanceSearcher = new OldSearcher();
                distanceSearcher.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, page,2,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        showResult(itemList);
                    }
                    @Override
                    public void onFail() {
                        showToast("검색된 아이템이 없습니다.");
                    }
                });
                break;
            case 3:
                OldSearcher randomSearcher = new OldSearcher();
                randomSearcher.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, page,1,  MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
                    @Override
                    public void onSuccess(List<Item> itemList) {
                        showResult(itemList);
                    }
                    @Override
                    public void onFail() {
                        showToast("검색된 아이템이 없습니다.");
                    }
                });
                break;
        }


    }

    private void showResult(List<Item> itemList) {

        adapter.Clear();

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            //url 이미지가 없으면 그냥 default 이미지 넣어줌
            if(item.getImageUrl().equals("")){
                item.setImageUrl(defaultImageUrl);
            }

            ItemList.add(itemList.get(i));

        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }

    public class TabListen implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {

            int position = tab.getPosition();
            Log.d("MainActivity", "선택된 탭 : " + position);

            switch (position) {
                case 0:
                    prepareItems(0);
                    break;
                case 1:
                    prepareItems(1);
                    break;
                case 2:
                    prepareItems(2);
                    break;
                case 3:
                    prepareItems(3);
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
        /*
        SaveData svData = new SaveData(MainActivity.mContext);
        if(svData.isFood()){
            MainActivity.ThemaItem = svData.getFood("SharedFood");
            Log.i("aaaa", "222222222222222222222222222" + MainActivity.ThemaItem.get(0).title);
        }*/
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void showToast(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
