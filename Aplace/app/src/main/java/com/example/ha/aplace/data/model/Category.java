package com.example.ha.aplace.data.model;

/**
 * Created by Ha Truong on 8/19/2017.
 */

public class Category {

    private String categroyID;
    private String categoryName;

    public Category(String categroyID, String categoryName) {
        this.categroyID = categroyID;
        this.categoryName = categoryName;
    }

    public String getCategroyID() {
        return categroyID;
    }

    public Category setCategroyID(String categroyID) {
        this.categroyID = categroyID;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }
}
