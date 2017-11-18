package com.example.josevictor.fomepede;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.josevictor.fomepede.Helper.DatabaseHelper;
import com.example.josevictor.fomepede.Model.Restaurante;
import com.example.josevictor.fomepede.Utils.UtilsFront;

import java.sql.SQLException;

public class CadastroRestaurante extends AppCompatActivity {

    public static final String MODO = "MODO";
    public static final String ID = "ID";
    public static final int NOVO = 1;
    public static final int ALTERAR = 2;

    private int modo;

    private Button btnAdicionar;
    private Button btnVoltar;
    private EditText txtNome;
    private EditText txtTelefone;
    private EditText txtCep;
    private Spinner spnEstado;
    private EditText txtCidade;
    private EditText txtEndereco;
    private EditText txtNumero;

    private Restaurante restaurante;

    public static void novo(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, CadastroRestaurante.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterar(Activity activity, int requestCode, Restaurante restaurante) {
        Intent intent = new Intent(activity, CadastroRestaurante.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(ID, restaurante.getId());
        activity.startActivityForResult(intent, ALTERAR);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_restaurante);

        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtTelefone = (EditText) findViewById(R.id.txtTelefone);
        txtCep = (EditText) findViewById(R.id.txtCep);
        txtCidade = (EditText) findViewById(R.id.txtCidade);
        txtEndereco = (EditText) findViewById(R.id.txtEndereco);
        txtNumero = (EditText) findViewById(R.id.txtNumero);
        spnEstado = (Spinner) findViewById(R.id.spnEstado);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        modo = bundle.getInt(MODO);

        if (modo == ALTERAR) {
            int id = bundle.getInt(ID);
            try {
                DatabaseHelper conexao = DatabaseHelper.getInstance(this);
                restaurante = conexao.getRestauranteDao().queryForId(id);
                txtNome.setText(restaurante.getNome());
                txtTelefone.setText(restaurante.getTelefone());
                txtCep.setText(restaurante.getCep());
                txtCidade.setText(restaurante.getCidade());
                txtEndereco.setText(restaurante.getEndereco());
                txtNumero.setText(restaurante.getNumero());
                ArrayAdapter<String> arraySpinner = (ArrayAdapter<String>) spnEstado.getAdapter();
                spnEstado.setSelection(arraySpinner.getPosition(restaurante.getEstado()));
                this.btnAdicionar.setText(R.string.alterar);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            setTitle("Alterar Restaurante");
        } else {
            restaurante = new Restaurante();

            setTitle("Novo Restaurante");
        }

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acaoAdicionar();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acaoVoltar();
            }
        });

    }

    public void acaoAdicionar() {
        String nome = txtNome.getText().toString();
        String telefone = txtTelefone.getText().toString();
        String cep = txtCep.getText().toString();
        String cidade = txtCidade.getText().toString();
        String endereco = txtEndereco.getText().toString();
        String numero = txtNumero.getText().toString();
        String estado = spnEstado.getSelectedItem().toString();

        if (nome.equals("")) {
            UtilsFront.mensagemErro(this, R.string.mensagem_erro_nome);
            return;
        }
        if (telefone.equals("")) {
            UtilsFront.mensagemErro(this, R.string.mensagem_erro_telefone);
            return;
        }

        restaurante.setNome(nome);
        restaurante.setTelefone(telefone);
        restaurante.setCep(cep);
        restaurante.setCidade(cidade);
        restaurante.setEndereco(endereco);
        restaurante.setNumero(numero);
        restaurante.setEstado(estado);

        try {
            DatabaseHelper conexao = DatabaseHelper.getInstance(this);

            if (modo == ALTERAR) {
                conexao.getRestauranteDao().update(restaurante);
            } else {
                conexao.getRestauranteDao().create(restaurante);
            }


            setResult(Activity.RESULT_OK);

            setResult(Activity.RESULT_OK);
            finish();
        } catch (SQLException e) {
            UtilsFront.mensagemErro(this, R.string.mensagem_erro_salvar);
            e.printStackTrace();
        }
    }

    public void acaoVoltar() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
