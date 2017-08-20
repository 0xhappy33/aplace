package com.example.ha.aplace.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ha.aplace.ActivityUtils;
import com.example.ha.aplace.R;
import com.example.ha.aplace.data.PlaceRepo;
import com.example.ha.aplace.data.model.Place;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.imgDetailAct_PlacePicture)
    ImageView imgPlacePicture;
    @BindView(R.id.edtDetailAct_PlaceName)
    EditText edtPlaceName;
    @BindView(R.id.edtDetailAct_PlaceAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtDetailAct_PlaceDescription)
    EditText edtPlaceDescription;

    private String placeID;
    private String categoryID;
    private PlaceRepo placeRepo;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        placeID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);
        Log.d("TEST ", placeID + " " +  categoryID);
        initProgressDialog();
        setPlace();
    }

    private void setPlace(){
        //final Place place = placeRepo.getPlace(categoryID,placeID);
        final Place place = addTestData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (place.getPlaceImage() != null){
                    Bitmap placeBitMap = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0 , place.getPlaceImage().length);
                    imgPlacePicture.setImageBitmap(placeBitMap);
                }
                edtPlaceName.setText(place.getPlaceName());
                edtPlaceAddress.setText(place.getPlaceAddress());
                edtPlaceDescription.setText(place.getPlaceDescription());
            }
        }, 4000);
        progressDialog.dismiss();
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getResources().getString(R.string.text_retrieving_data));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @OnClick(R.id.imgDetailAct_Delete)
    public void deletePlace(View view){

    }

    @OnClick(R.id.imgDetailAct_Edit)
    public void editPlace(View view){

    }

    @OnClick(R.id.imgDetailAct_Direction)
    public void getDirection(View view){

    }

    public Place addTestData(){
        return new Place.Builder()
                .setPlaceID(UUID.randomUUID().toString())
                .setCategoryID(categoryID)
                .setPlaceImage(null)
                .setPlaceName("ABddd")
                .setPlaceAddress("adsads")
                .setPlaceDescription("asdasdsad")
                .setPlaceLat(0)
                .setPlaceLng(0)
                .build();
    }
}
