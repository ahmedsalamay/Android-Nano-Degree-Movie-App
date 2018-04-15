package com.phantom.asalama.movies;

import com.squareup.picasso.Picasso;

import dagger.Component;

@MovieAppScope
@Component(modules = {MovieServicesModule.class, PicasoModule.class})
public interface MovieAppComponent {
    Picasso getPicasso();

    MovieService getMovieService();
}
