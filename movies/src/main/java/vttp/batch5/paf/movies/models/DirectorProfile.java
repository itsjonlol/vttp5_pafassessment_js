package vttp.batch5.paf.movies.models;

import java.math.BigDecimal;

public class DirectorProfile {
    String director_name;
    Integer movies_count;
    BigDecimal total_revenue;
    BigDecimal total_budget;
    BigDecimal margin;


    public String getDirector_name() {
        return director_name;
    }


    public void setDirector_name(String director_name) {
        this.director_name = director_name;
    }


    public Integer getMovies_count() {
        return movies_count;
    }


    public void setMovies_count(Integer movies_count) {
        this.movies_count = movies_count;
    }


    public BigDecimal getTotal_revenue() {
        return total_revenue;
    }


    public void setTotal_revenue(BigDecimal total_revenue) {
        this.total_revenue = total_revenue;
    }


    public BigDecimal getTotal_budget() {
        return total_budget;
    }


    public void setTotal_budget(BigDecimal total_budget) {
        this.total_budget = total_budget;
    }


    public BigDecimal getMargin() {
        return margin;
    }


    public void setMargin(BigDecimal margin) {
        this.margin = margin;
    }


   
    

    

}
