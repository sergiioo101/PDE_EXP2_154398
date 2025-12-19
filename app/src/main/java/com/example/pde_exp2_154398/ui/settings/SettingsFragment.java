package com.example.pde_exp2_154398.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pde_exp2_154398.R;

public class SettingsFragment extends Fragment {
    public static final String PREFS_NAME = "app_prefs";
    public static final String KEY_VIEW_MODE = "view_mode";
    public static final String VIEW_MODE_GRID = "grid";
    public static final String VIEW_MODE_LIST = "list";
    
    private RadioGroup radioGroupViewMode;
    private SharedPreferences sharedPreferences;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        
        radioGroupViewMode = view.findViewById(R.id.radioGroupViewMode);
        
        // Cargar valor guardado
        String currentMode = sharedPreferences.getString(KEY_VIEW_MODE, VIEW_MODE_LIST);
        if (VIEW_MODE_GRID.equals(currentMode)) {
            radioGroupViewMode.check(R.id.radioButtonGrid);
        } else {
            radioGroupViewMode.check(R.id.radioButtonList);
        }
        
        // Guardar cuando cambie
        radioGroupViewMode.setOnCheckedChangeListener((group, checkedId) -> {
            String mode = checkedId == R.id.radioButtonGrid ? VIEW_MODE_GRID : VIEW_MODE_LIST;
            sharedPreferences.edit().putString(KEY_VIEW_MODE, mode).apply();
        });
    }
    
    public static String getViewMode(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_VIEW_MODE, VIEW_MODE_LIST);
    }
}

