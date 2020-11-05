package com.example.tallertransaccion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class registrarusuario extends AppCompatActivity {

    EditText nombreu, claveu, usuariou, identu;
    Button registraru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarusuario);

        nombreu = findViewById(R.id.etnombreu);
        claveu = findViewById(R.id.etclaveu);
        usuariou = findViewById(R.id.etusuariou);
        identu = findViewById(R.id.etidentu);

        registraru = findViewById(R.id.btregistraru);


        registraru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String musuario = usuariou.getText().toString();
                String mnombre = nombreu.getText().toString();
                String mclave = claveu.getText().toString();
                String mid = identu.getText().toString();

                if (!musuario.isEmpty() && !mnombre.isEmpty() && !mclave.isEmpty() && !mid.isEmpty())
                {
                    metodoregistrarusuario(musuario, mnombre, mclave, mid);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void metodoregistrarusuario(final String musuario, final String mnombre, String mclave, String mid) {

        String url = "http://192.168.1.2/tallerBanco/agregarusuario.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("1")) {

                            Toast.makeText(getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            usuariou.setText("");
                            nombreu.setText("");
                            claveu.setText("");
                            identu.setText("");
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "Registro de usuario incorreto", Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuariou.getText().toString().trim());
                params.put("nombre", nombreu.getText().toString().trim());
                params.put("clave", claveu.getText().toString().trim());
                params.put("ident", identu.getText().toString().trim());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}
