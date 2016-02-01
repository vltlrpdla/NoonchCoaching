package project.jeonghoon.com.nooncoaching;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by jeonghoon on 2016-01-30.
 */
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    // Fragment items
    ArrayList<Fragment> items = new ArrayList<Fragment>();

    // Page titles
    ArrayList<String> titles = new ArrayList<String>();

    /**
     * Constructor
     *
     * @param fm
     */
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment fragment) {
        items.add(fragment);
    }

    /**
     * Add a fragment as a page
     *
     * @param fragment
     * @param title
     */
    public void addItem(Fragment fragment, String title) {
        items.add(fragment);
        titles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
