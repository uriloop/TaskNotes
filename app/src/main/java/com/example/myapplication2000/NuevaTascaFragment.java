package com.example.myapplication2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication2000.databinding.FragmentNuevaTascaBinding;


public class NuevaTascaFragment extends Fragment {

   private FragmentNuevaTascaBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return (binding = FragmentNuevaTascaBinding.inflate(inflater,container,false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TasquesViewModel tasquesViewModel=new ViewModelProvider(requireActivity()).get(TasquesViewModel.class);
        NavController navController= Navigation.findNavController(view);


        binding.crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tasca= binding.tasca.getText().toString();

                tasquesViewModel.insertar(new Tasca(tasca,false));
                navController.popBackStack();
            }
        });
    }


}