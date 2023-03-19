package com.example.unidade3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.ItemModel;

public class MainActivity extends AppCompatActivity {
    Context context;

    Map<String, String> dicionarioUsuarios = new HashMap<String, String>(){{
        put("juca", "bala");
        put("anderson", "silva");
        put("cristiano","ronaldo");
    }};

    Map<String, String> dicionarioDicas = new HashMap<String, String>() {{
        put("juca", "Doce muito popular.");
        put("anderson", "Sobrenome muito comum no Brasil.");
        put("cristiano","Fez dois gols na final da copa de 2002.");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        Button btnLogin = findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void Login(){
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);

        String user = username.getText().toString();
        String pass = password.getText().toString();

        String senhaCorreta = dicionarioUsuarios.get(user);

        if(pass.equals(senhaCorreta)){

            Intent i = new Intent(MainActivity.this, ItemActivity.class);

            startActivity(i);

        }else if(senhaCorreta == null){
            Toast.makeText(context, "USUÁRIO INEXISTENTE!", Toast.LENGTH_SHORT).show();

        }else{
            AlertDialog alert = this.CreateYesNoAlert(user);

            alert.show();

        }
    }

    private AlertDialog CreateYesNoAlert(String user){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Senha incorreta! Gostaria de ver a dica de senha?");

        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog alert = CreateDicaAlert(user);

                alert.show();

                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alert = builder.create();

        return alert;
    }

    private AlertDialog CreateDicaAlert(String user){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        String dica = dicionarioDicas.get(user);

        builder.setTitle("Dica");
        builder.setMessage(dica);

        AlertDialog alert = builder.create();

        return alert;
    }
}