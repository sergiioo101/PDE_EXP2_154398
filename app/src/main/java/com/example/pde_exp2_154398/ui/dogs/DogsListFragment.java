package com.example.pde_exp2_154398.ui.dogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pde_exp2_154398.R;
import com.example.pde_exp2_154398.data.Dog;
import com.example.pde_exp2_154398.ui.settings.SettingsFragment;
import com.example.pde_exp2_154398.viewmodel.DogViewModel;

import java.util.List;

public class DogsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private DogAdapter adapter;
    private DogViewModel viewModel;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dogs_list, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        recyclerView = view.findViewById(R.id.recyclerViewDogs);
        
        viewModel = new ViewModelProvider(this).get(DogViewModel.class);
        
        boolean isGridMode = SettingsFragment.VIEW_MODE_GRID.equals(
                SettingsFragment.getViewMode(requireContext()));
        setupRecyclerView(isGridMode);
        
        viewModel.getAllDogs().observe(getViewLifecycleOwner(), dogs -> {
            if (dogs != null && adapter != null) {
                adapter.setDogs(dogs);
            }
        });
        
        SharedPreferences prefs = requireContext().getSharedPreferences(
                SettingsFragment.PREFS_NAME, Context.MODE_PRIVATE);
        preferenceChangeListener = (sharedPreferences, key) -> {
            if (SettingsFragment.KEY_VIEW_MODE.equals(key)) {
                boolean newGridMode = SettingsFragment.VIEW_MODE_GRID.equals(
                        SettingsFragment.getViewMode(requireContext()));
                setupRecyclerView(newGridMode);
                if (adapter != null && viewModel != null) {
                    LiveData<List<Dog>> dogsLiveData = viewModel.getAllDogs();
                    if (dogsLiveData.getValue() != null) {
                        adapter.setDogs(dogsLiveData.getValue());
                    }
                }
            }
        };
        prefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
    
    private void setupRecyclerView(boolean isGridMode) {
        if (recyclerView == null) {
            return;
        }
        
        if (isGridMode) {
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
        
        adapter = new DogAdapter(dog -> {
            Bundle bundle = new Bundle();
            bundle.putInt("dogId", dog.getId());
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_dogsList_to_dogDetail, bundle);
        }, isGridMode);
        recyclerView.setAdapter(adapter);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (recyclerView != null && adapter == null && viewModel != null) {
            boolean isGridMode = SettingsFragment.VIEW_MODE_GRID.equals(
                    SettingsFragment.getViewMode(requireContext()));
            setupRecyclerView(isGridMode);
            if (viewModel.getAllDogs().getValue() != null) {
                adapter.setDogs(viewModel.getAllDogs().getValue());
            }
        }
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (preferenceChangeListener != null) {
            SharedPreferences prefs = requireContext().getSharedPreferences(
                    SettingsFragment.PREFS_NAME, Context.MODE_PRIVATE);
            prefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
        }
        adapter = null;
        recyclerView = null;
    }
}

