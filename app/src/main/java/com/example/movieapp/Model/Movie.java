package com.example.movieapp.Model;

public class Movie {
    private String poster;
    private String title;
    private Double rating;
    private String overview;
    private int quantity;

    public Movie(String poster,String title, Double rating, String overview) {
        this.poster = poster;
        this.title = title;
        this.rating = rating;
        this.overview = overview;
        this.quantity=0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
