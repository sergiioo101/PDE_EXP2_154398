package com.example.pde_exp2_154398.ui.dogs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pde_exp2_154398.R;
import com.example.pde_exp2_154398.data.Dog;

import java.util.ArrayList;
import java.util.List;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.DogViewHolder> {
    private List<Dog> dogs = new ArrayList<>();
    private OnDogClickListener listener;
    private boolean isGridMode;
    
    public interface OnDogClickListener {
        void onDogClick(Dog dog);
    }
    
    public DogAdapter(OnDogClickListener listener, boolean isGridMode) {
        this.listener = listener;
        this.isGridMode = isGridMode;
    }
    
    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
        notifyDataSetChanged();
    }
    
    public void setGridMode(boolean isGridMode) {
        this.isGridMode = isGridMode;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = isGridMode ? R.layout.item_dog_grid : R.layout.item_dog_list;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new DogViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        Dog dog = dogs.get(position);
        holder.bind(dog);
    }
    
    @Override
    public int getItemCount() {
        return dogs.size();
    }
    
    class DogViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        
        public DogViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewDog);
            textViewName = itemView.findViewById(R.id.textViewDogName);
            
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onDogClick(dogs.get(position));
                }
            });
        }
        
        public void bind(Dog dog) {
            Glide.with(itemView.getContext())
                    .load(dog.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageView);
            
            if (textViewName != null) {
                textViewName.setText(dog.getName());
            }
        }
    }
}

