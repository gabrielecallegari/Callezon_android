package it.itsar.amazon_redo.http.data;

import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import it.itsar.amazon_redo.http.controller.AppController;
import it.itsar.amazon_redo.http.model.Prodotto;

/*"id"
"title"
"description"
"price"
"discountPercentage"
"rating"
"stock"
"brand"
"category"
"thumbnail"
"images"
*/

public class JSONProducts {
    String url_products = "https://dummyjson.com/products";
    public static final ArrayList<Prodotto> prodotti = new ArrayList<>();
    public void getProducts(final PostAsync callback){
        prodotti.clear();

        JsonObjectRequest richiesta = new JsonObjectRequest(Request.Method.GET, url_products, null, response -> {
           try {
               JSONArray array = response.getJSONArray("products");
               for (int i = 0; i < array.length(); i++) {
                    Prodotto p = new Prodotto();
                    JSONObject ritorno = array.getJSONObject(i);
                    p.setId(ritorno.getInt("id"));
                    p.setTitle(ritorno.getString("title"));
                    p.setDescription(ritorno.getString("description"));
                    p.setPrice(ritorno.getInt("price"));
                    p.setDiscount(ritorno.getDouble("discountPercentage"));
                    p.setRating(ritorno.getDouble("rating"));
                    p.setStock(ritorno.getInt("stock"));
                    p.setBrand(ritorno.getString("brand"));
                    p.setCategory(ritorno.getString("category"));
                    p.setThumbnail(ritorno.getString("thumbnail"));

                    JSONArray images = ritorno.getJSONArray("images");
                    ArrayList<String> img = new ArrayList<>();
                    for (int j = 0; j < images.length(); j++) {
                       img.add(images.getString(j));
                    }
                    p.setImages(img);

                    prodotti.add(p);
               }
               callback.terminateProcess(prodotti);
           }catch(JSONException e){
               Log.d("ERRORE PRODOTTI", "getProducts: ");
               callback.failedProcess(e);
           }
        }, error -> {
            Log.d("Errore JSON", "Errore\n"+error);
            callback.failedProcess(error);
        });

        AppController.getInstance().addToRequestQueue(richiesta);

    }
}
