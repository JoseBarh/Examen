package com.example.pm2e1201720050029;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.pm2e1201720050029.Contactos.Contactos;
import com.example.pm2e1201720050029.Transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityVerContactos extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listacontactos;
    ArrayList<Contactos> lista;
    ArrayList<String> ArregloContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_contactos);

        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        listacontactos = (ListView) findViewById(R.id.ListaContactos);

        ObtenerListaContactos();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ArregloContactos);
        listacontactos.setAdapter(adp);

        listacontactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String informacion = "ID: " + lista.get(position).getId() + "\n";
                informacion += "Nombre: " + lista.get(position).getNombre() + "\n";
                informacion += "Telefono: " + lista.get(position).getTelefono();

                Toast.makeText(getApplicationContext(), informacion,Toast.LENGTH_LONG).show();

                Intent Compartir = new Intent();
                Compartir.setAction(Intent.ACTION_SEND);
                Compartir.putExtra(Intent.EXTRA_TEXT, informacion);
                Compartir.setType("text/plain");

                Intent share = Intent.createChooser(Compartir, null);
                startActivity(share);

            }
        });
    }
    private void ObtenerListaContactos(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos listContactos = null;
        lista = new ArrayList<Contactos>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tablacontactos, null);

        while (cursor.moveToNext()){
            listContactos = new Contactos();
            listContactos.setId(cursor.getInt(0));
            listContactos.setPais(cursor.getString(1));
            listContactos.setNombre(cursor.getString(2));
            listContactos.setTelefono(cursor.getString(3));
            listContactos.setNota(cursor.getString(4));


            lista.add(listContactos);
            fillList();
        }
    }//obtenerListaPersonas

    private void fillList() {
        ArregloContactos = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++){
            ArregloContactos.add(lista.get(i).getId() + " | "
                    +lista.get(i).getNombre() + " | "
                    +lista.get(i).getTelefono());
        }
    }//fillList
}