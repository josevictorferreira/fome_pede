package com.example.josevictor.fomepede;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.josevictor.fomepede.Helper.DatabaseHelper;
import com.example.josevictor.fomepede.Model.Restaurante;

import java.util.List;

public class Principal extends AppCompatActivity {

    private ListView lstRestaurantes;
    private ArrayAdapter<Restaurante> listaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        lstRestaurantes = (ListView) findViewById(R.id.lstRestaurantes);

        popularLista();

        registerForContextMenu(lstRestaurantes);
    }

    private void popularLista() {
        List<Restaurante> lista = null;

        try {
            DatabaseHelper conexao = DatabaseHelper.getInstance(this);

            lista = conexao.getRestauranteDao()
                    .queryBuilder()
                    .orderBy("nome", true)
                    .query();
        } catch (Exception e) {
            e.printStackTrace();
        }

        listaAdapter = new ArrayAdapter<Restaurante>(this,
                android.R.layout.simple_list_item_1,
                lista);

        lstRestaurantes.setAdapter(listaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.adicionar:
                adicionaRestaurante();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void adicionaRestaurante() {
        Intent intent = new Intent(this, CadastroRestaurante.class);
        startActivity(intent);
    }
}
