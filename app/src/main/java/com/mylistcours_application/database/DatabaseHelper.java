package com.mylistcours_application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.mylistcours_application.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // @Id @GeneratedValue(strategy=GenerationType.IDENTITY )
    String table_produit = "produit";
    String f_produit_id = "produit_id";
    String f_produit_nom ="produit_nom";
    String f_produit_quantite = "produit_quantite";
    String f_produit_active = "isActive";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "mylistcours-db", null, 1);
    }

    // ce methode pour creer 1 table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement= "CREATE TABLE "+table_produit+"(" +
                f_produit_id        + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                f_produit_nom       + " VARCHAR(100) NOT NULL," +
                f_produit_quantite  + " INT NOT NULL," +
                f_produit_active    + " BOOLEAN NOT NULL" +
                ");";

        db.execSQL(createTableStatement);
    }

    // ce methode pour mettre à jour la table suite à la demande de client
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Methode pour ajouter 1 produit à bd
    public boolean addProduit(Produit produitACreer){
        boolean resultatInsertion = false;

        // ouvrir la base de donnees pour creer nouveau produit
        SQLiteDatabase ref_db = this.getWritableDatabase();

        // creer 1 contentvalue pour stocker les valeur du nouveau produit
        ContentValues contentValues_nouveauProduitAjouter = new ContentValues();

        // preparer les veleurs du nouveau produit
        contentValues_nouveauProduitAjouter.put(f_produit_nom, produitACreer.getProduit_nom());
        contentValues_nouveauProduitAjouter.put(f_produit_quantite, produitACreer.getProduit_quantite());
        contentValues_nouveauProduitAjouter.put(f_produit_active, produitACreer.isActive());

        // faire insert dans la base de donnees
        long lastInsertID =ref_db.insert(table_produit, null, contentValues_nouveauProduitAjouter );

        // CAS 1 : CAS ECHEC, Le inserted id vaut -1 cela veut dire que l'insertion n'a pas eu lieu
        if (lastInsertID ==-1){
            resultatInsertion = false;
        }
        // CAS 2 : CAS SUCCES, on positionne le flag en sortie à true
        else {
            resultatInsertion = true;
        }

        ref_db.close();
        return resultatInsertion;
    }

    public List<Produit> getAllProduits(){
        List<Produit> listProduits = new ArrayList<Produit>();

        SQLiteDatabase ref_db = this.getWritableDatabase();

        String getListProduitStatement = "SELECT * FROM " +table_produit;

        //Cette interface cursor fournit un accès en lecture-écriture aléatoire à l'ensemble de résultats renvoyé par une requête de base de données.
        Cursor cursor_resultatALaRequete = ref_db.rawQuery(getListProduitStatement, null);

        if(cursor_resultatALaRequete.moveToFirst() == true){
            do {
                int id = cursor_resultatALaRequete.getInt(0);
                String nom = cursor_resultatALaRequete.getString(1);
                int quantite = cursor_resultatALaRequete.getInt(2);
                boolean isActive = cursor_resultatALaRequete.getInt(3) == 1 ? true : false;

                Produit produit = new Produit(id, nom, quantite, isActive);

                listProduits.add(produit);
            } while (cursor_resultatALaRequete.moveToNext());
        }

        // fermer le cursor
        cursor_resultatALaRequete.close();

        // fermer la BD
        ref_db.close();

        return listProduits;
    }
}
