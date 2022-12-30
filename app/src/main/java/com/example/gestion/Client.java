package com.example.gestion;

import org.json.JSONObject;

public class Client {
    private String id,nom,adresse,phone,fax,email,contact,phonecontact ;
    public  Client (JSONObject jObject) {
        this.id=jObject.optString("id");
        this.nom=jObject.optString("nom");
        this.adresse=jObject.optString("adresse");
        this.phone=jObject.optString("phone");
        this.fax=jObject.optString("fax");
        this.email=jObject.optString("email");
        this.contact=jObject.optString("contact");
        this.phonecontact=jObject.optString("phonecontact");
    }

    public String getReference() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getPhonecontact() {
        return phonecontact;
    }
}
