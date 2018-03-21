package com.phantom.asalama.movies;

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
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1, mTwoPane);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}