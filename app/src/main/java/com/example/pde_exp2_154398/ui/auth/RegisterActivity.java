package com.example.pde_exp2_154398.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pde_exp2_154398.R;
import com.example.pde_exp2_154398.ui.main.MainActivity;
import com.example.pde_exp2_154398.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private TextView loginLink;
    private AuthViewModel authViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);
        loginLink = findViewById(R.id.textViewLogin);
        
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
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
        
        // Validación en tiempo real de la contraseña
        passwordEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validatePasswordLocal(passwordEditText.getText().toString());
            }
        });
        
        registerButton.setOnClickListener(v -> attemptRegister());
        
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
    
    private void attemptRegister() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        
        if (validateInput(email, password)) {
            // Validación local adicional
            if (validatePasswordLocal(password)) {
                authViewModel.register(email, password);
            }
        }
    }
    
    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError(getString(R.string.email_required));
            emailEditText.requestFocus();
            return false;
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError(getString(R.string.invalid_email));
            emailEditText.requestFocus();
            return false;
        }
        
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError(getString(R.string.password_required));
            passwordEditText.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private boolean validatePasswordLocal(String password) {
        if (password.length() < 8) {
            passwordEditText.setError(getString(R.string.password_policy_error));
            passwordEditText.requestFocus();
            return false;
        }
        
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }
        }
        
        if (!hasUpperCase || !hasLowerCase || !hasDigit) {
            passwordEditText.setError(getString(R.string.password_policy_error));
            passwordEditText.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void navigateToMain() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

