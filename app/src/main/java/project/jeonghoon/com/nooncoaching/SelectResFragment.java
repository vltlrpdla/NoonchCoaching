package project.jeonghoon.com.nooncoaching;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by EOM on 2015-08-20.
 */
public class SelectResFragment extends Fragment {

    RecyclerView recyclerView;
    SingerAdapter adapter;
    DBHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_select_res, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // set orientation to vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // set adapter
        adapter = new SingerAdapter(getActivity().getApplicationContext());

        // add sample data
        dbHandler = DBHandler.open(MainActivity.mContext);
        Cursor cursor = dbHandler.food_search_select();


        String[] str = new String[5];
        for(int j =0; j<cursor.getCount(); j++) {
            SingerItem tempItem = null;
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                if ( i == 0 )
                    continue;
                str[i] = cursor.getString(i);
                //Log.d("wert3738", j + "번쨰" + i + "아이템" + cursor.getString(i));
            }
            adapter.addItem(new SingerItem(str[1],str[2],str[3],str[4]));
            Log.d("wert3738", j+"번쨰"+ 1+ " 아이템" + str[1]);
            Log.d("wert3738", j+"번쨰"+ 2+ " 아이템" + str[2]);
            Log.d("wert3738", j+"번쨰"+ 3+ " 아이템" + str[3]);
            Log.d("wert3738", j+"번쨰"+ 4+ " 아이템" + str[4]);

         //   tempItem.title = "" + str[1];
           // tempItem.imageUrl = "" +str[2];
           // tempItem.phone = "" +str[3];
          //  tempItem.fDate = "" +str[4];



            cursor.moveToNext();
        }


        recyclerView.setAdapter(adapter);

        // set OnItemClickListener
        adapter.setOnItemClickListener(new SingerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SingerAdapter.ViewHolder holder, View view, int position) {
                SingerItem item = adapter.getItem(position);
                String title = item.getTitle();
                String imageUrl = item.getImageUrl();
                String phone = item.getPhone();
                String fDate = item.getfDate();

                Toast.makeText(getActivity().getApplicationContext(), "아이템 클릭됨 : " + position + ", " + title + ", " + phone, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
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
}
