package com.example.register;

public class Movie {

    private String title,poster,overview;
    private Double rating;

    public Movie(String title,String poster,String overview,Double rating){
//      poster = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQuuqXX2tr6lXChezJU5w-fPf4GrjKxfSKSw&usqp=CAU";
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating() {
        return rating;
    }

}
