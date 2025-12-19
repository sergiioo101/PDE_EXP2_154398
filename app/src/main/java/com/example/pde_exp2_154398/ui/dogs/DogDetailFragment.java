package com.example.pde_exp2_154398.ui.dogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pde_exp2_154398.R;
import com.example.pde_exp2_154398.data.Dog;
import com.example.pde_exp2_154398.viewmodel.DogViewModel;

public class DogDetailFragment extends Fragment {
    private ImageView imageView;
    private TextView textViewName;
    private TextView textViewBreed;
    private TextView textViewCharacteristics;
    private DogViewModel viewModel;
    private int dogId;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dog_detail, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        imageView = view.findViewById(R.id.imageViewDogDetail);
        textViewName = view.findViewById(R.id.textViewDogNameDetail);
        textViewBreed = view.findViewById(R.id.textViewDogBreedDetail);
        textViewCharacteristics = view.findViewById(R.id.textViewDogCharacteristicsDetail);
        
        // Obtener dogId del argumento
        if (getArguments() != null) {
            dogId = getArguments().getInt("dogId", -1);
        }
        
        if (dogId == -1) {
            return;
        }
        
        viewModel = new ViewModelProvider(this).get(DogViewModel.class);
        
        viewModel.getDogById(dogId).observe(getViewLifecycleOwner(), dog -> {
            if (dog != null) {
                displayDog(dog);
            }
        });
    }
    
    private void displayDog(Dog dog) {
        Glide.with(requireContext())
                .load(dog.getImageUrl())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageView);
        
        textViewName.setText(dog.getName());
        textViewBreed.setText(dog.getBreed());
        textViewCharacteristics.setText(dog.getCharacteristics());
    }
}

