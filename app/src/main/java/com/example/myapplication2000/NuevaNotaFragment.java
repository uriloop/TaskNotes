package com.example.myapplication2000;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication2000.databinding.FragmentNuevaNotaBinding;


public class NuevaNotaFragment extends Fragment {
    private FragmentNuevaNotaBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNuevaNotaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotasViewModel notasViewModel = new ViewModelProvider(requireActivity()).get(NotasViewModel.class);
        NavController navController = Navigation.findNavController(view);

        binding.crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titol = binding.titol.getText().toString();
                String text = binding.text.getText().toString();

                notasViewModel.insertar(new Nota(titol, text));

                navController.popBackStack();
            }
        });
    }
}