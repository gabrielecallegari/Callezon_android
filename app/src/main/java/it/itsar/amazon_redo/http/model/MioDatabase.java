package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.utenteLoggato;
import static it.itsar.amazon_redo.http.model.Profile.acquistati;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import it.itsar.amazon_redo.http.data.DBInterface;

public class MioDatabase {

    public static ArrayList<Utente> mioDatabase = new ArrayList<>();

    private DBInterface dbInterface;

    public void registraListener(DBInterface inter){
        dbInterface = inter;
    }

    public static void salvaUtentiSuDatabase(Utente utente){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        reference.add(utente).addOnSuccessListener(documentReference ->{
            Log.d("SALVATAGGIO", "Avvenuto");
        }).addOnFailureListener(e -> {
            Log.d("SALVATAGGIO", "Non avvenuto");
        });

    }

    public void leggiUtentiDaDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                mioDatabase.clear();
                for(QueryDocumentSnapshot documento : task.getResult()){
                    Utente ut = documento.toObject(Utente.class);
                    mioDatabase.add(ut);
                }

                if(this.dbInterface != null) dbInterface.onSuccess();

            }else{
                dbInterface.onFailed();
            }
        });
    }

    public void leggiAcquistiDaDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("acquisti");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                acquistati.clear();
                for(QueryDocumentSnapshot documento : task.getResult()){
                    Acquisti ut = documento.toObject(Acquisti.class);
                    if(ut.getUser().equals(utenteLoggato.getUsername()) && ut!=null)acquistati.add(ut);
                }

                if(this.dbInterface != null) dbInterface.onSuccess();

            }else{
                dbInterface.onFailed();
            }
        });
    }


    public static void salvaAcquistiSuDatabase(Acquisti a){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("acquisti");
        reference.add(a).addOnSuccessListener(documentReference ->{
            Log.d("SALVATAGGIO", "Avvenuto");
        }).addOnFailureListener(e -> {
            Log.d("SALVATAGGIO", "Non avvenuto");
        });

    }

    public static void setIsLoggedDatabase(String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");


    }
}
