package com.example.gestion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

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
import java.util.List;
import java.util.Map;

public class MainActivity3contrat extends Activity implements View.OnClickListener {
    String ms="";
    EditText ID, nomClient;
    EditText edtdatedeb, edtdatefin, edtredevance, edtcontratRech,NomClient;
    TextView txtListeContrat;
    Button btRecherche, btAjout, btMiseAJ, btSupp, btSave, btAnnuler;
    Button btRechercheClient;
    ListView  lsvListeContrat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity3contrat);
        edtcontratRech = (EditText) findViewById(R.id.EditRecherche);
     //   txtListeContrat =  (TextView) findViewById(R.id.txtListeContrat);
        edtdatedeb = (EditText) findViewById(R.id.dated2);
        edtdatefin = (EditText) findViewById(R.id.datef);
        edtredevance = (EditText) findViewById(R.id.redevancevalue);
        NomClient=(EditText)findViewById(R.id.NomClient) ;
        btRecherche = (Button) findViewById(R.id.button);
        btAjout = (Button) findViewById(R.id.button4);
        btMiseAJ = (Button) findViewById(R.id.button5);
        btSupp = (Button) findViewById(R.id.button8);
        btSave = (Button) findViewById(R.id.button9);
        btAnnuler = (Button) findViewById(R.id.button10);
        btRechercheClient = (Button) findViewById(R.id.button11);
        // ID = (EditText) findViewById(R.id.editTextTextPersonName2);
        //nomClient = (EditText) findViewById(R.id.editTextTextPersonName3);

        btAjout.setOnClickListener(this);
        btMiseAJ.setOnClickListener(this);
        btSave.setOnClickListener(this);
        btAnnuler.setOnClickListener(this);
        btSupp.setOnClickListener(this);
        btRecherche.setOnClickListener(this);
        btRechercheClient.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if (view == btSave) {
            String dated = edtdatedeb.getText().toString();
            String datef = edtdatefin.getText().toString();
            String red = edtredevance.getText().toString();
            MainActivity3contrat.bg_ajoutcontrat bg = new MainActivity3contrat.bg_ajoutcontrat(MainActivity3contrat.this);
            bg.execute(dated,datef,red);
            btRechercheClient.setEnabled(false);

        }
        if(view==btRecherche){


            Intent i4=new Intent(MainActivity3contrat.this,detail_clientActivity.class);
            i4.putExtra("id a mettre ",edtcontratRech.getText().toString()) ;
            startActivity(i4);

        }
        if(view==btRechercheClient) {

            Intent i1=new Intent(getApplicationContext(),MainActivity2ListeContrat.class) ;
            i1.putExtra("nom case",NomClient.getText().toString()) ;

            startActivity(i1);


        }
        if (view == btAnnuler) {
            edtdatedeb.setText("");
            edtdatefin.setText("");
            edtredevance.setText("");

        }

        if (view == btMiseAJ) {
            String dated = edtdatedeb.getText().toString();
            String datef = edtdatefin.getText().toString();
            String red = edtredevance.getText().toString();

            MainActivity3contrat.bg_miseajourcontrat bg = new MainActivity3contrat.bg_miseajourcontrat(MainActivity3contrat.this);
            bg.execute(dated,datef,red);

        }

        if (view == btAjout) {

            btRecherche.setEnabled(false);
            btSupp.setEnabled(false);
            btMiseAJ.setEnabled(false);
            btAjout.setEnabled(false);
            btRechercheClient.setEnabled(false);

        }
        if (view == btSupp) {
            String dated = edtdatedeb.getText().toString();
            String datef = edtdatefin.getText().toString();
            String red = edtredevance.getText().toString();

            MainActivity3contrat.bg_suupprimercontrat bg = new MainActivity3contrat.bg_suupprimercontrat(MainActivity3contrat.this);
            bg.execute(dated,datef,red);

        }
         /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 5) {
                if (resultCode == RESULT_OK) {
                    String result = data.getStringExtra("result");
                    nomClient.setText(result);
                }
        }   } */

        if (view == btRechercheClient) {
            Intent intent = new Intent(MainActivity3contrat.this,MainActivityRechClientContrat.class);
           String Nom = NomClient.getText().toString();
           intent.putExtra("nom du client", Nom);
            //startActivityForResult(intent, 5);
            // Toast.makeText(this, "client existant ", Toast.LENGTH_SHORT).show();

              startActivity(intent);




        }

    }

    private class bg_suupprimercontrat extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public bg_suupprimercontrat(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity3contrat.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String strdatefin = edtdatefin.getText().toString();
            String strredevance = edtredevance.getText().toString();
            String strid=edtcontratRech.getText().toString();
            String strdatdeb=edtdatedeb.getText().toString();

            String connstr = "http://192.168.1.95/emp/supprimecontrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("idcontrat", "UTF-8") + "=" + URLEncoder.encode(strid, "UTF-8");

                writer.write(data);
                Log.v("MainActivity3contrat", data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.setMessage(s);
            dialog.show();
            if (s.contains("succes de suppression ")) {
                Toast.makeText(context, "contrat supprimer avec succès.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Problème de suppression  .", Toast.LENGTH_LONG).show();
            }
        }
    }




    private class bg_miseajourcontrat extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public bg_miseajourcontrat(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity3contrat.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String strdatefin = edtdatefin.getText().toString();
            String strredevance = edtredevance.getText().toString();
            String strid=edtcontratRech.getText().toString();
            String strdatdeb=edtdatedeb.getText().toString();

            String connstr = "http://192.168.1.95/emp/miseajourcontrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("idcontrat", "UTF-8") + "=" + URLEncoder.encode(strid, "UTF-8")+"&&"+
                        URLEncoder.encode("datedeb", "UTF-8") + "=" + URLEncoder.encode(strdatdeb, "UTF-8") +"&&"+
                        URLEncoder.encode("datefin", "UTF-8") + "=" + URLEncoder.encode(strdatefin, "UTF-8") + "&&" +
                        URLEncoder.encode("redevance", "UTF-8") + "=" + URLEncoder.encode(strredevance, "UTF-8") ;

                writer.write(data);
                Log.v("MainActivity3contrat", data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.setMessage(s);
            dialog.show();
            if (s.contains("succes de mis a jour ")) {
                Toast.makeText(context, "contrat mis a jour avec succès.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Problème de mis a jour .", Toast.LENGTH_LONG).show();
            }
        }
    }









private class bg_ajoutcontrat extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public bg_ajoutcontrat(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity3contrat.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String strdatedeb = edtdatedeb.getText().toString();
            String strdatefin = edtdatefin.getText().toString();
            String strredevance = edtredevance.getText().toString();


            String connstr = "http://192.168.1.95/emp/ajoutcontrat.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("datedeb", "UTF-8") + "=" + URLEncoder.encode(strdatedeb, "UTF-8") + "&&" +
                        URLEncoder.encode("datefin", "UTF-8") + "=" + URLEncoder.encode(strdatefin, "UTF-8") + "&&" +
                        URLEncoder.encode("redevance", "UTF-8") + "=" + URLEncoder.encode(strredevance, "UTF-8");

                writer.write(data);
                Log.v("MainActivity3contrat", data);
                writer.flush();
                writer.close();
                InputStream ips = http.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
                String ligne = "";
                while ((ligne = reader.readLine()) != null) {
                    result += ligne;
                }
                reader.close();
                ips.close();
                http.disconnect();

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.setMessage(s);
            dialog.show();
            if (s.contains("succes d'insertion")) {
                Toast.makeText(context, "contrat insérer avec succès.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Problème d'insertion .", Toast.LENGTH_LONG).show();
            }
        }
    }







}



