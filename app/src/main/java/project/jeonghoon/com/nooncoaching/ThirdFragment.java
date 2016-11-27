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

public class ThirdFragment extends Fragment {

    private RecyclerView recyclerView;
    private ItemsAdapter adapter;
    private List<Item> ItemList;
    DBHandler dbHandler;
    //ArrayList<FavorItem> FavorItems;
    ArrayList<FavorItem> FavorItems;
    String defaultImageUrl = "http://222.116.135.79:8080/Noon/images/noon.png";
    String address = "";
    String weather ="";
    private static final String LOG_TAG = "ThirdFragment";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // set orientation to vertical
        //LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //recyclerView.setLayoutManager(layoutManager);

        // set adapter
        //adapter = new FavorItemAdapter(getActivity().getApplicationContext());
        ItemList = new ArrayList<>();
        adapter = new ItemsAdapter(getActivity(), ItemList, weather, address);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        address = getArguments().getString("address");
        weather = getArguments().getString("weather");
        Log.d(LOG_TAG,"address "+ address);

        prepareItems(0);

        TabLayout tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("한식"));
        tabs.addTab(tabs.newTab().setText("일식"));
        tabs.addTab(tabs.newTab().setText("중식"));
        tabs.addTab(tabs.newTab().setText("양식"));
        tabs.addTab(tabs.newTab().setText("기타"));

        tabs.setOnTabSelectedListener(new TabListen());

        return rootView;
    }

    private void prepareItems(int type) {

        switch(type) {
            case 0:
                adapter.clear();

                dbHandler = DBHandler.open(MainActivity.mContext);
                FavorItems = dbHandler.selectFavorItem();

                for (int i = 0; i < FavorItems.size(); i++) {

                    String beforeSplitAddress = FavorItems.get(i).getAddress();
                    String ad[] = beforeSplitAddress.split(" ");

                    String beforeSplitCategory = FavorItems.get(i).getCategory();
                    String ca[] = beforeSplitCategory.split(" > ");

                    if (ad[1].equals(address)) {
                        if (ca[1].equals("한식")) {

                            if(FavorItems.get(i).getImageUrl().equals("")){
                                FavorItems.get(i).setImageUrl(defaultImageUrl);
                            }

                            ItemList.add(FavorItems.get(i));
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case 1:
                adapter.clear();

                dbHandler = DBHandler.open(MainActivity.mContext);
                FavorItems = dbHandler.selectFavorItem();

                for (int i = 0; i < FavorItems.size(); i++) {

                    String beforeSplitAddress = FavorItems.get(i).getAddress();
                    String ad[] = beforeSplitAddress.split(" ");

                    String beforeSplitCategory = FavorItems.get(i).getCategory();
                    String ca[] = beforeSplitCategory.split(" > ");

                    if (ad[1].equals(address)) {
                        if (ca[1].equals("일식")) {

                            if(FavorItems.get(i).getImageUrl().equals("")){
                                FavorItems.get(i).setImageUrl(defaultImageUrl);
                            }

                            ItemList.add(FavorItems.get(i));
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case 2:
                adapter.clear();

                dbHandler = DBHandler.open(MainActivity.mContext);
                FavorItems = dbHandler.selectFavorItem();

                for (int i = 0; i < FavorItems.size(); i++) {

                    String beforeSplitAddress = FavorItems.get(i).getAddress();
                    String ad[] = beforeSplitAddress.split(" ");

                    String beforeSplitCategory = FavorItems.get(i).getCategory();
                    String ca[] = beforeSplitCategory.split(" > ");

                    if (ad[1].equals(address)) {
                        if (ca[1].equals("중식")) {

                            if(FavorItems.get(i).getImageUrl().equals("")){
                                FavorItems.get(i).setImageUrl(defaultImageUrl);
                            }

                            ItemList.add(FavorItems.get(i));
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case 3:
                adapter.clear();

                dbHandler = DBHandler.open(MainActivity.mContext);
                FavorItems = dbHandler.selectFavorItem();

                for (int i = 0; i < FavorItems.size(); i++) {

                    String beforeSplitAddress = FavorItems.get(i).getAddress();
                    String ad[] = beforeSplitAddress.split(" ");

                    String beforeSplitCategory = FavorItems.get(i).getCategory();
                    String ca[] = beforeSplitCategory.split(" > ");

                    if (ad[1].equals(address)) {
                        if (ca[1].equals("양식")) {

                            if(FavorItems.get(i).getImageUrl().equals("")){
                                FavorItems.get(i).setImageUrl(defaultImageUrl);
                            }

                            ItemList.add(FavorItems.get(i));
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
            case 4:
                adapter.clear();

                dbHandler = DBHandler.open(MainActivity.mContext);
                FavorItems = dbHandler.selectFavorItem();

                for (int i = 0; i < FavorItems.size(); i++) {

                    String beforeSplitAddress = FavorItems.get(i).getAddress();
                    String ad[] = beforeSplitAddress.split(" ");

                    String beforeSplitCategory = FavorItems.get(i).getCategory();
                    String ca[] = beforeSplitCategory.split(" > ");

                    if (ad[1].equals(address)) {
                        if (!(ca[1].equals("한식") | ca[1].equals("일식") | ca[1].equals("중식") | ca[1].equals("양식"))) {

                            if(FavorItems.get(i).getImageUrl().equals("")){
                                FavorItems.get(i).setImageUrl(defaultImageUrl);
                            }

                            ItemList.add(FavorItems.get(i));
                        }
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
                break;
        }
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
                case 4:
                    prepareItems(4);
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        dbHandler.close();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
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
