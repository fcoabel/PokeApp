package com.example.pokeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokeapp.AdapterRV;
import com.example.pokeapp.R;
import com.example.pokeapp.api.CallData;
import com.example.pokeapp.api.RetrofitService;
import com.example.pokeapp.databinding.FragmentPokedexBinding;
import com.example.pokeapp.model.Pokemon;
import com.example.pokeapp.model.PokemonListResponse;
import com.example.pokeapp.model.PokemonResult;
import com.example.pokeapp.model.Utilities;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding = null;
    private AdapterRV adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPokedexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: CAMBIAR LISTA Y METODO CLICK
        adapter = new AdapterRV(null, CallData.getListPokedex(), this, (p, position) -> {
            capturarPokemon((PokemonResult) p, position);
            Utilities.showSnackbarMessage(view, "Pokemon capturado", Snackbar.LENGTH_LONG);
        });
        binding.rvPokedex.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvPokedex.setAdapter(adapter);
    }
    // TODO: AÑADIR METODO CLICK AÑADIR A CAPTURADOS

    public void capturarPokemon(PokemonResult pr, int position){
        Pokemon pokemon = new Pokemon();
        pokemon.setId(position);
        pokemon.setName(pr.getName());
        CallData.addPokemonFirestore(pokemon, mensaje -> {
//            Utilities.showToastMessage(getContext(), mensaje, Snackbar.LENGTH_LONG);
        });


    }
}