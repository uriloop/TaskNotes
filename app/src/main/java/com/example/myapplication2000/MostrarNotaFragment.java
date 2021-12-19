package com.example.myapplication2000;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication2000.databinding.FragmentMostrarNotaBinding;
import com.google.android.material.snackbar.Snackbar;


public class MostrarNotaFragment extends Fragment {
    private FragmentMostrarNotaBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMostrarNotaBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotasViewModel notasViewModel = new ViewModelProvider(requireActivity()).get(NotasViewModel.class);

        NavController navController = Navigation.findNavController(view);


        notasViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Nota>() {
            @Override
            public void onChanged(Nota nota) {

                binding.titol.setText(nota.titol);
                binding.text.setText(nota.text);
                binding.ratingBar.setRating(nota.valoracion);

                binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        if (fromUser) {
                            notasViewModel.actualizar(nota, rating);
                        }
                    }
                });


                binding.desar.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View view) {
                        boolean change = false;
                        binding.desar.setBackgroundColor(getResources().getColor(R.color.purple_500, getActivity().getTheme()));
                        if (!nota.titol.equals(binding.titol.getText().toString())) {
                            nota.titol = binding.titol.getText().toString();
                            change = true;
                        }
                        if (!nota.text.equals(binding.text.getText().toString())) {
                            nota.text = binding.text.getText().toString();
                            change = true;
                        }

                        if (nota.titol.length() < 1 && nota.text.length() < 1)
                            noGuardar(1); //mostrar error doble;
                        else if (nota.text.length() < 1) noGuardar(2);//mostrar error text;
                        else if (nota.titol.length() < 1) noGuardar(3); //mostrar error titol;
                        else if (change) {
                            notasViewModel.actualizar(nota, binding.titol.getText().toString(), binding.text.getText().toString());
                            Snackbar.make(view, "Nota: "+nota.titol+" desada.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2000).show();
                            navController.popBackStack();
                        }else{
                            Snackbar.make(view, "No has fet canvis.", Snackbar.LENGTH_LONG).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(1300).show();

                        }
                    }
                });

            }


        });
        /*binding.desar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notasViewModel.actualizar(notasViewModel.notaSeleccionada.getValue(),view.get.titol,notasViewModel.notaSeleccionada.getValue().text);
                navController.popBackStack();
            }
        });*/
    }

    public void noGuardar(int i) {
        if (i == 1) {
            binding.titol.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titol.setHint(" !");

            binding.text.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.text.setHint(" !");

        } else if (i == 2) {
            binding.text.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.text.setHint(" !");


        } else if (i == 3) {
            binding.titol.setHintTextColor(getResources().getColor(R.color.red, getActivity().getTheme()));
            binding.titol.setHint(" !");
        }
    }
}