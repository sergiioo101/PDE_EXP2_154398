package com.example.pde_exp2_154398.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pde_exp2_154398.R;
import com.example.pde_exp2_154398.ui.main.MainActivity;
import com.example.pde_exp2_154398.viewmodel.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;
    private AuthViewModel authViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewRegister = findViewById(R.id.textViewRegister);
        
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        
        // Observar cambios de usuario
        authViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                navigateToMain();
            }
        });
        
        // Observar errores
        authViewModel.getErrorLiveData().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                textInputLayoutPassword.setError(errorMessage);
            }
        });
        
        buttonLogin.setOnClickListener(v -> attemptLogin());
        
        textViewRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        
        // Si ya hay un usuario autenticado, ir directamente a MainActivity
        if (authViewModel.getCurrentUser() != null) {
            navigateToMain();
        }
    }
    
    private void attemptLogin() {
        // Limpiar errores previos
        textInputLayoutEmail.setError(null);
        textInputLayoutPassword.setError(null);
        
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        
        if (validateInput(email, password)) {
            authViewModel.login(email, password);
        }
    }
    
    private boolean validateInput(String email, String password) {
        boolean isValid = true;
        
        if (TextUtils.isEmpty(email)) {
            textInputLayoutEmail.setError(getString(R.string.email_required));
            editTextEmail.requestFocus();
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputLayoutEmail.setError(getString(R.string.invalid_email));
            editTextEmail.requestFocus();
            isValid = false;
        }
        
        if (TextUtils.isEmpty(password)) {
            textInputLayoutPassword.setError(getString(R.string.password_required));
            editTextPassword.requestFocus();
            isValid = false;
        }
        
        return isValid;
    }
    
    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
