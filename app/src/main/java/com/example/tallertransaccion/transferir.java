package com.example.tallertransaccion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class transferir extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {


    public static final String usuario ="usuario";
    public static final String nombre = "nombre";

    TextView titularcuenta, hora, fecha,cuentaorigen, traercuenta, usuariocuenta, saldo1, cuentadestino2, traercuentadestino, saldodestino2;
    EditText  cuentadestino, valor;
    Button cerrarsesion, cancelar, transferir;

    RequestQueue rq2,rq;
    JsonObjectRequest jrq2,jrq;

    Double valor1,valor2;
    String total, total2;

    int verificadorsaldo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferir);



        titularcuenta = findViewById(R.id.tvtitular);
        cuentadestino = findViewById(R.id.etcuentadestino);
        cuentaorigen = findViewById(R.id.tvnrocuenta);
        hora = findViewById(R.id.tvhora);
        fecha = findViewById(R.id.tvfecha);
        saldo1 = findViewById(R.id.tvsaldo);
        traercuenta = findViewById(R.id.tvtraercuenta);
        usuariocuenta = findViewById(R.id.tvusuariocuenta);
        valor = findViewById(R.id.etvalor);

        cerrarsesion = findViewById(R.id.btcerrar);
        cancelar = findViewById(R.id.btcancelar);
        transferir = findViewById(R.id.bttransferir);

        saldodestino2 = findViewById(R.id.tvsaldodestino2);
        traercuentadestino = findViewById(R.id.tvtraercuentadestino);
        cuentadestino2 = findViewById(R.id.tvcuentadestino2);

        usuariocuenta.setText(getIntent().getStringExtra("usuario"));
        titularcuenta.setText(getIntent().getStringExtra("nombre"));



        rq2 = Volley.newRequestQueue(getApplicationContext());
        rq = Volley.newRequestQueue(getApplicationContext());



        // hora del dispositivo ----------
        Date hor = new Date();
        int horas = hor.getHours();
        int minutos = hor.getMinutes();
        int segundos = hor.getSeconds();
        String tiempo = horas + ":" + minutos + ":" + segundos;

        hora.setText(tiempo);

        // fecha del dispositivo ------------------------
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date fecha1 = new Date();

        String fecha2 = formatofecha.format(fecha1);

        fecha.setText(fecha2);


        traercuentadestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buscarcuentadestino();

            }
        });

        traercuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                metodotraercuenta();

            }
        });

        transferir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nurocuentaorigenm = cuentaorigen.getText().toString();
                String nurocuentadestinom = cuentadestino.getText().toString();
                String valorm = valor.getText().toString();
                String fecham = fecha.getText().toString();
                String horam = hora.getText().toString();


                if (!nurocuentadestinom.isEmpty() && !nurocuentaorigenm.isEmpty() && !valorm.isEmpty() && !fecham.isEmpty() && !horam.isEmpty())
                {

                    verificadorsaldo = (int) (Double.parseDouble(saldo1.getText().toString()) - 10000);

                    if (Double.parseDouble(valor.getText().toString())  > verificadorsaldo)
                    {
                        Toast.makeText(getApplicationContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        metodotransferir(nurocuentadestinom, nurocuentaorigenm, valorm, horam, fecham);

                        if (!cuentaorigen.getText().toString().isEmpty())
                        {
                            valor1 = Double.parseDouble(saldo1.getText().toString()) - Double.parseDouble(valor.getText().toString());
                            total = String.format(valor1.toString());

                            metodoactualizarcuentaorigen();

                        }

                        if (!cuentadestino.getText().toString().isEmpty())
                        {
                            valor2 = Double.parseDouble(saldodestino2.getText().toString()) + Double.parseDouble(valor.getText().toString());
                            total2 = String.format(valor2.toString());

                            metodoactualizarcuentadestino();
                        }
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "debe ingresar todos los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });


        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                metodocerrarsesion();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cuentadestino.setText("");
                valor.setText("");
            }
        });
    }

    private void buscarcuentadestino() {



        String url4 = "http://192.168.1.2/tallerBanco/buscarcuentadestino.php?nrocuenta="+cuentadestino.getText().toString();

        jrq = new JsonObjectRequest(Request.Method.GET,url4,null,this,this);
        rq.add(jrq);



    }


   private void metodoactualizarcuentadestino() {


        String url4 = "http://192.168.1.2/tallerBanco/actualizarcuentadestino.php?nrocuenta="+cuentadestino.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url4,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("1")) {

                            Toast.makeText(getApplicationContext(), "Transaccion registrada correctamente", Toast.LENGTH_SHORT).show();
                            cuentadestino.setText("");
                            saldo1.setText("");
                            cuentaorigen.setText("");
                            valor.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Transaccion registrada correctamente", Toast.LENGTH_SHORT).show();
                            cuentadestino.setText("");
                            saldo1.setText("");
                            cuentaorigen.setText("");
                            valor.setText("");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "transaccion incompleta ccccccccccccc", Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("nrocuenta", cuentadestino.getText().toString().trim());
                params.put("fecha", fecha.getText().toString().trim());
                params.put("saldo", total2.trim());

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

    }

     private void metodoactualizarcuentaorigen() {

        String url3 = "http://192.168.1.2/tallerBanco/actualizarcuenta.php?nrocuenta="+cuentaorigen.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url3,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("1")) {

                            Toast.makeText(getApplicationContext(), "Transaccion registrada correctamente", Toast.LENGTH_SHORT).show();
                            cuentadestino.setText("");
                            saldo1.setText("");
                            cuentaorigen.setText("");
                            valor.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "Transaccion registrada correctamente", Toast.LENGTH_SHORT).show();
                            cuentadestino.setText("");
                            saldo1.setText("");
                            cuentaorigen.setText("");
                            valor.setText("");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), "transaccion incompleta mmmmmmmmmmmmmm", Toast.LENGTH_SHORT).show();
                    }
                }

        ) {
            protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("nrocuenta", cuentaorigen.getText().toString().trim());
                    params.put("fecha", fecha.getText().toString().trim());
                    params.put("saldo", total.trim());

                    return params;
                }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

    }


    private void metodotransferir(String nurocuentadestinom, String nurocuentaorigenm, String valorm, String horam, String fecham) {


            String url = "http://192.168.1.2/tallerBanco/agregartransaccion.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("1")) {

                                Toast.makeText(getApplicationContext(), "Transaccion registrada correctamente", Toast.LENGTH_SHORT).show();
                                cuentadestino.setText("");
                                saldo1.setText("");
                                cuentaorigen.setText("");
                                valor.setText("");
                            } else {
                                Toast.makeText(getApplicationContext(), "Transaccion registrada correctamente", Toast.LENGTH_SHORT).show();

                                cuentaorigen.setText("");
                                valor.setText("");
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "transaccion incompleta yyyyyyyyy", Toast.LENGTH_SHORT).show();
                        }
                    }

            ) {
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    params.put("nrocuentaorigen", cuentaorigen.getText().toString().trim());
                    params.put("nrocuentadestino", cuentadestino.getText().toString().trim());
                    params.put("hora", hora.getText().toString().trim());
                    params.put("fecha", fecha.getText().toString().trim());
                    params.put("valor", valor.getText().toString().trim());

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);

        }


    private void metodocerrarsesion() {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));

        titularcuenta.setText("");
        cuentadestino.setText("");
        hora.setText("");
        fecha.setText("");
        saldo1.setText("");
        usuariocuenta.setText("");

    }

    private void metodotraercuenta() {

        String url2 = "http://192.168.1.2/tallerBanco/buscarcuenta.php?usuario="+usuariocuenta.getText().toString();

        jrq2 = new JsonObjectRequest(Request.Method.GET,url2,null,this,this);
        rq2.add(jrq2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inftransac = getMenuInflater();
        inftransac.inflate(R.menu.menu_transaccion, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       switch (item.getItemId())
       {

           case R.id.menulistar:
               metodolistar();
           return true;

       }

        return super.onOptionsItemSelected(item);
    }

    private void metodolistar() {

        startActivity(new Intent(getApplicationContext(),listar.class));


    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(),"Numero de cuenta destino no encontrado",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {

         if (!cuentadestino.getText().toString().isEmpty())
         {
             cuentadestino cuent2 = new cuentadestino();

             JSONArray arreglocuenta2 = response.optJSONArray("cuenta");
             JSONObject jsoncuenta2 = null;

             try
             {

                 jsoncuenta2 = arreglocuenta2.getJSONObject(0);

                 cuent2.setUsuariodestino(jsoncuenta2.optString("usuario"));
                 cuent2.setNrocuenta(jsoncuenta2.optString("nrocuenta"));
                 cuent2.setSaldodestino(jsoncuenta2.optString("saldo"));

                 cuentadestino2.setText(cuent2.getNrocuenta());
                 saldodestino2.setText(cuent2.getSaldodestino());

                 Toast.makeText(getApplicationContext(), "La cuenta si existe", Toast.LENGTH_SHORT).show();


             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }


        if (cuentaorigen.getText().toString().isEmpty())
        {
            cuenta cuent = new cuenta();

            JSONArray arreglocuenta = response.optJSONArray("cuenta");
            JSONObject jsoncuenta = null;

            try
            {

                jsoncuenta = arreglocuenta.getJSONObject(0);

                cuent.setNrocuenta(jsoncuenta.optString("nrocuenta"));
                cuent.setSaldo1(jsoncuenta.optString("saldo"));

                cuentaorigen.setText(cuent.getNrocuenta());
                saldo1.setText(cuent.getSaldo1());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
