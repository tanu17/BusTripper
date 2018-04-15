package com.bustripper.bustripper.UserInterface;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.bustripper.bustripper.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> FragmentListTitles = new ArrayList<>();

    public ActivityAdapter(Context context, FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) { return fragmentList.get(position); }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position){ return null; }
    public void AddFragment(Fragment fragment){
        fragmentList.add(fragment);
   }
}
