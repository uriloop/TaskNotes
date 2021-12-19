package com.example.myapplication2000;


import android.annotation.SuppressLint;

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
import com.google.android.material.snackbar.Snackbar;


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

                if (titol.length()<1&&text.length()<1) noGuardar(1); //mostrar error doble;
                else if (text.length()<1) noGuardar(2);//mostrar error text;
                else if (titol.length()<1) noGuardar(3); //mostrar error titol;
                else{
                    notasViewModel.insertar(new Nota(titol, text));
                    navController.popBackStack();
                    Snackbar.make(view, "Nota: "+titol+" desada.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2000).show();

                }


            }
        });
    }
    @SuppressLint("ResourceAsColor")
    public void noGuardar(int i){
        if (i==1){
            binding.titol.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titol.setHint(" !");

            binding.text.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.text.setHint(" !");

        }else if (i==2){
            binding.text.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.text.setHint(" !");


        }else if (i==3){
            binding.titol.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titol.setHint(" !");
        }
    }
}