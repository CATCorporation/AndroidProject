package com.example.emerich.mamoyenne.Traitment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.*;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.emerich.mamoyenne.BddPack.MyBddClass;
import com.example.emerich.mamoyenne.R;

import java.util.ArrayList;

public class AddMenu extends ActionBarActivity implements View.OnClickListener{

    MyBddClass maBdd;
    Spinner mySpinner1 ;
    Spinner mySpinner2 ;
    Spinner mySpinner ;
    ArrayList<String> maListe = new ArrayList<String>();
    String Semestre = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        // on set la bdd
        maBdd = new MyBddClass(this);
        maBdd.openDatabase();

        Button one = (Button) findViewById(R.id.insertBtn);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.delete);
        two.setOnClickListener(this);

        loadSpinner();

        Button three = (Button) findViewById(R.id.addNote);
        three.setOnClickListener(this); // calling onClick() method
        Button four = (Button) findViewById(R.id.delNote);
        four.setOnClickListener(this);

        loadSpinner2();

        mySpinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadSpinner3();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.sm1:
                        Semestre = "1";
                        break;

                    case R.id.sm2:
                        Semestre = "2";
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        maBdd.closeDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.insertBtn:
                EditText mat = (EditText) findViewById(R.id.matiereText);
                EditText coef = (EditText) findViewById(R.id.coefText);
                maBdd.insert_matiere(mat.getText().toString(),coef.getText().toString());
                mat.setText("");
                coef.setText("");
                loadSpinner();
                loadSpinner2();
                break;

            case R.id.delete:
                maBdd.deleteMatiere(mySpinner.getSelectedItem().toString());
                mySpinner.setAdapter(null);
                loadSpinner();
                break;

            case R.id.addNote:
                EditText note = (EditText) findViewById(R.id.noteText);
                maBdd.insert_note(note.getText().toString(),Semestre,maBdd.getMatiereId(mySpinner1.getSelectedItem().toString()));
                note.setText("");
                loadSpinner3();
                break;

            case R.id.delNote:
                deleteNote();
                break;
        }
    }

    private void loadSpinner(){
        mySpinner = (Spinner) findViewById(R.id.spinner);
        maListe = maBdd.selectMatiere();

        if(maListe.size() > 0)
            maListe.add("Liste vide");

            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, maListe);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);

    }
    private void loadSpinner2(){
        maListe = maBdd.selectMatiere();

        mySpinner1 = (Spinner) findViewById(R.id.matiereSpin);

        if(maListe.size() > 0)
            maListe.add("Liste vide");


            mySpinner2 = (Spinner) findViewById(R.id.matiereSpin);
            mySpinner2.clearAnimation();
            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, maListe);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner2.setAdapter(adapter);

    }
    private void loadSpinner3(){
        maListe = maBdd.selectNote(mySpinner1.getSelectedItem().toString());

        mySpinner2 = (Spinner) findViewById(R.id.noteSpin);

        if(maListe.size() == 0)
            maListe.add("Pas de note");

            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, maListe);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner2.setAdapter(adapter);

    }

    private void deleteNote() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Your Title");

        // set dialog message
        AlertDialog.Builder builder = alertDialogBuilder
                .setMessage("Etes-vous sure de supprimer la note " + mySpinner2.getSelectedItem().toString() +
                        " pour la matière " + mySpinner1.getSelectedItem().toString() + "!")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        maBdd.deleteNote(mySpinner2.getSelectedItem().toString(), mySpinner1.getSelectedItem().toString());
                        mySpinner1.setAdapter(null);
                        mySpinner2.setAdapter(null);
                        loadSpinner2();
                        loadSpinner3();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
