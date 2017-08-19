package com.example.ha.aplace.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ha.aplace.data.model.Category;
import com.example.ha.aplace.data.model.DBUtitls;
import com.example.ha.aplace.data.model.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ha Truong on 8/19/2017.
 * This is a Aplace
 * into the com.example.ha.aplace.data
 */

public class PlaceRepo {

    private PlaceSqliteHelper placeSqliteHelper;
    private static PlaceRepo INSTANCE;

    private PlaceRepo(Context context){
        placeSqliteHelper = new PlaceSqliteHelper(context);
    }

    public static PlaceRepo getInstance(Context context){
        return (INSTANCE == null) ? new PlaceRepo(context) :  INSTANCE;
    }

    // return list categories in database
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();

        // create database through sqlitehelper to read from sqlite
        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        // create list of columns in database
        String[] columns = {
                DBUtitls.COLUMN_CATEGORY_ID,
                DBUtitls.COLUMN_CATEGORY_NAME
        };

        // excute query to database: name of category, list columns created, selection, four things after
        Cursor cursor = database.query(DBUtitls.CATEGORY_TBL_NAME, columns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_CATEGORY_ID));
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_CATEGORY_NAME));

                categories.add(new Category(categoryID, categoryName));
            }
        }

        if (cursor != null)
            cursor.close();
        database.close();
        return categories;
    }

    // return list places in database
    public List<Place> getPlaces(String cateID){
        List<Place> places = new ArrayList<>();
        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        // create list columns in database
        String[] columns = {
                DBUtitls.COLUMN_PLACE_ID,
                DBUtitls.COLUMN_PLACE_CATEGORY_ID,
                DBUtitls.COLUMN_PLACE_NAME,
                DBUtitls.COLUMN_PLACE_ADDRESS,
                DBUtitls.COLUMN_PLACE_DESCRIPTION,
                DBUtitls.COLUMN_PLACE_IMAGE,
                DBUtitls.COLUMN_PLACE_LAT,
                DBUtitls.COLUMN_PLACE_LNG
        };

        String selection = DBUtitls.COLUMN_PLACE_CATEGORY_ID + " = ?";
        String[] selectionArgs = {cateID};

        // excute query to database: name of category, list columns created, selection, four things after
        Cursor cursor = database.query(DBUtitls.PLACE_TBL_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_ID));
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_CATEGORY_ID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_NAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_DESCRIPTION));
                byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_IMAGE));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_LNG));

                Place place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setCategoryID(categoryID)
                        .setPlaceName(placeName)
                        .setPlaceAddress(placeAddress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImage(placeImage)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .build();
                places.add(place);
            }
        }

        if (cursor != null)
            cursor.close();
        database.close();
        return places;
    }

    public Place getPlace(String plID){
        Place place = null;

        SQLiteDatabase database = placeSqliteHelper.getReadableDatabase();

        // create list columns in database
        String[] columns = {
                DBUtitls.COLUMN_PLACE_ID,
                DBUtitls.COLUMN_PLACE_CATEGORY_ID,
                DBUtitls.COLUMN_PLACE_NAME,
                DBUtitls.COLUMN_PLACE_ADDRESS,
                DBUtitls.COLUMN_PLACE_DESCRIPTION,
                DBUtitls.COLUMN_PLACE_IMAGE,
                DBUtitls.COLUMN_PLACE_LAT,
                DBUtitls.COLUMN_PLACE_LNG
        };

        String selection = DBUtitls.COLUMN_PLACE_ID + " = ?";
        String[] selectionArgs = {plID};

        // excute query to database: name of category, list columns created, selection, four things after
        Cursor cursor = database.query(DBUtitls.PLACE_TBL_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
                String placeID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_ID));
                String categoryID = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_CATEGORY_ID));
                String placeName = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_NAME));
                String placeAddress = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_ADDRESS));
                String placeDescription = cursor.getString(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_DESCRIPTION));
                byte[] placeImage = cursor.getBlob(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_IMAGE));
                double placeLat = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_LAT));
                double placeLng = cursor.getDouble(cursor.getColumnIndexOrThrow(DBUtitls.COLUMN_PLACE_LNG));

                place = new Place.Builder()
                        .setPlaceID(placeID)
                        .setCategoryID(categoryID)
                        .setPlaceName(placeName)
                        .setPlaceAddress(placeAddress)
                        .setPlaceDescription(placeDescription)
                        .setPlaceImage(placeImage)
                        .setPlaceLat(placeLat)
                        .setPlaceLng(placeLng)
                        .build();
//                places.add(place);

        }

        if (cursor != null)
            cursor.close();
        database.close();

        return place;
    }

}
