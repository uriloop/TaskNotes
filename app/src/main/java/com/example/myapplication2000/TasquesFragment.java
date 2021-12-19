package com.example.myapplication2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;

import com.example.myapplication2000.databinding.FragmentNotesBinding;
import com.example.myapplication2000.databinding.FragmentTasquesBinding;
import com.example.myapplication2000.databinding.ViewholderNotaBinding;
import com.example.myapplication2000.databinding.ViewholderTascaBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class TasquesFragment extends Fragment {

    private FragmentTasquesBinding binding;
    TasquesViewModel tasquesViewModel;
    NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentTasquesBinding.inflate(inflater, container, false)).getRoot();

    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        tasquesViewModel = new ViewModelProvider(requireActivity()).get(TasquesViewModel.class);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        binding.irANuevaTasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nuevaTascaFragment);
            }
        });

        TasquesFragment.TasquesAdapter tasquesAdapter;
        tasquesAdapter = new TasquesFragment.TasquesAdapter();

        binding.recyclerViewT.setAdapter(tasquesAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int posicion = viewHolder.getAdapterPosition();
                Tasca tasca = tasquesAdapter.obtenerTasca(posicion);
                tasquesViewModel.eliminar(tasca);

                Snackbar snack = null;


                snack.make(view, tasca.tasca+" eliminada.", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tasquesViewModel.insertar(tasca);
                    }

                }).setBackgroundTint(getResources().getColor(R.color.purple_500,getActivity().getTheme())).setActionTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setTextColor(getResources().getColor(R.color.white,getActivity().getTheme())).setDuration(2500).show();


            }
        }).attachToRecyclerView(binding.recyclerViewT);

        obtenerTasques().observe(getViewLifecycleOwner(), new Observer<List<Tasca>>() {
            @Override
            public void onChanged(List<Tasca> tasques) {
                tasquesAdapter.establecerLista(tasques);
            }
        });
        /*binding.recyclerViewT.setAdapter(tasquesAdapter);*/


    }
    //////////   activar aixó

    LiveData<List<Tasca>> obtenerTasques(){
        return tasquesViewModel.obtener();
    }

    class TasquesAdapter extends RecyclerView.Adapter<TasquesFragment.TascaViewHolder> {

        List<Tasca> tasques;

        @NonNull
        @Override
        public TasquesFragment.TascaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TasquesFragment.TascaViewHolder(ViewholderTascaBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TasquesFragment.TascaViewHolder holder, int position) {

            Tasca tasca = tasques.get(position);



            holder.binding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    tasquesViewModel.actualizar(tasca,b);

                }
            });




            holder.binding.tasca.setText(tasca.tasca);
            holder.binding.checkBox.setChecked(tasca.check);

        }



        @Override
        public int getItemCount() {
            return tasques != null ? tasques.size() : 0;
        }

        public void establecerLista(List<Tasca> tasques){
            this.tasques = tasques;
            notifyDataSetChanged();
        }
        public Tasca obtenerTasca(int posicion){
            return tasques.get(posicion);
        }


    }

    static class TascaViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderTascaBinding binding;

        public TascaViewHolder(ViewholderTascaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
