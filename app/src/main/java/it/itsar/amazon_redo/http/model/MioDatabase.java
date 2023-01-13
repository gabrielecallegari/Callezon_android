package it.itsar.amazon_redo.http.model;

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

    public static void salvaSuDatabase(Utente utente){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        reference.add(utente).addOnSuccessListener(documentReference ->{
            Log.d("SALVATAGGIO", "Avvenuto");
        }).addOnFailureListener(e -> {
            Log.d("SALVATAGGIO", "Non avvenuto");
        });

    }

    public void leggiDaDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");
        reference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                mioDatabase.clear();
                for(QueryDocumentSnapshot documento : task.getResult()){
                    Utente ut = documento.toObject(Utente.class);
                    Log.d("UTENTE USERNAME", ut.getUsername());
                    mioDatabase.add(ut);
                }

                if(this.dbInterface != null) dbInterface.onSuccess();

            }else{
                dbInterface.onFailed();
            }
        });

    }

    public static void setIsLoggedDatabase(String username){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reference = db.collection("users");


    }
}
