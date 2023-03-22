package com.example.unidade3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListarItemActivity extends AppCompatActivity {

    MeuSQLite gerenciadorBancoDeDados;
    SQLiteDatabase bancoDeDados;
    ArrayAdapter<String> adapterListaItem;
    ListView listView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar_item);

        gerenciadorBancoDeDados = new MeuSQLite(this, "projetodb");

        //adapter da lista
        ArrayList<String> lista = this.ReadAllItem();

        this.listView = findViewById(R.id.lista);

        this.adapterListaItem = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, lista);

        listView.setAdapter(adapterListaItem);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterListaItem.getItem(i);

                String id = item.split(" - ")[0];
                String descricao = item.split(" - ")[1];
                String quantidade = item.split(" - ")[2];

                Intent intent = new Intent(ListarItemActivity.this, ItemActivity.class);

                Bundle b = new Bundle();
                b.putString("id", id);
                b.putString("descricao", descricao);
                b.putString("quantidade", quantidade);

                intent.putExtras(b);

                startActivity(intent);
            }
        });
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
