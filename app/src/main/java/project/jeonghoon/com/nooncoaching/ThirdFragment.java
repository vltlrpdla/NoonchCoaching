package project.jeonghoon.com.nooncoaching;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ThirdFragment extends Fragment {


    SelectResFragment selectResFragment;
    MySearchResFragment mySearchResFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_third, container, false);

        selectResFragment = new SelectResFragment();
        mySearchResFragment = new MySearchResFragment();


        getChildFragmentManager().beginTransaction().replace(R.id.container, selectResFragment).commit();


        TabLayout tabs = (TabLayout) rootView.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("나의 음식"));
        tabs.addTab(tabs.newTab().setText("선호 음식"));

        tabs.setOnTabSelectedListener(new TabListen());


        return rootView;
    }

    public class TabListen implements TabLayout.OnTabSelectedListener{
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();

            Log.d("MainActivity", "선택된 탭 : " + position);

            Fragment selected = null;
            if (position == 0) {
                selected = selectResFragment;
            } else if (position == 1) {
                selected = mySearchResFragment;
            }

            getChildFragmentManager().beginTransaction().replace(R.id.container, selected).commit();

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }


    }



}
