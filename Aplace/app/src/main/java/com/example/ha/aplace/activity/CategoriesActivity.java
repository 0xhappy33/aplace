package com.example.ha.aplace.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ha.aplace.R;
import com.example.ha.aplace.data.PlaceRepo;
import com.example.ha.aplace.data.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ha.aplace.ActivityUtils.CATEGORY_KEY_PUT_EXTRA;

public class CategoriesActivity extends AppCompatActivity {
    @BindView(R.id.txtCategoriesAct_Restaurant)
    TextView txtRestaurant;
    @BindView(R.id.txtCategoriesAct_Cinema)
    TextView txtCinema;
    @BindView(R.id.txtCategoriesAct_Fashion)
    TextView txtFashion;
    @BindView(R.id.txtCategoriesAct_Atm)
    TextView txtAtm;

    @BindView(R.id.layoutCategoriesAct_Restaurant)
    ConstraintLayout layoutRestaurant;
    @BindView(R.id.layoutCategoriesAct_Cinema)
    ConstraintLayout layoutCinema;
    @BindView(R.id.layoutCategoriesAct_Fashion)
    ConstraintLayout layoutFashion;
    @BindView(R.id.layoutCategoriesAct_Atm)
    ConstraintLayout layoutAtm;


    private PlaceRepo placeRepo;
    private List<Category> categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        layoutRestaurant = (ConstraintLayout) findViewById(R.id.layoutCategoriesAct_Restaurant);
        layoutRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CategoriesActivity.this, "Hello world", Toast.LENGTH_SHORT).show();
            }
        });

        ButterKnife.bind(this);
        init();
    }

    public void init(){
        placeRepo = PlaceRepo.getInstance(this);
        categories = placeRepo.getCategories();
        //Log.d("Test", String.valueOf(categories.size()));
        setCategories();
    }

    private void startPlacesAct(String categoryID){
        Intent placeActIntent = new Intent(CategoriesActivity.this,PlacesActivity.class);
        placeActIntent.putExtra(CATEGORY_KEY_PUT_EXTRA, categoryID);
        startActivity(placeActIntent);
    }

    private void setCategories(){
        txtRestaurant.setText(categories.get(0).getCategoryName());
        txtCinema.setText(categories.get(1).getCategoryName());
        txtFashion.setText(categories.get(2).getCategoryName());
        txtAtm.setText(categories.get(3).getCategoryName());
    }

    @OnClick(R.id.layoutCategoriesAct_Restaurant)
    public void clickOnRestaurant(View view){
        String categoryID = categories.get(0).getCategroyID();
        startPlacesAct(categoryID);
    }

    @OnClick(R.id.layoutCategoriesAct_Cinema)
    public void clickOnCinema(View view){
        String categoryID = categories.get(1).getCategroyID();
        startPlacesAct(categoryID);
    }

    @OnClick(R.id.layoutCategoriesAct_Fashion)
    public void clickOnFashion(View view){
        String categoryID = categories.get(2).getCategroyID();
        startPlacesAct(categoryID);
    }

    @OnClick(R.id.layoutCategoriesAct_Atm)
    public void clickOnAtm(View view){
        String categoryID = categories.get(3).getCategroyID();
        startPlacesAct(categoryID);
    }
}
