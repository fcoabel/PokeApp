package com.example.pokeapp.model;

import java.util.ArrayList;

public class PokemonListResponse{
    private int count = 0;
    private String next = "";
    private String previous = "";

    private ArrayList<PokemonResult> results = new ArrayList<PokemonResult>() {};

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<PokemonResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<PokemonResult> results) {
        this.results = results;
    }
}