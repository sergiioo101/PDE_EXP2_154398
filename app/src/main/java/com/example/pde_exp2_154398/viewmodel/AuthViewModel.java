package com.example.pde_exp2_154398.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<String> errorLiveData;
    
    public AuthViewModel(Application application) {
        super(application);
        firebaseAuth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        errorLiveData = new MutableLiveData<>();
        
        // Observar cambios de autenticación
        firebaseAuth.addAuthStateListener(firebaseAuth -> {
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                userLiveData.setValue(currentUser);
            }
        });
    }
    
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.setValue(user);
                    } else {
                        if (task.getException() != null) {
                            errorLiveData.setValue(task.getException().getMessage());
                        }
                    }
                });
    }
    
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        userLiveData.setValue(user);
                    } else {
                        if (task.getException() != null) {
                            Exception exception = task.getException();
                            if (exception instanceof FirebaseAuthWeakPasswordException) {
                                FirebaseAuthWeakPasswordException weakPasswordException = 
                                    (FirebaseAuthWeakPasswordException) exception;
                                errorLiveData.setValue("La contraseña no cumple con la política de seguridad. " +
                                    "Debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas y números.");
                            } else {
                                errorLiveData.setValue(exception.getMessage());
                            }
                        }
                    }
                });
    }
    
    public void logout() {
        firebaseAuth.signOut();
        userLiveData.setValue(null);
    }
    
    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
    
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
    
    public MutableLiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
}

