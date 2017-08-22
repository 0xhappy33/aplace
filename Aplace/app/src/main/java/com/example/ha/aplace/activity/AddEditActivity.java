package com.example.ha.aplace.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ha.aplace.ActivityUtils;
import com.example.ha.aplace.R;
import com.example.ha.aplace.data.PlaceRepo;
import com.example.ha.aplace.data.model.Place;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditActivity extends AppCompatActivity {

    @BindView(R.id.imgAddEditAct_PlacePicture)
    ImageView imagePlacePicture;
    @BindView(R.id.edtAddEditAct_PlaceName)
    EditText edtPlaceName;
    @BindView(R.id.edtAddEditAct_PlaceAddress)
    EditText edtPlaceAddress;
    @BindView(R.id.edtAddEditAct_PlaceDescription)
    EditText edtPlaceDescription;

    private String placeID;
    private String categoryID;
    private PlaceRepo placeRepo;

    private boolean hasImage = false;
    private boolean allowSave = false;

    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        ButterKnife.bind(this);
        init();
    }

    //   recive data from intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK ){
            if (data == null){
                if (placeID == null){
                    hasImage = false;
                    allowSave = false;
                }else{
                    hasImage = true;
                }
            }else{
                hasImage = true;
                allowSave = true;
                Bitmap placeImage = (Bitmap) data.getExtras().get("data");
                imagePlacePicture.setImageBitmap(placeImage);
            }
        }
    }

    private void init() {
        placeID = getIntent().getStringExtra(ActivityUtils.PLACE_KEY_PUT_EXTRA);
        categoryID = getIntent().getStringExtra(ActivityUtils.CATEGORY_KEY_PUT_EXTRA);
        placeRepo = PlaceRepo.getInstance(this);

        if (placeID != null){
            hasImage = true;
        }
    }

    @OnClick(R.id.btnAddEditAct_Save)
    public void savePlace(View view){
        String placeName = edtPlaceName.getText().toString();
        String placeAddress = edtPlaceAddress.getText().toString();
        String placeDescription = edtPlaceDescription.getText().toString();

        if (Place.validateInput(placeName, placeAddress,placeDescription,categoryID)){
            allowSave = true;
        }else{
            Toast.makeText(this, "Please fill in place's information", Toast.LENGTH_SHORT).show();
        }
        if (allowSave){
            // Them moi
            if (hasImage && placeID == null){
                Toast.makeText(this, "Add new", Toast.LENGTH_SHORT).show();
            }
            // Cap nhat
            if (placeID != null){
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick(R.id.imgAddEditAct_PlacePicture)
    public void openCameraIntent(View view){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_REQUEST_CODE);
    }
}
