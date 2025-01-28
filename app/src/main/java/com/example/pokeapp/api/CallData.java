package com.example.pokeapp.api;

import static com.example.pokeapp.model.Pokemon.convertMapToPokemon;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.util.Log;

import com.example.pokeapp.R;
import com.example.pokeapp.model.Pokemon;
import com.example.pokeapp.model.PokemonListResponse;
import com.example.pokeapp.model.PokemonResult;
import com.example.pokeapp.model.Utilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CallData {
    public static ArrayList listPokemon = new ArrayList<>();
    private static ArrayList<Pokemon> listCaptured = new ArrayList<Pokemon>();
    private static ArrayList<PokemonResult> listPokedex = new ArrayList<PokemonResult>();
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final String base_api_url = "https://pokeapi.co/api/v2/";

    public CallData(boolean filled) {
        if (filled) {
            fillData();
        }
    }
    public interface OnCompleteListener<T> {
        void onComplete(String mensaje);
    }
    private void fillData(){
        listPokemon = new ArrayList<>();
        listPokemon.add(new Pokemon(1, "Bulbasaur", "Grass", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png", 0.7f, 6.9f));
        listPokemon.add(new Pokemon(11, "aa"));
        listPokemon.add(new Pokemon(111, "aaa"));
        listPokemon.add(new Pokemon(1111, "aaaa"));
        listPokemon.add(new Pokemon(11111, "aaaaa"));
    }

//    public ArrayList getListPokemon() {
//        return listPokemon;
//    }

    public static void addPokemonFirestore(Pokemon p, OnCompleteListener<Void> callback){
        db.collection("Users") // Colección de usuarios
                .document(auth.getCurrentUser().getUid()) // Documento del usuario actual
                .update("listPokemon", FieldValue.arrayUnion(p))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onComplete("Pokemon capturado.");
                        getPokemonsFromFirestore();
                    } else {
                        callback.onComplete("No se pudo capturar.");
                    }
                });
    }

    public static void createUserInFirestore(){
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // Si el usuario está autenticado, obtenemos su UID
            String userId = user.getUid();

            // Referencia al documento del usuario en la colección "users"
            DocumentReference userDocRef = db.collection("users").document(userId);

            // Verificamos si el documento del usuario ya existe
            userDocRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (!task.getResult().exists()) {
                        // Si el documento no existe, lo creamos con los datos del usuario
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("email", user.getEmail());
                        userData.put("name", user.getDisplayName()); // Si el nombre está disponible
                        userData.put("listPokemon", FieldValue.arrayUnion()); // Crear un array vacío para "listPokemon"

                        // Crear el documento en Firestore
                        userDocRef.set(userData)
                                .addOnSuccessListener(aVoid -> {
//                                    mensaje = "Documento de usuario creado correctamente.";
                                })
                                .addOnFailureListener(e -> {
//                                    mensaje = "Error al crear el documento de usuario";
                                });
                    } else {
//                        mensaje = "El usuario ya existe en Firestore.";
                    }
                } else {
                    // Error al obtener el documento
//                    mensaje = "Error al obtener el documento";

                }
            });
        } else {
//            mensaje = "El usuario no está autenticado.";
        }
    }

    public static void getPokedexList(){
        Retrofit rf = new Retrofit.Builder()
                .baseUrl(base_api_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService rfs = rf.create(RetrofitService.class);

        Call<PokemonListResponse> callgetlistpok = rfs.getPokemonList(2, 0);
        callgetlistpok.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonListResponse pokemonListResponse = response.body();
                    setListPokedex(pokemonListResponse.getResults());
                }

            }
            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
            }
        });
    }

    public static void getPokemonsFromFirestore(){
        db.collection("Users")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Object listPokemon = task.getResult().get("listPokemon");

                        // Verificamos que listPokemon no sea nulo y que sea una lista
//                        if (listPokemon != null && listPokemon instanceof List<?>) {
//                            List<?> pokemonList = (List<?>) listPokemon;
//                            ArrayList<Pokemon> capturedPokemons = new ArrayList<>();
//
//                            // Recorremos la lista de objetos (mapas)
//                            for (Object obj : pokemonList) {
//                                if (obj instanceof Map) {
//                                    Map<String, Object> pokemonMap = (Map<String, Object>) obj;
//
//                                    // Convertimos el mapa a un objeto Pokemon
//                                    Pokemon pokemon = convertMapToPokemon(pokemonMap);
//                                    capturedPokemons.add(pokemon);
//                                }
//                            }
//
//                            // Llamamos a setListCaptured para actualizar la lista de Pokémon capturados
//                            setListCaptured(capturedPokemons);
//                        }
                    }
                });
    }

    public static FirebaseFirestore getDb() {
        return db;
    }

    public static void setDb(FirebaseFirestore db) {
        CallData.db = db;
    }

    public static FirebaseAuth getAuth() {
        return auth;
    }

    public static void setAuth(FirebaseAuth auth) {
        CallData.auth = auth;
    }

    public static ArrayList<Pokemon> getListCaptured() {
        return listCaptured;
    }

    public static void setListCaptured(ArrayList<Pokemon> listCaptured) {
        CallData.listCaptured = listCaptured;
    }

    public static ArrayList getListPokemon() {
        return listPokemon;
    }

    public static void setListPokemon(ArrayList listPokemon) {
        CallData.listPokemon = listPokemon;
    }

    public static ArrayList<PokemonResult> getListPokedex() {
        return listPokedex;
    }

    public static void setListPokedex(ArrayList<PokemonResult> listPokedex) {
        CallData.listPokedex = listPokedex;
    }
}