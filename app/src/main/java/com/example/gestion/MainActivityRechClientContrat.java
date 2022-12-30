package com.example.gestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivityRechClientContrat extends AppCompatActivity {
EditText id,nomc ;
Button bvalider, bannuler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rech_client_contrat);
        id=(EditText) findViewById(R.id.edtID);
        nomc=(EditText) findViewById(R.id.edtnom);
        bvalider=(Button) findViewById(R.id.btnvalider) ;
        bannuler=(Button) findViewById(R.id.btnannuler) ;


        Intent i=getIntent();
        String ch = i.getStringExtra("nom du client");

        nomc.setText(ch);


        bannuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(MainActivityRechClientContrat.this,MainActivity3contrat.class) ;
                startActivity(i1);
            }
        });
    }
}