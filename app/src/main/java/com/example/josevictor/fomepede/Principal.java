package com.example.josevictor.fomepede;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.josevictor.fomepede.Helper.DatabaseHelper;
import com.example.josevictor.fomepede.Model.Restaurante;
import com.example.josevictor.fomepede.Utils.UtilsFront;

import java.sql.SQLException;
import java.util.List;

public class Principal extends AppCompatActivity {

    private ListView lstRestaurantes;
    private ArrayAdapter<Restaurante> listaAdapter;

    private static final int REQUEST_NOVO_RESTAURANTE    = 1;
    private static final int REQUEST_ALTERAR_RESTAURANTE = 2;

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

        registerForContextMenu(lstRestaurantes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_selecionado, menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == REQUEST_NOVO_RESTAURANTE || requestCode == REQUEST_ALTERAR_RESTAURANTE)
                && resultCode == Activity.RESULT_OK){
            popularLista();
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Restaurante restaurante = (Restaurante) this.lstRestaurantes.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.menuItemExcluir:
                excluirRestaurante(restaurante);
                break;
            case R.id.menuItemLigar:
                ligarRestaurante(restaurante);
                break;
            case R.id.menuItemAlterar:
                alterarRestaurante(restaurante);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.adicionar:
                adicionaRestaurante();
                return true;
            case R.id.menuItemInformacoes:
                mostraInfos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ligarRestaurante(final Restaurante restaurante) {
        String telefone = restaurante.getTelefone();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", telefone, null));
        startActivity(intent);
    }

    public void excluirRestaurante(final Restaurante restaurante) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Realmente deseja exluir o restaurante?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                try {
                    DatabaseHelper conexao = DatabaseHelper.getInstance(Principal.this);
                    conexao.getRestauranteDao().delete(restaurante);
                    popularLista();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void alterarRestaurante(Restaurante restaurante) {
        CadastroRestaurante.alterar(this,
                                    REQUEST_ALTERAR_RESTAURANTE,
                                    restaurante);
    }

    public void adicionaRestaurante() {
        CadastroRestaurante.novo(this, REQUEST_NOVO_RESTAURANTE);
    }

    public void mostraInfos() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
