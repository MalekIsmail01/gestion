package com.example.gestion;

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
import android.widget.ImageButton;
import android.widget.Toast;

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

public class MainActivity2client extends Activity implements View.OnClickListener {
    Button bAjout, bM, bS, saveBtn, cancelBtn, bR;
    EditText txtR;
    EditText edtnom, edtadresse, edttel, edtfax, edtemail, edtcontact, edttelcontact,EditRecherche;
    ImageButton nextone, prevone, lastone, firstone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2client);
        firstone = (ImageButton) findViewById(R.id.btn_firstone);
        lastone = (ImageButton) findViewById(R.id.btn_Lastone);
        nextone = (ImageButton) findViewById(R.id.btn_Nextone);
        prevone = (ImageButton) findViewById(R.id.btn_prevone);
        edtnom = (EditText) findViewById(R.id.nom);
        edtadresse = (EditText) findViewById(R.id.adresse);
        edttel = (EditText) findViewById(R.id.tel);
        edtfax = (EditText) findViewById(R.id.fax);
        edtemail = (EditText) findViewById(R.id.mail);
        edtcontact = (EditText) findViewById(R.id.contact);
        edttelcontact = (EditText) findViewById(R.id.telc);
        bAjout = (Button) findViewById(R.id.button2);
        bM = (Button) findViewById(R.id.button3);
        bS = (Button) findViewById(R.id.button4);
        txtR = (EditText) findViewById(R.id.EditRecherche);
        saveBtn = (Button) findViewById(R.id.button6);
        cancelBtn = (Button) findViewById(R.id.button7);
        bR = (Button) findViewById(R.id.btnRecherche);

        bAjout.setOnClickListener(this);
        bM.setOnClickListener(this);
        bS.setOnClickListener(this);
        bR.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        firstone.setOnClickListener(this);
        prevone.setOnClickListener(this);
        nextone.setOnClickListener(this);
        lastone.setOnClickListener(this);

        Intent i2=getIntent();
        String ch = i2.getStringExtra("XD");
        txtR.setText(ch);

    }

    @Override
    public void onClick(View view) {

        if (view == bAjout) {
            cancelBtn.setEnabled(false);
            bR.setEnabled(false);
            bS.setEnabled(false);
            nextone.setEnabled(false);
            prevone.setEnabled(false);
            lastone.setEnabled(false);
            firstone.setEnabled(false);
            bM.setEnabled(false);

        }



        if (view == bR) {

            Intent i=new Intent(getApplicationContext(),detail_clientActivity.class);
            i.putExtra("nom",txtR.getText().toString());
             startActivity(i);

        }

        if (view == cancelBtn) {
            edtnom.setText("");
            edtadresse.setText("");
            edttel.setText("");
            edtfax.setText("");
            edtemail.setText("");
            edtcontact.setText("");
            edttelcontact.setText("");
        }
        if (view == saveBtn) {
            String nom = edtnom.getText().toString();
            String adresse = edtadresse.getText().toString();
            String contact = edtcontact.getText().toString();
            String contactphone = edttelcontact.getText().toString();
            String fax = edtfax.getText().toString();
            String email = edtemail.getText().toString();
            String phone = edttel.getText().toString();
            bg_insertion_client bg = new bg_insertion_client(MainActivity2client.this);
            bg.execute(nom, adresse, contact, contactphone, fax, email, phone);
            bR.setEnabled(true);
            bS.setEnabled(true);
            nextone.setEnabled(true);
            prevone.setEnabled(true);
            lastone.setEnabled(true);
            firstone.setEnabled(true);
            bM.setEnabled(true);
            bAjout.setEnabled(true);
            cancelBtn.setEnabled(true);
        }
        if (view == bM) {
            String nom = edtnom.getText().toString();
            String adresse = edtadresse.getText().toString();
            String contact = edtcontact.getText().toString();
            String contactphone = edttelcontact.getText().toString();
            String fax = edtfax.getText().toString();
            String email = edtemail.getText().toString();
            String phone = edttel.getText().toString();


            bg_miseajour_client bg = new bg_miseajour_client(MainActivity2client.this);
            bg.execute(nom, adresse, contact, contactphone, fax, email, phone);
        }

        if (view == bS) {
            String nom = edtnom.getText().toString();
            String adresse = edtadresse.getText().toString();
            String contact = edtcontact.getText().toString();
            String contactphone = edttelcontact.getText().toString();
            String fax = edtfax.getText().toString();
            String email = edtemail.getText().toString();
            String phone = edttel.getText().toString();

            bg_supprimer_client bg = new bg_supprimer_client(MainActivity2client.this);
            bg.execute(nom, adresse, contact, contactphone, fax, email, phone);


        }


    }

    private class bg_supprimer_client extends AsyncTask<String, Void, String> {

            AlertDialog dialog;
            Context context;

            public  bg_supprimer_client(Context context) {

                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = new AlertDialog.Builder(MainActivity2client.this).create();
                dialog.setTitle("Etat de connexion");
            }

            @Override
            protected String doInBackground(String... strings) {

                String result = "";

                String strnom = edtnom.getText().toString();
                String stradresse = edtadresse.getText().toString();
                String strphone = edttel.getText().toString();
                String strfax = edtfax.getText().toString();
                String stremail = edtemail.getText().toString();
                String strcontact = edtcontact.getText().toString();
                String strphonecontact = edttelcontact.getText().toString();

                String connstr = "http://192.168.1.12/emp/supprimerClient.php";
                try {
                    URL url = new URL(connstr);
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("POST");
                    http.setDoInput(true);
                    http.setDoOutput(true);
                    OutputStream ops = http.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(stremail, "UTF-8") ;

                    writer.write(data);
                    Log.v("MainActivity2client", data);
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
                if (s.contains("succes suppression ")) {
                    Toast.makeText(context, "Client supprimer avec succès.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Problème de suppression .", Toast.LENGTH_LONG).show();
                }
            }
        }



        private class  bg_insertion_client extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public  bg_insertion_client(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity2client.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String strnom = edtnom.getText().toString();
            String stradresse = edtadresse.getText().toString();
            String strphone = edttel.getText().toString();
            String strfax = edtfax.getText().toString();
            String stremail = edtemail.getText().toString();
            String strcontact = edtcontact.getText().toString();
            String strphonecontact = edttelcontact.getText().toString();

            String connstr = "http://192.168.1.12/emp/ajoutC.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(strnom, "UTF-8") + "&&" +
                        URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(stradresse, "UTF-8") + "&&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(strphone, "UTF-8") + "&&" +
                        URLEncoder.encode("fax", "UTF-8") + "=" + URLEncoder.encode(strfax, "UTF-8") + "&&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(stremail, "UTF-8") + "&&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(strcontact, "UTF-8") + "&&" +
                        URLEncoder.encode("phonecontact", "UTF-8") + "=" + URLEncoder.encode(strphonecontact, "UTF-8");

                writer.write(data);
                Log.v("MainActivity2client", data);
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
            if (s.contains("succes insertion")) {
                Toast.makeText(context, "Client inséré avec succès.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Problème d'insertion.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class bg_miseajour_client extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;

        public  bg_miseajour_client(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity2client.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";

            String strnom = edtnom.getText().toString();
            String stradresse = edtadresse.getText().toString();
            String strphone = edttel.getText().toString();
            String strfax = edtfax.getText().toString();
            String stremail = edtemail.getText().toString();
            String strcontact = edtcontact.getText().toString();
            String strphonecontact = edttelcontact.getText().toString();

            String connstr = "http://192.168.1.12/emp/MiseAJourClient.php";
            try {
                URL url = new URL(connstr);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoInput(true);
                http.setDoOutput(true);
                OutputStream ops = http.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
                String data = URLEncoder.encode("nom", "UTF-8") + "=" + URLEncoder.encode(strnom, "UTF-8") + "&&" +
                        URLEncoder.encode("adresse", "UTF-8") + "=" + URLEncoder.encode(stradresse, "UTF-8") + "&&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(strphone, "UTF-8") + "&&" +
                        URLEncoder.encode("fax", "UTF-8") + "=" + URLEncoder.encode(strfax, "UTF-8") + "&&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(stremail, "UTF-8") + "&&" +
                        URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(strcontact, "UTF-8") + "&&" +
                        URLEncoder.encode("phonecontact", "UTF-8") + "=" + URLEncoder.encode(strphonecontact, "UTF-8");

                writer.write(data);
                Log.v("MainActivity2client", data);
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
            if (s.contains("succes de mis a jour")) {
                Toast.makeText(context, "Client mis a jour avec succès.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Problème de mis a jour .", Toast.LENGTH_LONG).show();
            }
        }
    }


}