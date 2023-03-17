package com.example.unidade3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.ItemModel;

public class MainActivity extends AppCompatActivity {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;
    ArrayAdapter<String> adapterListaItem;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gerenciadorBancoDeDados = new MeuSQLite(this, "projetodb");

        //adapter da lista
        ArrayList<String> lista = this.ReadAllItem();

        this.listView = findViewById(R.id.lista);

        this.adapterListaItem = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, lista);

        listView.setAdapter(adapterListaItem);

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

            if(itemModel.getId() > 0){



            }else{

                ContentValues valores = new ContentValues();
                valores.put("descricao",  itemModel.getDescricao());
                valores.put("quantidade", itemModel.getQuantidade() );

                bancoDeDados = gerenciadorBancoDeDados.getWritableDatabase();

                long resultado = bancoDeDados.insert("item", null, valores);

                bancoDeDados.close();

                if (resultado ==-1)
                    Toast.makeText(this,"Não foi possível inserir este item",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this,"Item inserido: " + itemModel.getDescricao() + " n: " + itemModel.getQuantidade(),Toast.LENGTH_SHORT).show();

                    this.AtualizarGrid();

                    this.LimparCampos();
                }
            }
        }
    }

    private void AtualizarGrid(){

        ArrayList<String> lista = this.ReadAllItem();

        this.listView = findViewById(R.id.lista);

        adapterListaItem =  new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, lista);

        adapterListaItem.notifyDataSetChanged();

        listView.setAdapter(adapterListaItem);

        listView.invalidateViews();
        listView.refreshDrawableState();
    }

    private ArrayList<String> ReadAllItem(){
        ArrayList<String> listaItem = new ArrayList<>();

        bancoDeDados = gerenciadorBancoDeDados.getReadableDatabase();

        String[] campos_item = {"id", "descricao", "quantidade"};
        Cursor lista = bancoDeDados.query("item", campos_item, null, null, null, null, null );

        lista.moveToFirst();

        while(lista.isAfterLast() == false){

            int id = Integer.parseInt(lista.getString(lista.getColumnIndexOrThrow("id")));
            String descricao = lista.getString(lista.getColumnIndexOrThrow("descricao"));
            int quantidade = Integer.parseInt(lista.getString(lista.getColumnIndexOrThrow("quantidade")));

            String itemInfo = id + " - " + descricao + " - " + quantidade;

            listaItem.add(itemInfo);

            lista.moveToNext();
        }

        bancoDeDados.close();

        return listaItem;
    }
}