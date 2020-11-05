package com.example.tallertransaccion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class listar extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    RecyclerView recyclerlista;
    ArrayList <listatransferir> listatransaccion;

    RequestQueue rq;
    JsonRequest jrq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        recyclerlista = findViewById(R.id.rvlistado);
        listatransaccion = new ArrayList<>();

        recyclerlista.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));

        recyclerlista.setHasFixedSize(true);

        rq = Volley.newRequestQueue(getApplicationContext());

        cargarWebService();


    }

    private void cargarWebService() {

        String url = "http://192.168.1.2/tallerBanco/listartransaccion.php";
        jrq = new JsonObjectRequest(Request.Method.POST,url,null,this,this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"Error en la conexion con el servidor",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONObject response) {

        listatransferir listatransferir = null;

        JSONArray json = response.optJSONArray("transaccion");

        try{

            for (int i = 0; i < json.length(); i++)
            {

                listatransferir = new listatransferir();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                listatransferir.setNrotransac(jsonObject.optString("nrotransac"));
                listatransferir.setNrocuentaorigen(jsonObject.optString("nrocuentaorigen"));
                listatransferir.setNrocuentadestino(jsonObject.optString("nrocuentadestino"));
                listatransferir.setFecha(jsonObject.optString("fecha"));
                listatransferir.setHora(jsonObject.optString("hora"));
                listatransferir.setValor(jsonObject.optString("valor"));

                listatransaccion.add(listatransferir);


                transaccionadapter adapter = new transaccionadapter(listatransaccion);

                recyclerlista.setAdapter(adapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "No se ha podido establecer conexión con el servidor" +
                    " "+response, Toast.LENGTH_LONG).show();
        }

    }
}