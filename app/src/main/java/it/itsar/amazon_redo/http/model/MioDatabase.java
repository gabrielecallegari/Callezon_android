package it.itsar.amazon_redo.http.model;

import static it.itsar.amazon_redo.MainActivity.utenteLoggato;
import static it.itsar.amazon_redo.http.model.Profile.acquistati;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.itsar.amazon_redo.http.data.DBInterface;

public class MioDatabase {

    public static ArrayList<Utente> mioDatabase = new ArrayList<>();



    public static void salvaUtentiSuDatabase(Utente utente){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        reference.add(utente).addOnSuccessListener(documentReference ->{
            Log.d("SALVATAGGIO", "Avvenuto");
        }).addOnFailureListener(e -> {
            Log.d("SALVATAGGIO", "Non avvenuto");
        });

    }

    public void leggiUtentiDaDatabase(final DBInterface callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                mioDatabase.clear();
                for(QueryDocumentSnapshot documento : task.getResult()){
                    Utente ut = documento.toObject(Utente.class);
                    ut.setId(documento.getId());
                    mioDatabase.add(ut);
                }

                callback.onSuccess();

            }else{
                callback.onFailed();
            }
        });
    }

    public void leggiAcquistiDaDatabase(final DBInterface callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("acquisti");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                acquistati.clear();
                for(QueryDocumentSnapshot documento : task.getResult()){
                    Acquisti ut = documento.toObject(Acquisti.class);
                    ut.setId(documento.getId());
                    if(ut.getUser().equals(utenteLoggato.getUsername()) && ut!=null)acquistati.add(ut);
                }

                callback.onSuccess();

            }else{
                callback.onFailed();
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

    public static void setIsLoggedDatabase(Utente ut,boolean logged){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        Map<String, Object> mappa = new HashMap<>();
        mappa.put("username",ut.getUsername());
        mappa.put("cvv",ut.getCvv());
        mappa.put("carta",ut.getCarta());
        mappa.put("islogged",logged);
        mappa.put("indirizzo",ut.getIndirizzo());
        mappa.put("password",ut.getPassword());
        mappa.put("scadenza",ut.getScadenza());
        mappa.put("id",ut.getId());
        reference.document(ut.getId()).update(mappa).addOnSuccessListener(command -> {
            Log.d("LOGOUT", "riuscito");
        }).addOnFailureListener(command -> {
            Log.d("LOGOUT", "non riuscito: ");
        });
    }
}
