package com.mhh.lib.adapter.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j_turn on 2015/12/16.
 * Email 766082577@qq.com
 */
public class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {
                                               /*
                                               让我改成了FragmentStatePagerAdapter，最好是用FragmentStatePagerAdapter
                                               ，因为用FragmentPagerAdapter的话里面的缓存很难搞
                                                */
    private List<FragmentInfo> mFragments;

    private static class FragmentInfo {
        Fragment fragment;
        Class cls;
        String title;
        Bundle bundle;

        FragmentInfo(Class cls) {
            this.cls = cls;
        }

        FragmentInfo(Class cls, String title) {
            this.cls = cls;
            this.title = title;
        }

        FragmentInfo(Class cls, String title, Bundle bundle) {
            this.cls = cls;
            this.title = title;
            this.bundle = bundle;
        }
    }

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    public void addFragment(Class cls) {
        mFragments.add(new FragmentInfo(cls));
    }

    public void addFragment(Class cls, String title) {
        mFragments.add(new FragmentInfo(cls, title));
    }

    public void addFragment(Class cls, String title, Bundle bundle) {
        mFragments.add(new FragmentInfo(cls, title, bundle));
    }

    public void clear() {
        if (mFragments != null) {
            mFragments.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position < mFragments.size()) {
            FragmentInfo info = mFragments.get(position);
            if (info.fragment == null) {
                try {
                    info.fragment = (Fragment) info.cls.newInstance();
                    if (info.bundle != null) {
                        info.fragment.setArguments(info.bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return info.fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position < mFragments.size()) {
            return mFragments.get(position).title;
        }
        return null;
    }

    //这个方法是我自己加的
    public void removeFragment(int position){
        mFragments.remove(position);
    }


    /*
    查了很多资料，发现要做到ViewPager刷新数据，动态更改adapter的数量那种效果只要实现这个方法就可以了：
    注意getItemPostion要返回POSTION_NONE，这时候viewpager会以为没有检测到item的存在从而刷新。
    但是这种方法不适合数据量比较大的。viewpager中删除fragment时，不加这个会出错
     */
    @Override
    public int getItemPosition(Object object) {
            return POSITION_NONE;
    }

    /*public void setFragments(FragmentManager fm) {
        if(this.mFragments != null){
            FragmentTransaction ft = fm.beginTransaction();
            for(FragmentInfo f:this.mFragments){
                ft.remove(f);
            }
            ft.commit();
            ft=null;
            fm.executePendingTransactions();
        }
        notifyDataSetChanged();
    }*/
}
