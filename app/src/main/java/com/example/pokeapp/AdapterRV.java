package com.example.pokeapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.example.pokeapp.api.CallData;
import com.example.pokeapp.databinding.CardviewCapturedBinding;
import com.example.pokeapp.databinding.ItemPokedexBinding;
import com.example.pokeapp.model.Pokemon;
import com.example.pokeapp.model.PokemonResult;
import com.example.pokeapp.model.Utilities;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AdapterRV extends RecyclerView.Adapter<HolderRV>{

    private ArrayList<Pokemon> pokemons = null;
    private ArrayList<PokemonResult> pokemonsResult = null;
    private Fragment fragment = null;
    private onItemClickListener listener = null;
    private ViewBinding binding = null;

    public interface onItemClickListener{
        void onItemClick(Object o, int position);
    }
    // TODO: cambiar arraylist por objeto generico
    public AdapterRV(ArrayList<Pokemon> pokemons, ArrayList<PokemonResult> pokemonsResult, Fragment fragment, onItemClickListener listener) {
        if (fragment.getClass().getSimpleName().equals("PokedexFragment")){
            this.pokemonsResult = pokemonsResult;
        }
        if (fragment.getClass().getSimpleName().equals("CapturedPokemonsFragment")){
            this.pokemons = pokemons;
        }
        this.fragment = fragment;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HolderRV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: PARAMETRIZAR IF PARA USAR EN CONSTRUCTOR
        if(fragment.getClass().getSimpleName().equals("CapturedPokemonsFragment"))
            binding = CardviewCapturedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        if(fragment.getClass().getSimpleName().equals("PokedexFragment"))
            binding = ItemPokedexBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new HolderRV(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRV holder, int position) {
        if (fragment.getClass().getSimpleName().equals("PokedexFragment")){
            PokemonResult p = CallData.getListPokedex().get(position);
            holder.bind(null, p);
            holder.itemView.setOnClickListener(view -> listener.onItemClick(p, position));
        }
        if (fragment.getClass().getSimpleName().equals("CapturedPokemonsFragment")){
            Pokemon p = pokemons.get(position);
            holder.bind(p, null );
            holder.itemView.setOnClickListener(view -> listener.onItemClick(p, position));
        }
    }

    @Override
    public int getItemCount() {
        if (fragment.getClass().getSimpleName().equals("PokedexFragment"))
            return pokemonsResult.size();
        if (fragment.getClass().getSimpleName().equals("CapturedPokemonsFragment"))
            return pokemons.size();
        return 0;
    }
}

class HolderRV extends RecyclerView.ViewHolder {
    private ViewBinding binding = null;

    public HolderRV(ViewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
// TODO: cambiar pokemon por objeto generico y castear

    /**
     * Metodo para enlazar los datos con la vista.
     * @param p, objeto a enlazar.
     */
    public void bind(Pokemon p, PokemonResult pr){

        if(binding instanceof CardviewCapturedBinding){
            CardviewCapturedBinding binding = (CardviewCapturedBinding) this.binding;

            binding.cvID.setText(String.valueOf(p.getId()));
            binding.cvImage.setImageResource(R.drawable.ic_launcher_foreground);
            binding.cvName.setText(p.getName());
            binding.cvType.setText(p.getType());
            return;
        }

        if(binding instanceof ItemPokedexBinding){
            ItemPokedexBinding binding = (ItemPokedexBinding) this.binding;

            binding.imageView.setImageResource(R.drawable.ic_pokedex);
            binding.textView.setText(pr.getName());
        }
    }
}