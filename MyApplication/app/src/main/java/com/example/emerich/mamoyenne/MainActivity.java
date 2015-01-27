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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            // bouton ajouter/supprimer cliqué
            case R.id.add:
               Intent addMatiereIntent =
                        new Intent(this, AddMenu.class);
               startActivity(addMatiereIntent);
               break;

            // bouton menu principal cliqué


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


            default:
                break;
        }
    }
}
