package com.example.myapplication2000;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication2000.databinding.FragmentMostrarNotaBinding;


public class MostrarNotaFragment extends  Fragment {
    private FragmentMostrarNotaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarNotaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotasViewModel notasViewModel = new ViewModelProvider(requireActivity()).get(NotasViewModel.class);

        notasViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Nota>() {
            @Override
            public void onChanged(Nota nota) {
                binding.titol.setText(nota.titol);
                binding.text.setText(nota.text);
                binding.ratingBar.setRating(nota.valoracion);

                binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if(fromUser){
                            notasViewModel.actualizar(nota, rating);
                        }
                    }
                });
            }
        });
    }
}