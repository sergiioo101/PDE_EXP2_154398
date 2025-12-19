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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputEditText editTextEmail;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextConfirmPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private AuthViewModel authViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);
        
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
        
        // Validación en tiempo real de la contraseña
        editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && !TextUtils.isEmpty(editTextPassword.getText())) {
                validatePasswordLocal(editTextPassword.getText().toString());
            }
        });
        
        buttonRegister.setOnClickListener(v -> attemptRegister());
        
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
    
    private void attemptRegister() {
        // Limpiar errores previos
        textInputLayoutEmail.setError(null);
        textInputLayoutPassword.setError(null);
        textInputLayoutConfirmPassword.setError(null);
        
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        
        if (validateInput(email, password, confirmPassword)) {
            // Validación local adicional de password
            if (validatePasswordLocal(password)) {
                authViewModel.register(email, password);
            }
        }
    }
    
    private boolean validateInput(String email, String password, String confirmPassword) {
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
        
        if (TextUtils.isEmpty(confirmPassword)) {
            textInputLayoutConfirmPassword.setError(getString(R.string.password_required));
            editTextConfirmPassword.requestFocus();
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            textInputLayoutConfirmPassword.setError(getString(R.string.password_mismatch));
            editTextConfirmPassword.requestFocus();
            isValid = false;
        }
        
        return isValid;
    }
    
    private boolean validatePasswordLocal(String password) {
        if (password.length() < 8) {
            textInputLayoutPassword.setError(getString(R.string.password_policy_error));
            editTextPassword.requestFocus();
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
            textInputLayoutPassword.setError(getString(R.string.password_policy_error));
            editTextPassword.requestFocus();
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
