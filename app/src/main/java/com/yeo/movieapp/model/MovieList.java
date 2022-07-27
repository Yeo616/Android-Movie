package com.yeo.movieapp.model;

import java.util.List;

public class MovieList {

    private int count;
    private String result;
    List<Movie> items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Movie> getItems() {
        return items;
    }

    public void setItems(List<Movie> items) {
        this.items = items;
    }
}
