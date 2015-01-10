package com.androidapp.vurb.codechallenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidapp.vurb.codechallenge.R;
import com.androidapp.vurb.codechallenge.models.Card;
import com.androidapp.vurb.codechallenge.models.Movie;
import com.androidapp.vurb.codechallenge.models.Music;
import com.androidapp.vurb.codechallenge.models.Place;
import com.androidapp.vurb.codechallenge.adapters.viewholders.MovieViewHolder;
import com.androidapp.vurb.codechallenge.adapters.viewholders.MusicViewHolder;
import com.androidapp.vurb.codechallenge.adapters.viewholders.PlaceViewHolder;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by jonwu on 1/9/15.
 * Create list item views for each type of card here.
 */
public class CardViews {

    ImageLoader imageLoader;
    DisplayImageOptions options;

    public CardViews(){
        imageLoader = ImageLoader.getInstance();
        this.options = new DisplayImageOptions.Builder().cacheInMemory(true) // default
                .cacheOnDisk(true).build();
    }

    public View getPlaceView(View view, final Card card, LayoutInflater inflater, ViewGroup parent){
        PlaceViewHolder place;

        if(view == null || view.getTag(R.layout.ac_place_item) == null) {
            place = new PlaceViewHolder();
            view = inflater.inflate(R.layout.ac_place_item, parent, false);

            place.tvCategory = (TextView) view.findViewById(R.id.tvCategory);
            place.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            place.ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            view.setTag(R.layout.ac_place_item, place);
        }else{
            place = (PlaceViewHolder) view.getTag(R.layout.ac_place_item);
        }

        place.tvTitle.setText(card.getTitle());
        imageLoader.displayImage(card.getThumbnail(), place.ivThumbnail, options);
        place.tvCategory.setText(((Place) card).getPlaceCategory());
        return view;
    }

    public View getMovieView(View view, final Card card, LayoutInflater inflater, ViewGroup parent){
        MovieViewHolder movie;

        if(view == null || view.getTag(R.layout.ac_movie_item) == null) {
            movie = new MovieViewHolder();
            view = inflater.inflate(R.layout.ac_movie_item, parent, false);
            movie.ivActor = (ImageView) view.findViewById(R.id.ivActor);
            movie.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            movie.ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            view.setTag(R.layout.ac_movie_item, movie);
        }else{
            movie = (MovieViewHolder) view.getTag(R.layout.ac_movie_item);
        }

        movie.tvTitle.setText(card.getTitle());
        imageLoader.displayImage(card.getThumbnail(), movie.ivThumbnail, options);
        imageLoader.displayImage(((Movie) card).getMovieExtraImageURL(), movie.ivActor, options);

        return view;
    }

    public View getMusicView(View view, final Card card, LayoutInflater inflater, ViewGroup parent, final Context context){
        MusicViewHolder music = new MusicViewHolder();


        if(view == null || view.getTag(R.layout.ac_music_item) == null) {
            view = inflater.inflate(R.layout.ac_music_item, parent, false);
            music.btMusicVideo = (Button) view.findViewById(R.id.btMusicVideo);
            music.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            music.ivThumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
            view.setTag(R.layout.ac_music_item, music);
        }else{
            music = (MusicViewHolder) view.getTag(R.layout.ac_music_item);
        }
        music.tvTitle.setText(card.getTitle());

        imageLoader.displayImage(card.getThumbnail(), music.ivThumbnail, options);
        music.btMusicVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(((Music) card).getMusicVideoURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
