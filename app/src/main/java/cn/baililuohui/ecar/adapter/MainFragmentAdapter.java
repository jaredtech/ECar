package cn.baililuohui.ecar.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by Jared on 2017/5/4.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {

    String[] titles;

    SparseArray<Fragment> fragments;

    public MainFragmentAdapter(FragmentManager fm, SparseArray<Fragment> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
