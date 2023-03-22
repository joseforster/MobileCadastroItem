package com.example.unidade3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import model.ItemModel;

public class ItemActivity extends AppCompatActivity {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item);

        gerenciadorBancoDeDados = new MeuSQLite(this, "projetodb");

        Button btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalvarItem();
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LimparCampos();
            }
        });

        Button btnExcluir = findViewById(R.id.btnExcluir);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExcluirItem();
            }
        });

        Bundle b = this.getIntent().getExtras();

        if(b != null){
            String id = b.getString("id");
            String descricao = b.getString("descricao");
            String quantidade = b.getString("quantidade");

            TextView idTextView = findViewById(R.id.id_item);
            TextView descricaoTextView = findViewById(R.id.descricao_item);
            TextView quantidadeTextView = findViewById(R.id.quantidade_item);

            idTextView.setText(id);
            descricaoTextView.setText(descricao);
            quantidadeTextView.setText(quantidade);
        }
    }

    private void ExcluirItem(){

        TextView idTextView = findViewById(R.id.id_item);

        bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();

        String where = "id = " + Integer.parseInt(idTextView.getText().toString());

        long resultado = bancoDeDados.delete("item",where,null);

        bancoDeDados.close();

        if (resultado ==-1)
            Toast.makeText(this,"Erro ao realizar operação",Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this,"Item excluído com sucesso",Toast.LENGTH_SHORT).show();

            this.LimparCampos();
        }

    }

    private void LimparCampos(){

        TextView idTextView = findViewById(R.id.id_item);

        idTextView.setText("0");

        TextView descricaoTextView = findViewById(R.id.descricao_item);

        descricaoTextView.setText(null);

        TextView quantidadeTextView = findViewById(R.id.quantidade_item);

        quantidadeTextView.setText(null);

    }

    private void SalvarItem(){
        TextView descricaoTextView = findViewById(R.id.descricao_item);
        TextView quantidadeTextView = findViewById(R.id.quantidade_item);
        TextView idTextView = findViewById(R.id.id_item);

        boolean isValid = true;

        int id = Integer.parseInt(idTextView.getText().toString());
        String descricao = descricaoTextView.getText().toString();

        if(descricao.isEmpty() || quantidadeTextView.getText().toString().isEmpty()){
            Toast.makeText(this, "Descrição e quantidade são obrigatórios", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        int quantidade = Integer.parseInt(quantidadeTextView.getText().toString());

        if(isValid){

            ItemModel itemModel = new ItemModel(id, descricao, quantidade);

            ContentValues valores = new ContentValues();
            valores.put("descricao",  itemModel.getDescricao());
            valores.put("quantidade", itemModel.getQuantidade() );

            bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();

            long resultado;
            //update
            if(itemModel.getId() > 0){

                String where = "id = " + itemModel.getId();

                resultado = bancoDeDados.update("item",valores, where,null);

            }else{
                //insert
                resultado = bancoDeDados.insert("item", null, valores);
            }

            bancoDeDados.close();

            if (resultado ==-1)
                Toast.makeText(this,"Erro ao realizar operação",Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this,"Item salvo: " + itemModel.getDescricao() + " n: " + itemModel.getQuantidade(),Toast.LENGTH_SHORT).show();

                this.LimparCampos();
            }
        }
    }
}
