package com.example.pokeapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pokemon implements Serializable {
    private int id = 0;
    private String name = "";
    private String type = "";
    private String picture = "";
    private float height = 0;
    private float weight = 0;

    public Pokemon(int id, String name, String type, String picture, float height, float weight) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.picture = picture;
        this.height = height;
        this.weight = weight;
    }

    public Pokemon() {
    }

    public Pokemon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", picture='" + picture + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                '}';
    }

    public static Pokemon convertMapToPokemon(Map<String, Object> pokemonMap) {
        // Extraemos los datos del mapa
        int id = ((Long) pokemonMap.get("id")).intValue();  // Firestore usa Long para enteros grandes
        String name = (String) pokemonMap.get("name");
        String type = (String) pokemonMap.get("type");
        int height = ((Long) pokemonMap.get("height")).intValue();  // Asumimos que height es un número
        int weight = ((Long) pokemonMap.get("weight")).intValue();  // Asumimos que weight es un número
        String picture = (String) pokemonMap.get("picture");

        // Creamos un objeto Pokemon con los valores extraídos
        return new Pokemon(id, name, type, picture, height, weight);
    }
}