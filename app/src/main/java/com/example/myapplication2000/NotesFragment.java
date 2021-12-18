package com.example.myapplication2000;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;


import com.example.myapplication2000.databinding.FragmentNotesBinding;
import com.example.myapplication2000.databinding.ViewholderNotaBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NotesFragment extends Fragment {
    private FragmentNotesBinding binding;
    NotasViewModel notasViewModel;
    private NavController navController;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentNotesBinding.inflate(inflater, container, false)).getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        notasViewModel = new ViewModelProvider(requireActivity()).get(NotasViewModel.class);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        binding.irANuevaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nuevaNotaFragment);
            }
        });



        NotasAdapter notasAdapter;
        notasAdapter = new NotasAdapter();




        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();



                Nota nota = notasAdapter.obtenerNota(posicion);
                notasViewModel.eliminar(nota);
                Snackbar.make(view, nota.titol+" eliminada.", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notasViewModel.insertar(nota);
                    }

                }).show();


            }
        }).attachToRecyclerView(binding.recyclerView);

        obtenerNotas().observe(getViewLifecycleOwner(), new Observer<List<Nota>>() {
            @Override
            public void onChanged(List<Nota> notas) {
                notasAdapter.establecerLista(notas);
            }
        });


        binding.recyclerView.setAdapter(notasAdapter);

        notasViewModel.establecerTerminoBusqueda("");

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                notasViewModel.establecerTerminoBusqueda(newText);
                return false;
            }
        });

    }
    //////////   activar aixó

    LiveData<List<Nota>> obtenerNotas() {
        return notasViewModel.buscar();
    }

    class NotasAdapter extends RecyclerView.Adapter<NotaViewHolder> {

        List<Nota> notas;


        @NonNull
        @Override
        public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NotaViewHolder(ViewholderNotaBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {

            Nota nota = notas.get(position);

            holder.binding.titol.setText(nota.titol);
            holder.binding.text.setText(nota.text);
            holder.binding.ratingbar.setRating(nota.valoracion);

            holder.binding.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        notasViewModel.actualizar(nota, rating);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notasViewModel.seleccionar(nota);
                    navController.navigate(R.id.action_mostrarNotaFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return notas != null ? notas.size() : 0;
        }

        public void establecerLista(List<Nota> notas) {
            this.notas = notas;
            notifyDataSetChanged();
        }

        public Nota obtenerNota(int posicion) {
            return notas.get(posicion);
        }

       /* public void setPosicion(int fromPos, int toPos) {

        }*/
    }

    static class NotaViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderNotaBinding binding;

        public NotaViewHolder(ViewholderNotaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}