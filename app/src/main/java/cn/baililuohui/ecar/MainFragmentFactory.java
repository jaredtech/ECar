package cn.baililuohui.ecar;

import android.support.v4.app.Fragment;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import cn.baililuohui.ecar.ui.fragment.MeFragment;
import cn.baililuohui.ecar.ui.fragment.MoreFragment;
import cn.baililuohui.ecar.ui.fragment.NavigationFragment;

/**
 * Created by Jared on 2017/5/4.
 */

public class MainFragmentFactory {
    private static SparseArray<Fragment> fragments;

    public static SparseArray<Fragment> createFragment() {
        fragments = new SparseArray<>();
        fragments.put(0, NavigationFragment.newInstance());
        fragments.put(1, MoreFragment.newInstance());
        fragments.put(2, MeFragment.newInstance());
        return fragments;
    }
}
