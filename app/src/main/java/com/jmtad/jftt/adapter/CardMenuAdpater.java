package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jmtad.jftt.module.mine.MenuFragment;

import java.util.List;

/**
 * @description:个人中心菜单列表适配器
 * @author: luweiming
 * @create: 2018-10-17 14:57
 **/
public class CardMenuAdpater extends FragmentStatePagerAdapter {
    List<MenuFragment> fragmentList;
    Context context;

    public CardMenuAdpater(FragmentManager fm, Context context, List<MenuFragment> fragments) {
        super(fm);
        this.context = context;
        this.fragmentList = fragments;
    }


    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

}
