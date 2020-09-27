package com.accelstackace.selfilmindia.Prime;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapterr extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerAdapterr(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                taboneott tab1 = new taboneott();
                return tab1;
            case 1:
                tabtwoott tab2 = new tabtwoott();
                return tab2;
            case 2:
                tabthreeott tab3 = new tabthreeott();
                return tab3;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}