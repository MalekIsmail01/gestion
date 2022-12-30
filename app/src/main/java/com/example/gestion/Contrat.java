package com.example.gestion;

import org.json.JSONObject;

public class Contrat {

    private String idcontrat,datedeb,datefin,redevance ;
    public  Contrat (JSONObject jObject) {
        this.idcontrat = jObject.optString("idcontrat");
        this.datedeb = jObject.optString("datedeb");
        this.datefin = jObject.optString("datefin");
        this.redevance = jObject.optString("redevance");
    }

    public String geReference() {
        return idcontrat;
    }

    public String getDatedeb() {
        return datedeb;
    }

    public String getDatefin() {
        return datefin;
    }

    public String getRedevance() {
        return redevance;
    }
}
