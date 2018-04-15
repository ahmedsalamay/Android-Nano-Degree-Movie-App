package com.phantom.asalama.movies.screen.home;

import dagger.Component;

@Component(modules = {HomeModule.class})
@HomeScope
public interface HomeComponent {
    // public SectionsPagerAdapter sectionPagerAdapter();
    void injectHome(MovieListActivity movieListActivity);
}
