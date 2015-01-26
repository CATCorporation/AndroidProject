package com.example.emerich.mamoyenne;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.emerich.mamoyenne.BddPack.MyBddClass;
import com.example.emerich.mamoyenne.Traitment.About;
import com.example.emerich.mamoyenne.Traitment.AddMenu;
import com.example.emerich.mamoyenne.Traitment.Show;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    MyBddClass maBdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        maBdd = new MyBddClass(this);
        maBdd.openDatabase();

        maBdd.insert_matiere("maths","4");
        maBdd.insert_matiere("francais","2");
        maBdd.insert_note("10.5", "1", "1");
        maBdd.insert_note("2", "1", "2");
        maBdd.insert_note("15.5", "1", "2");
        maBdd.insert_note("8.5", "1", "1");
        maBdd.insert_note("12", "1", "1");
        maBdd.insert_note("17", "1", "1");

        // mise en place listener sur les boutons
        Button one = (Button) findViewById(R.id.add);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.show);
        two.setOnClickListener(this);
        Button three = (Button) findViewById(R.id.about);
        three.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            // bouton ajouter/supprimer cliqué
            case R.id.add:
                //changement de layout
                setContentView(R.layout.add_layout);

                // mise en place listener sur les boutons
                Button four = (Button) findViewById(R.id.addM);
                four.setOnClickListener(this);
                Button five = (Button) findViewById(R.id.addN);
                five.setOnClickListener(this);
                Button six = (Button) findViewById(R.id.main);
                six.setOnClickListener(this);
                break;

            // bouton menu principal cliqué
            case R.id.main:
                //changement de layout
                setContentView(R.layout.activity_main);

                // mise en place listener sur les boutons
                Button one = (Button) findViewById(R.id.add);
                one.setOnClickListener(this); // calling onClick() method
                Button two = (Button) findViewById(R.id.show);
                two.setOnClickListener(this);
                Button three = (Button) findViewById(R.id.about);
                three.setOnClickListener(this);
                break;

            // bouton consulter cliqué
            case R.id.show:
                Intent showIntent =
                        new Intent(this, Show.class);
                startActivity(showIntent);
                break;

            // bouton a propos cliqué
            case R.id.about:
                Intent aboutIntent =
                        new Intent(this, About.class);
                startActivity(aboutIntent);
                break;

            // bouton matiere cliqué
            case R.id.addM:
                Intent addMatiereIntent =
                        new Intent(this, AddMenu.class);
                // passage de paramètres pour identifier charger le bon layout
                Bundle b = new Bundle();
                b.putInt("key", 0);
                addMatiereIntent.putExtras(b);
                startActivity(addMatiereIntent);
                break;

            // bouton note cliqué
            case R.id.addN:
                Intent addNoteIntent =
                        new Intent(this, AddMenu.class);
                // passage de paramètres pour identifier charger le bon layout
                Bundle b2 = new Bundle();
                b2.putInt("key", 1);
                addNoteIntent.putExtras(b2);
                startActivity(addNoteIntent);
                break;

            default:
                break;
        }
    }
}
