package com.example.josevictor.fomepede;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.josevictor.fomepede.Helper.DatabaseHelper;
import com.example.josevictor.fomepede.Model.Restaurante;
import com.example.josevictor.fomepede.Utils.UtilsFront;

import java.sql.SQLException;

public class CadastroRestaurante extends AppCompatActivity {

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

        restaurante = new Restaurante();
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

            conexao.getRestauranteDao().create(restaurante);

            setResult(Activity.RESULT_OK);

            finish();
        } catch (SQLException e) {
            UtilsFront.mensagemErro(this, R.string.mensagem_erro_salvar);
            e.printStackTrace();
        }
    }

    public void acaoLimparCampos() {
        txtNome.setText("");
        txtTelefone.setText("");
        txtCep.setText("");
        txtCidade.setText("");
        txtEndereco.setText("");
        txtNumero.setText("");
        spnEstado.setSelection(0);
    }

    public void acaoVoltar() {
        finish();
    }
}
