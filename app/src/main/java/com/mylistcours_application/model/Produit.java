package com.mylistcours_application.model;

public class Produit {
    private int produit_id;

    private String produit_nom;
    private int produit_quantite;
    private boolean isActive;



    // Contructor avec 3 parametres
    public Produit(String i_produit_nom, int i_produit_quantite, boolean i_isActive) {
        this.produit_nom = i_produit_nom;
        this.produit_quantite = i_produit_quantite;
        this.isActive = i_isActive;
    }

    // Contructor avec 4 parametres
    public Produit(int i_produit_id, String i_produit_nom, int i_produit_quantite, boolean i_isActive) {
        this.produit_id = i_produit_id;
        this.produit_nom = i_produit_nom;
        this.produit_quantite = i_produit_quantite;
        this.isActive = i_isActive;
    }

    @Override
    public String toString() {
        return produit_id + ": " + produit_nom + ", quantité =" + produit_quantite;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public String getProduit_nom() {
        return produit_nom;
    }

    public void setProduit_nom(String produit_nom) {
        this.produit_nom = produit_nom;
    }



    public int getProduit_quantite() {
        return produit_quantite;
    }

    public void setProduit_quantite(int produit_quantite) {
        this.produit_quantite = produit_quantite;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
