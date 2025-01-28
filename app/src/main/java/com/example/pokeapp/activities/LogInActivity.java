package com.example.pokeapp.activities;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokeapp.R;
import com.example.pokeapp.api.CallData;
import com.example.pokeapp.databinding.ActivityLoginBinding;
import com.example.pokeapp.model.Utilities;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity {

    private ActivityLoginBinding binding = null;
    private Intent intent = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CallData.getPokedexList();
        CallData.getPokemonsFromFirestore();

        binding.bSignin.setOnClickListener(view -> {
            intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            finish();
        });

        binding.bLogin.setOnClickListener(view -> {
            Utilities.closeKeyboard(view, LogInActivity.this);
            tryLogin();
        });
    }

    // TODO: IMPLEMENTAR CON GOOGLE

    public void tryLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()){
            binding.etEmail.setError(getString(R.string.eEmptyEmail));
            binding.etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError(getString(R.string.eInvalidEmail));
            binding.etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            binding.etPassword.setError(getString(R.string.eEmptyPassword));
            binding.etPassword.requestFocus();
            return;
        }

        CallData.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                FirebaseUser user = CallData.getAuth().getCurrentUser();
                Utilities.showToastMessage(getApplicationContext(), getString(R.string.sWelcome) + " " + (user.getDisplayName() != null ? user.getDisplayName() : " "), Snackbar.LENGTH_LONG);
                intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return;
            }

            if (task.getException() != null) {
                if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                    binding.etEmail.setError(getString(R.string.eUserUnknow));
                    binding.etEmail.requestFocus();
                    return;
                }
                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    binding.etPassword.setError(getString(R.string.eIncorrectPassword));
                    binding.etPassword.requestFocus();
                }
                // TODO: MOSTRAR MENSAJE DE ERROR
            }
        });
    }
}
