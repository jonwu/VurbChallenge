package com.androidapp.vurb.codechallenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidapp.vurb.codechallenge.models.Card;
import com.androidapp.vurb.codechallenge.models.Movie;
import com.androidapp.vurb.codechallenge.models.Music;
import com.androidapp.vurb.codechallenge.models.Place;

import java.util.ArrayList;

/**
 * Created by jonwu on 1/8/15.
 */
public class CardListAdapter extends BaseAdapter {
    public ArrayList<Card> cards;
    private static LayoutInflater inflater = null;
    public Context context;
    CardViews cardViews;

    public CardListAdapter(Context context, ArrayList<Card> cards) {
        this.context = context;
        this.cards = cards;
        inflater = LayoutInflater.from(context);
        cardViews = new CardViews();
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (cards.get(position) instanceof Place) {
            return 1;
        }
        else if (cards.get(position) instanceof Movie) {
            return 2;
        }
        else if (cards.get(position) instanceof Music) {
            return 3;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int viewType = this.getItemViewType(position);
        View view = convertView;

        switch(viewType){
            case 1:
                return cardViews.getPlaceView(view, cards.get(position), inflater, parent);
            case 2:
                return cardViews.getMovieView(view, cards.get(position), inflater, parent);
            case 3:
                return cardViews.getMusicView(view, cards.get(position), inflater, parent, context);
            default:
                //Throw Exception
        }
        return view;

    }
}

