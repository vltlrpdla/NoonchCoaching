package project.jeonghoon.com.nooncoaching;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by EOM on 2015-08-20.
 */
public class Fragment4 extends Fragment {
    ViewGroup rootView;
    RecyclerView recyclerView;
    CateAdapter adapte;
    Searcher searcher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);

        String[] optionLavala = getResources().getStringArray(R.array.spinnerArray1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(super.getContext(),android.R.layout.simple_spinner_dropdown_item,optionLavala);
        Spinner obj = (Spinner) rootView.findViewById(R.id.spinner1);
        obj.setAdapter(adapter);

        obj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedView, int position, long id) {
                printChecked(selectedView, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        return rootView;
    }

    public void printChecked(View v, int position) {

        LocationManager locationManager = (LocationManager) MainActivity.mContext.getSystemService(Context.LOCATION_SERVICE);
        int radius = 1000;
        int page = 1;
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapte = new CateAdapter(getActivity().getApplicationContext());

        searcher = new Searcher();
        searcher.searchCategory(MainActivity.mContext, "FD6", latitude, longitude, radius, page, MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY, new OnFinishSearchListener() {
            @Override
            public void onSuccess(List<Item> itemList) {
                Item items = new Item();
                String[] str = new String[4];
                for (int i = 0; i < itemList.size(); i++) {
                    items = itemList.get(i);
                    str = items.category.split(">");
                }
                adapte.addItem(new CateItem(items.title,items.placeUrl,items.phone,str[1]));

            }
            @Override
            public void onFail() {

            }
        });
    }
}
