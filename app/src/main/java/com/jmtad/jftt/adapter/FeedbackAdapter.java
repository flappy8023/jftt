package com.jmtad.jftt.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-13 10:37
 **/
public class FeedbackAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public FeedbackAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return null == fragments ? 0 : fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "功能建议";
            case 1:
                return "性能问题";

        }
        return super.getPageTitle(position);
    }
}
