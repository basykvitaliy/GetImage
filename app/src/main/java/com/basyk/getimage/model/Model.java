package com.basyk.getimage.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Model {

    @Expose
    private List<String> data = new ArrayList<>();

    public Model() {
    }

    public Model(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


}
