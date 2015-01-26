package com.example.emerich.mamoyenne.Traitment;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.emerich.mamoyenne.BddPack.MyBddClass;
import com.example.emerich.mamoyenne.R;

import java.util.ArrayList;

public class AddMenu extends ActionBarActivity implements View.OnClickListener{

    int layout_val ;
    MyBddClass maBdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        /* on recupère la valeur du layout a charger
        Bundle b = getIntent().getExtras();
        layout_val = b.getInt("key");*/

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
        loadSpinner3();

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
            //maBdd.insert_note("10.5", "1", "1");

            case R.id.insertBtn:
                EditText mat = (EditText) findViewById(R.id.matiereText);
                EditText coef = (EditText) findViewById(R.id.coefText);
                maBdd.insert_matiere(mat.getText().toString(),coef.getText().toString());
                mat.setText("");
                coef.setText("");
                break;

            case R.id.delete:
                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
                maBdd.deleteMatiere(mySpinner.getSelectedItem().toString());
                mySpinner.setAdapter(null);
                loadSpinner();
                break;

            case R.id.addNote:
                Spinner mati = (Spinner) findViewById(R.id.matiereSpin);
                EditText note = (EditText) findViewById(R.id.noteText);
                maBdd.insert_note(note.getText().toString(),"1",maBdd.getMatiereId(mati.getSelectedItem().toString()));
                note.setText("");
                break;

            case R.id.delNote:
                Spinner mySpinner1 = (Spinner) findViewById(R.id.matiereSpin);
                Spinner mySpinner2 = (Spinner) findViewById(R.id.noteSpin);
                maBdd.deleteNote(mySpinner1.getSelectedItem().toString(),mySpinner2.getSelectedItem().toString());
                mySpinner1.setAdapter(null);
                mySpinner2.setAdapter(null);
                loadSpinner2();
                loadSpinner3();
                break;
        }
    }

    private void loadSpinner(){
        ArrayList<String> maListe = maBdd.selectMatiere();
        if(maListe.size() > 0){
            Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
            mySpinner.clearAnimation();
            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, maListe);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);
        }
    }
    private void loadSpinner2(){
        ArrayList<String> maListe = maBdd.selectMatiere();
        if(maListe.size() > 0){
            Spinner mySpinner = (Spinner) findViewById(R.id.matiereSpin);
            mySpinner.clearAnimation();
            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, maListe);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);
        }
    }
    private void loadSpinner3(){
        ArrayList<String> maListe = maBdd.selectNote();
        if(maListe.size() > 0){
            Spinner mySpinner = (Spinner) findViewById(R.id.noteSpin);
            mySpinner.clearAnimation();
            ArrayAdapter<String> adapter;

            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, maListe);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mySpinner.setAdapter(adapter);
        }
    }
}
