package it.itsar.amazon_redo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import it.itsar.amazon_redo.fragments.CartFragment;
import it.itsar.amazon_redo.fragments.HomeFragment;
import it.itsar.amazon_redo.fragments.SearchFragment;
import it.itsar.amazon_redo.http.data.JSONProducts;
import it.itsar.amazon_redo.http.data.PostAsync;
import it.itsar.amazon_redo.http.model.Prodotto;

public class MainActivity extends AppCompatActivity {
    //link prodotti https://dummyjson.com/products

    private BottomNavigationView navbar;
    private boolean execute = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getJson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navbar = findViewById(R.id.bottomTabBar);

        navbar.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.cart:
                    configFragment(CartFragment.class);
                    break;

                case R.id.search:
                    configFragment(SearchFragment.class);
                    break;

                case R.id.home:
                    configFragment(HomeFragment.class);
                    break;
            }
            return true;
        });


    }



    private void configFragment(Class fr){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fr,null)
                .setReorderingAllowed(true)
                .addToBackStack("name")
                .commit();
    }

    private void getJson(){
        new JSONProducts().getProducts(new PostAsync() {
            @Override
            public void terminateProcess(ArrayList<Prodotto> prodotti) {
                Log.d("CONNESSO", "Connesso");
            }

            @Override
            public void failedProcess(Exception e) {
                Log.d("FAILED", "failedProcess: ");
                DialogInterface.OnClickListener listener = (dialog, which) -> {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            getJson();
                            break;
                    }
                };
                new AlertDialog.Builder(MainActivity.this).setTitle("Errore")
                        .setMessage("Errore nella connessione").setPositiveButton("Riprova",listener).show();
            }
        });
    }
}