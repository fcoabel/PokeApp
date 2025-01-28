package com.example.pokeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pokeapp.model.Pokemon;
import com.example.pokeapp.R;
import com.example.pokeapp.databinding.FragmentDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding = null;
    private Pokemon pokemon = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pokemon = (Pokemon) getArguments().getSerializable("pokemon");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (pokemon != null){
            binding.imgeViewDetailFragment.setImageResource(R.drawable.ic_launcher_background);
            binding.nameDetailFragment.setText(pokemon.getName());
            binding.idDetailFragment.setText(String.valueOf(pokemon.getId()));
            binding.typeDetailFragment.setText(pokemon.getType());
            binding.heightDetailFragment.setText(String.valueOf(pokemon.getHeight()));
            binding.weightDetailFragment.setText(String.valueOf(pokemon.getWeight()));
        }
    }
}