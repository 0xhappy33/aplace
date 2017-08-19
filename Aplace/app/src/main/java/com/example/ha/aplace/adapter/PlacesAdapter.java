package com.example.ha.aplace.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ha.aplace.R;
import com.example.ha.aplace.data.model.Place;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ha Truong on 8/20/2017.
 * This is a Aplace
 * into the com.example.ha.aplace.adapter
 */

public class PlacesAdapter extends BaseAdapter {

    private Context context;
    private List<Place> places;

    public PlacesAdapter(Context context, List<Place> places) {
        this.context = context;
        setPlaces(places);
    }

    private void setPlaces(List<Place> places){
        this.places = places;
    }

    public void updateData(List<Place> places){
        this.places = places;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int position) {
        return places.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        PlaceViewHolder viewHolder = new PlaceViewHolder();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null){
            view = layoutInflater.inflate(R.layout.item_place, parent, false);
            ButterKnife.bind(viewHolder, view);
            view.setTag(viewHolder);
        }
        viewHolder = (PlaceViewHolder) view.getTag();
        Place place = (Place) getItem(position);

        if (place.getPlaceImage() != null){
            Bitmap placeBitMap = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0 , place.getPlaceImage().length);
            viewHolder.imgPlace.setImageBitmap(placeBitMap);
        }
        viewHolder.txtPlaceName.setText(place.getPlaceName());
        viewHolder.txtPlaceAddress.setText(place.getPlaceAddress());
        viewHolder.txtPlaceDescription.setText(place.getPlaceDescription());

        return view;
    }

    class PlaceViewHolder{
        @BindView(R.id.imgItemPlace_Picture)
        ImageView imgPlace;
        @BindView(R.id.txtItemPlace_PlaceName)
        TextView txtPlaceName;
        @BindView(R.id.txtItemPlace_PlaceAddress)
        TextView txtPlaceAddress;
        @BindView(R.id.txtItemPlace_PlaceDescription)
        TextView txtPlaceDescription;
    }
}
