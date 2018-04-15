package com.phantom.asalama.movies.screen.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private boolean mTwoPane;

    public SectionsPagerAdapter(FragmentManager fm, boolean twoPane) {
        super(fm);
        mTwoPane = twoPane;
    }

    @Override
    public Fragment getItem(int position) {
        return TabbedHomeFragment.newInstance(position + 1, mTwoPane);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}