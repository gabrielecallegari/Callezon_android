package it.itsar.amazon_redo;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import it.itsar.amazon_redo.fragments.CartFragment;
import it.itsar.amazon_redo.fragments.HomeFragment;
import it.itsar.amazon_redo.fragments.SearchFragment;
import it.itsar.amazon_redo.http.data.JSONProducts;
import it.itsar.amazon_redo.http.data.PostAsync;
import it.itsar.amazon_redo.http.model.Prodotto;
import it.itsar.amazon_redo.http.model.Profile;

public class MainActivity extends AppCompatActivity {
    //link prodotti https://dummyjson.com/products

    private ImageButton profile;

    public static boolean isLogged = false;

    private BottomNavigationView navbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getJson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navbar = findViewById(R.id.bottomTabBar);
        profile = findViewById(R.id.personal);

        profile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            launcher.launch(intent);
        });
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

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });
}