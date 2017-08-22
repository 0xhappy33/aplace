package com.example.ha.aplace.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    private Place place;
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
        place = placeRepo.getPlace(categoryID,placeID);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                if (place.getPlaceImage() != null){
                    Bitmap placeBitMap = BitmapFactory.decodeByteArray(place.getPlaceImage(), 0 , place.getPlaceImage().length);
                    imgPlacePicture.setImageBitmap(placeBitMap);
                }
                edtPlaceName.setText(place.getPlaceName());
                edtPlaceAddress.setText(place.getPlaceAddress());
                edtPlaceDescription.setText(place.getPlaceDescription());
            }
        }, 4000);
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailActivity.this);
        alertDialog.setTitle(getResources().getString(R.string.text_warning));
        alertDialog.setIcon(R.drawable.ic_warning);
        alertDialog.setMessage(getResources().getString(R.string.warning_do_you_want_to_delete)
                + " '"
                + place.getPlaceName()
                + "' ? "
        );

        alertDialog.setPositiveButton(getResources().getString(R.string.text_positive),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                        // placeRepo.delete(placeID);
                    }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.text_negative),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DetailActivity.this, "No", Toast.LENGTH_SHORT).show();
                    }
        });
        alertDialog.show();

    }

    @OnClick(R.id.imgDetailAct_Edit)
    public void editPlace(View view){
        Intent detailIntent = new Intent(DetailActivity.this, AddEditActivity.class);
        detailIntent.putExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA, place.getPlaceID());
        detailIntent.putExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA, place.getCategoryID());
        startActivity(detailIntent);
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
