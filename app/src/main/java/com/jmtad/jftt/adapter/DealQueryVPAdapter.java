package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jmtad.jftt.module.mine.RecordFragment;

import java.util.List;

/**
 * @description:交易查询页面类别页面适配器
 * @author: luweiming
 * @create: 2018-10-22 11:49
 **/
public class DealQueryVPAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<RecordFragment> fragments;

    public DealQueryVPAdapter(Context context, List<RecordFragment> fragments, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "全部";
            case 1:
                return "收入";
            case 2:
                return "支出";
        }
        return "";
    }
}
