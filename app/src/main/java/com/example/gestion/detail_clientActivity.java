package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.PrecomputedTextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class detail_clientActivity extends AppCompatActivity {
    ListView liste;
    String nom, adresse, tel, email;
    HashMap<String, String> map;
    ProgressDialog dialog;
    JSONParser parser = new JSONParser();
    int success;
    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();
    Button btn_aff , bt_ret;
    Button btn_ret;
    String s="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_client);

        bt_ret=(Button) findViewById(R.id.bt_ret) ;
        liste = (ListView)findViewById(R.id.listes);
        btn_aff = (Button) findViewById(R.id.btn_aff_det);


        btn_aff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bg_rechercher_client().execute();
            }
        });

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t = view.findViewById(R.id.casenom);
                Intent intent2=new Intent(detail_clientActivity.this,MainActivity2client.class) ;
                intent2.putExtra("XD",t.getText().toString());
                startActivity(intent2);
            }
        });
        bt_ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(detail_clientActivity.this,MainActivity2client.class);
                startActivity(i3);
            }
        });

    }



    class bg_rechercher_client extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(detail_clientActivity.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            Intent i;
            i = getIntent();
            String s=i.getStringExtra("nom") ;
            HashMap<String, String> map = new HashMap<>();
            map.put("nom",s);

            JSONObject object = parser.makeHttpRequest("http://192.168.1.12/emp/rechercherclient.php","GET",map);
            try {

                    success = object.optInt("succes");

                if (success == 1) {
                    JSONArray clients = object.getJSONArray("clients");
                    for (int p=0 ; p < clients.length(); p++) {
                        JSONObject client = clients.getJSONObject(p);
                        HashMap<String,String> j=new HashMap<>() ;
                        j.put("nom",client.getString("nom")) ;
                        j.put("adresse",client.getString("adresse")) ;
                        j.put("phone",client.getString("phone")) ;
                        j.put("email",client.getString("email")) ;

                    values.add(j);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } return null ;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.cancel();
            SimpleAdapter adapter=new SimpleAdapter(detail_clientActivity.this,values,R.layout.item,new String[] {"nom","adresse","phone","email"},new int[]{R.id.casenom,R.id.caseadresse,R.id.casetel,R.id.caseemail}) ;
            liste.setAdapter(adapter);
        }

    }



}
