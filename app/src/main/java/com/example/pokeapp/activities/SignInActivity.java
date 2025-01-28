package com.example.pokeapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokeapp.R;
import com.example.pokeapp.api.CallData;
import com.example.pokeapp.databinding.ActivitySigninBinding;
import com.example.pokeapp.model.Utilities;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import okhttp3.internal.Util;

public class SignInActivity extends AppCompatActivity {

    private ActivitySigninBinding binding = null;
    private Intent intent = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bSignin.setOnClickListener(view -> {
            Utilities.closeKeyboard(view, SignInActivity.this);
            trySignin();
        });
    }

    // TODO: IMPLEMENTAR CON GOOGLE

    public void trySignin(){

        String username = binding.etUserName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (username.isEmpty()){
            binding.etUserName.setError(getString(R.string.eEmptyUserName));
            binding.etUserName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            binding.etEmail.setError(getString(R.string.eEmptyEmail));
            binding.etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            binding.etPassword.setError(getString(R.string.eEmptyPassword));
            binding.etPassword.requestFocus();
            return;
        }

        // TODO ARREGLAR
        CallData.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        Utilities.showSnackbarMessage(binding.getRoot(), getString(R.string.sWelcome), Snackbar.LENGTH_LONG);
//                        intent = new Intent(SignInActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                        FirebaseUser user = CallData.getAuth().getCurrentUser();
                        if (user != null) {
                            // Si el usuario está autenticado, obtenemos su UID
                            String userId = user.getUid();
                            Utilities.showDialog(this, "firestore", userId + "\n" + user.getEmail());
                            CallData.setDb(FirebaseFirestore.getInstance());
                            CallData.getDb()
                                    .collection("Users")
                                    .document(userId)
                                    .set(new HashMap<String, Object>(
                                            Map.of(
                                                    "email", user.getEmail(),
                                                    "name", username,
                                                    "listPokemon", FieldValue.arrayUnion()
                                            )
                                    ))
                                    .addOnSuccessListener(a -> {
                                        Utilities.showDialog(this, "success", "Documento creado correctamente.");
                                    })
                                    .addOnFailureListener(e -> {
                                        Utilities.showDialog(this, "error", e.getMessage());
                                    });


                        } else {
                            Utilities.showDialog(this, "firestore", "El usuario no está autenticado.");
                        }
                    }

                    if (task.getException() != null){
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
//                            Utilities.showSnackbarMessage(binding.getRoot(), getString(R.string.sUserAlreadyExists), Snackbar.LENGTH_LONG);
//                            intent = new Intent(SignInActivity.this, LogInActivity.class);
//                            startActivity(intent);
//                            finish();
                            Utilities.showDialog(this, getString(R.string.sUserAlreadyExists), getString(R.string.sLogIn));
                        }
                        if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                            binding.etPassword.setError(getString(R.string.ePasswordLength));
                            binding.etPassword.requestFocus();
                            return;
                        }

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            binding.etEmail.setError(getString(R.string.eInvalidEmail));
                            binding.etEmail.requestFocus();
                        }
                    }

                });
    }
}
