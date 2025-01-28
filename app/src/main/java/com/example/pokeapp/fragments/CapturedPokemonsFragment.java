package com.example.pokeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokeapp.AdapterRV;
import com.example.pokeapp.api.CallData;
import com.example.pokeapp.model.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.databinding.FragmentCapturedPokemonsBinding;
import com.example.pokeapp.model.Utilities;

public class CapturedPokemonsFragment extends Fragment {

    private FragmentCapturedPokemonsBinding binding = null;
    private AdapterRV adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo comprobar si la lista está vacio

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCapturedPokemonsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        adapter = new AdapterRV(CallData.getListCaptured(), null,this, (p, position) -> onItemClick((Pokemon) p));
//        binding.rvCaptured.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.rvCaptured.setAdapter(adapter);
    }
    /**
     * @param p Pokemon seleccionado
     * Metodo que gestiona el click en un pokemon del recyclerView
     * */
    public void onItemClick(Pokemon p){
        Bundle bundle = new Bundle();
        bundle.putSerializable("pokemon", p);
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.action_capturedPokemonsFragment_to_detailFragment, bundle);
    }
}