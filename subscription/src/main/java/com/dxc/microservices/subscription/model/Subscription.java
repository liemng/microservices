package com.dxc.microservices.subscription.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Subscription {
    @Id
    @GeneratedValue
    private long id;
    private Date subscriptionDate = new Date();
    private int customerId;
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "subscription_movie", 
        joinColumns = @JoinColumn(name = "subscription_id"), 
        inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies = new HashSet<>();

    public Date getsubscriptionDate() {
        return subscriptionDate;
    }

    public void setsubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public boolean addMovie(Movie movie) {
        return movies.add(movie);
    }

    public boolean removeMovie(Movie movie) {
        return movies.remove(movie);
    }

    public void clearMovies() {
        movies.clear();
    }

    public long getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String toString() {
        return "Subscription for customer " + this.getCustomerId();
    }
}
