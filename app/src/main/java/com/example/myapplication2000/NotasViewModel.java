package com.example.myapplication2000;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.List;

public class NotasViewModel extends AndroidViewModel {

    NotasRepositorio notasRepositorio;


    MutableLiveData<Nota> notaSeleccionada = new MutableLiveData<>();

    MutableLiveData<String> terminoBusqueda = new MutableLiveData<>();

    LiveData<List<Nota>> resultadoBusqueda = Transformations.switchMap(terminoBusqueda, new Function<String, LiveData<List<Nota>>>() {
        @Override
        public LiveData<List<Nota>> apply(String input) {
            return notasRepositorio.buscar(input);
        }
    });




    public NotasViewModel(@NonNull Application application) {
        super(application);

        notasRepositorio = new NotasRepositorio(application);
    }



/*
    LiveData<List<Nota>> obtener(){
        return notasRepositorio.obtener();
    }

    LiveData<List<Nota>> masValorados(){
        return notasRepositorio.masValorados();
    }
*/

    LiveData<List<Nota>> buscar(){
        return resultadoBusqueda;
    }

    void insertar(Nota nota){
        notasRepositorio.insertar(nota);
    }

    void eliminar(Nota nota){
        notasRepositorio.eliminar(nota);
    }

    void actualizar(Nota nota, float valoracion){
        notasRepositorio.actualizar(nota, valoracion);
    }


    void seleccionar(Nota nota){
        notaSeleccionada.setValue(nota);
    }

    MutableLiveData<Nota> seleccionado(){
        return notaSeleccionada;
    }


    public void establecerTerminoBusqueda(String s){
        terminoBusqueda.setValue(s);
    }
}