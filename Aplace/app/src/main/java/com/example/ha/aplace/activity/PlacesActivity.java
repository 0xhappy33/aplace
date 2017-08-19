package com.example.ha.aplace.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ha.aplace.ActivityUtils;
import com.example.ha.aplace.R;
import com.example.ha.aplace.adapter.PlacesAdapter;
import com.example.ha.aplace.data.PlaceRepo;
import com.example.ha.aplace.data.model.Place;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlacesActivity extends AppCompatActivity {

    @BindView(R.id.lvPlaceAct)
    ListView lvPlaces;
    @BindView(R.id.txtPlaceAct_NoData)
    TextView txtNoData;

    private String categoryID;
    private PlaceRepo placeRepo;
    private List<Place> places = new ArrayList<>();
    private PlacesAdapter placesAdapter;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        ButterKnife.bind(this);
        initial();
    }

    private void initial() {
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        placesAdapter = new PlacesAdapter(this, places);
        lvPlaces.setAdapter(placesAdapter);
        initialProgressDialog();
        progressDialog.show();
        getPlaces();
        onPlaceClick();
    }

    private void getPlaces(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                places = placeRepo.getPlaces(categoryID);
                //addTestData();
                if (!places.isEmpty()){
                    txtNoData.setVisibility(View.GONE);
                }
                placesAdapter.updateData(places);
                progressDialog.dismiss();
            }
        }, 4000);
    }

    private void initialProgressDialog(){
        progressDialog = new ProgressDialog(PlacesActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void onPlaceClick(){
        lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = places.get(position);
                Intent detailIntent = new Intent(PlacesActivity.this, DetailActivity.class);
                detailIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, place.getPlaceID());
                startActivity(detailIntent);
            }
        });
    }

    @OnClick(R.id.fabPlacesAct_AddNewPlace)
    public void addNewPlace(View view){
        Toast.makeText(this, "Add new", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.btnPlaceAct_ShowAllMap)
    public void showAllOnMap(View view){
        Toast.makeText(this, "Show all map", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(PlacesActivity.this, DetailActivity.class));
    }
}
