package com.example.pokeapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokeapp.R;
import com.example.pokeapp.activities.LogInActivity;
import com.example.pokeapp.api.CallData;
import com.example.pokeapp.databinding.FragmentSettingsBinding;
import com.example.pokeapp.model.Pokemon;
import com.example.pokeapp.model.Utilities;

public class SettingsFragment extends Fragment {


    private FragmentSettingsBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.trAbout.setOnClickListener(view1 -> {
            Utilities.showDialog(getContext(), getString(R.string.sAbout), getString(R.string.sAutor) + "\n" + getString(R.string.sVersion));
        });

        binding.trLogOut.setOnClickListener(view1 -> {
            CallData.getAuth().signOut();
            Intent intent = new Intent(requireContext(), LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish(); // Finalizar la actividad actual
        });
    }
}