package com.example.myapplication2000;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = {Nota.class,Tasca.class}, version = 2, exportSchema = false)
public abstract class ElementosBaseDeDatos extends RoomDatabase {

    public abstract NotasDao obtenerNotasDao();




    public static volatile ElementosBaseDeDatos INSTANCIA;


    public static ElementosBaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (ElementosBaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                            ElementosBaseDeDatos.class, "elementos.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCIA;
    }




    @Dao
    public
    interface NotasDao {



        @Insert
        void insertar(Nota nota);


        @Update
        void actualizar(Nota nota);



        @Delete
        void eliminar(Nota nota);



        @Query("SELECT * FROM Nota WHERE titol LIKE '%' || :d || '%' order by valoracion desc")
        LiveData<List<Nota>> buscar(String d);

        @Query("SELECT * FROM Tasca")
        LiveData<List<Tasca>> obtenerT();

        @Insert
        void insertarT(Tasca tasca);

        @Update
        void actualizarT(Tasca tasca);

        @Delete
        void eliminarT(Tasca tasca);



    }


}
