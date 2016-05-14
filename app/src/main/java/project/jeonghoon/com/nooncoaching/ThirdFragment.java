package project.jeonghoon.com.nooncoaching;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class ThirdFragment extends Fragment {

    RecyclerView recyclerView;
    FavorItemAdapter adapter;
    DBHandler dbHandler;
    ArrayList<FavorItem> FavorItems;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // set orientation to vertical
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // set adapter
        adapter = new FavorItemAdapter(getActivity().getApplicationContext());

        // add sample data
        dbHandler = DBHandler.open(MainActivity.mContext);
        FavorItems = dbHandler.selectFavorItem();


        //if(FavorItems == null) {
          //  adapter.addItem("기념일을 입력해주세요.");
      //  }else{

            for (int i=0; i<FavorItems.size(); i++){

                Log.d("wert3738", i + "번쨰" + 1 + " 아이템" + FavorItems.get(i).getAddress());
                Log.d("wert3738", i + "번쨰" + 2 + " 아이템" + FavorItems.get(i).getTitle());


                String beforeSplitAddress = FavorItems.get(i).getAddress();
                String lc[] = beforeSplitAddress.split(" ");

                staticMerge.loadAddr(MainActivity.mContext);


                if ( lc[2].equals(staticMerge.dong))
                    adapter.addItem(FavorItems.get(i));

            }
     //   }


        recyclerView.setAdapter(adapter);

        // set OnItemClickListener
        adapter.setOnItemClickListener(new FavorItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FavorItemAdapter.ViewHolder holder, View view, int position) {
                FavorItem item = adapter.getItem(position);
                String title = item.getTitle();
                //String imageUrl = item.getImageUrl();
                String phone = item.getPhone();
                //String fDate = item.getfDate();
                String Cate = item.getCategory();

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getPhone()));
                startActivity(intent);


                Toast.makeText(getActivity().getApplicationContext(), "아이템 클릭됨 : " + position + ", " + title + ", " + phone + ", "+Cate , Toast.LENGTH_LONG).show();
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
