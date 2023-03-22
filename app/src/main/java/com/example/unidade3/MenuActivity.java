package com.example.unidade3;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater exibirMenu = getMenuInflater();
        exibirMenu.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.criar_item:
                startActivity(new Intent(MenuActivity.this,ItemActivity.class));
                return true;
            case R.id.listar_itens:
                startActivity(new Intent(MenuActivity.this,ListarItemActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
