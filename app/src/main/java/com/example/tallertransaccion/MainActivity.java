package com.example.tallertransaccion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RequestQueue rq;
    JsonObjectRequest jrq;

    EditText usuario, clave;
    Button iniciar, registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usuario = findViewById(R.id.etusuario);
        clave = findViewById(R.id.etclave);

        iniciar = findViewById(R.id.btiniciar);
        registrar = findViewById(R.id.btregistrar);

        rq = Volley.newRequestQueue(getApplicationContext());  // puse getaplicationcontext ya que no esta en el fragment


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), registrarusuario.class));

            }
        });


        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                metodoiniciar();

            }
        });

    }

    private void metodoiniciar() {

        String url = "http://192.168.1.2/tallerBanco/buscarusuario.php?usuario="+usuario.getText().toString()+"&clave="+clave.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"Cliente no encontrado",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {




        // cliente

        cliente client = new cliente();

        JSONArray arreglocliente = response.optJSONArray("clientes");
        JSONObject jsoncliente = null;

        try
        {

            jsoncliente = arreglocliente.getJSONObject(0);

            client.setUsuario(jsoncliente.optString("usuario"));
            client.setNombre(jsoncliente.optString("nombre"));
            client.setClave(jsoncliente.optString("clave"));
            client.setIdent(jsoncliente.optString("ident"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intentcliente = new Intent(getApplicationContext(), transferir.class);

        intentcliente.putExtra(transferir.usuario,client.getUsuario());
        intentcliente.putExtra(transferir.nombre,client.getNombre());

        startActivity(intentcliente);

        usuario.setText("");
        clave.setText("");

    }
}