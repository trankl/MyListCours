package com.mylistcours_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;

import com.mylistcours_application.database.DatabaseHelper;
import com.mylistcours_application.extraFonctions.AppelToast;
import com.mylistcours_application.model.Produit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edt_nom_produit;
    EditText edt_quantite_produit;
    ListView listview_listCours;

    Produit produit;
    List<Produit> produitList= new ArrayList<>();
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fct_remplirvue();
        initialDBHelper();

        this.listview_listCours.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        this.listview_listCours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                MainActivity.this.produit = (Produit) listview_listCours.getItemAtPosition(position);
                MainActivity.this.produit.setActive(currentCheck);
                AppelToast.displayCustomToast(MainActivity.this, "select ce produit " + position +" est active = " + produit.isActive());

            }
        });

        voirTout();
    }

    public void fct_remplirvue(){
        this.edt_nom_produit =findViewById(R.id.xml_edt_nom_produit);
        this.edt_quantite_produit =findViewById(R.id.xml_edt_quantite_produit);
        this.listview_listCours =findViewById(R.id.xml_listview_listCours);
    }

    public void initialDBHelper(){
        // on creer 1 objet type DatabaseHelper pour appeler la BD afin de memoriser ce musicien
        databaseHelper = new DatabaseHelper(MainActivity.this);
    }


    public void act_ajouter_produit(View view) {
        try {
            String nom = edt_nom_produit.getText().toString();
            int quantite = Integer.parseInt(edt_quantite_produit.getText().toString());

            Produit produit = new Produit(nom, quantite);

            // on creer 1 objet type DatabaseHelper pour appeler la BD afin de memoriser ce produit
            // DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
            // on ajoute ce produit à la BD
            databaseHelper.addProduit(produit);

            AppelToast.displayCustomToast(this, "Nouveau musicien est créé.");
            voirTout();
        } catch (Exception e){
            AppelToast.displayCustomToast(this, "Erreur quand on cree nouveau musicien"+e.toString());
        }

    }

    public void act_refraiche_list(View view) {
        voirTout();
    }



    public void voirTout() {

        produitList = databaseHelper.getAllProduits();

        // ArrayAdapter a utilisé à afficher le ListView avec des ListItem simple
        // android.R.layout.simple_list_item_1 est une disposition prédéfinie constante d'Android.
        ArrayAdapter arrayAdapter_listCours = new ArrayAdapter<Produit>(this, android.R.layout.simple_list_item_checked,produitList);

        listview_listCours.setAdapter(arrayAdapter_listCours);

        AppelToast.displayCustomToast(this, "Voir la liste des cours");

       for(int i=0;i<produitList.size(); i++ )  {
            this.listview_listCours.setItemChecked(i,produitList.get(i).isActive());
        }

    }

    public void act_supprimer_select_produit(View view) {
        try{
            if(this.produit.isActive()){
                databaseHelper.removeProduit(produit.getProduit_id());
                AppelToast.displayCustomToast(this, "Supprimer ce produit car il est acheté");
                voirTout();
            } else{
                AppelToast.displayCustomToast(this, " Select le produit pour supprimer");
            }
        } catch (Exception e) {
            AppelToast.displayCustomToast(this, "ERREUR: Ne peut pas supprimer ce produit "+ e.toString());
        }

    }
}