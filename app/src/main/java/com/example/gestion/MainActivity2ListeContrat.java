package com.example.gestion;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2ListeContrat extends AppCompatActivity {
    HashMap<String, String> map1;
    String idcontrat ;
    String datedeb,datefin,redevance ;
    ProgressDialog dialog;
    JSONParser parser = new JSONParser();
    ListView  lsContrat;
    int success;
    Button baffiche,bannulerc;
    String s="";
    ArrayList<HashMap<String, String>> values = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_liste_contrat);
        baffiche=(Button) findViewById(R.id.btnaffichecontrat) ;
        bannulerc=(Button) findViewById(R.id.btnAnullerContrat);
        lsContrat=(ListView) findViewById(R.id.lst) ;

        bannulerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i8=new Intent(MainActivity2ListeContrat.this,MainActivity3contrat.class) ;
                startActivity(i8);
            }
        });

        baffiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new bg_rechercher_Contrat().execute() ; }
        });
            lsContrat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView id=view.findViewById(R.id.caseId) ;
                    Intent i2=new Intent(MainActivity2ListeContrat.this,MainActivity3contrat.class) ;
                    i2.putExtra("id a mettre ",id.getText().toString()) ;
                    startActivity(i2);



                }
            });

    }





    class bg_rechercher_Contrat extends AsyncTask<String, Void, String> {

        AlertDialog dialog;
        Context context;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new AlertDialog.Builder(MainActivity2ListeContrat.this).create();
            dialog.setTitle("Etat de connexion");
        }

        @Override
        protected String doInBackground(String... strings) {

            Intent i;
            i = getIntent();
            String s=i.getStringExtra("id a mettre ") ;
            HashMap<String, String> map1 = new HashMap<>();
            map1.put("id a mettre ",s);

            JSONObject object = parser.makeHttpRequest("http://192.168.1.12/emp/recherchecontrat.php","GET",map1);
            try {

                success = object.optInt("succes");

                if (success == 1) {
                    JSONArray contrat = object.getJSONArray("contrat");
                    for (int p=0 ; p < contrat.length(); p++) {
                        JSONObject c = contrat.getJSONObject(p);
                        HashMap<String,String> j=new HashMap<>() ;
                        j.put("idcontrat",c.getString("idcontrat")) ;
                        j.put("datedeb",c.getString("datedeb")) ;
                        j.put("datefin",c.getString("datefin")) ;
                        j.put("redevance",c.getString("redevance")) ;
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
            SimpleAdapter adapter=new SimpleAdapter(MainActivity2ListeContrat.this,values,R.layout.itemcontrat,new String[] {"id","datedeb","datefin","redevance"},new int[]{R.id.caseId,R.id.caseDatedebut,R.id.caseDatefin,R.id.redevancevalue}) ;
            lsContrat.setAdapter(adapter);
        }

    }

}