package com.example.pm2e1201720050029;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm2e1201720050029.Contactos.Contactos;
import com.example.pm2e1201720050029.Transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityContacto extends AppCompatActivity {

    SQLiteConexion conexion;
    ArrayList<String> ListaContactos;
    ArrayList<Contactos> lista;
    Spinner pais;
    TextView txtPais;
    EditText nombre;
    EditText telefono;
    EditText nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        pais = (Spinner) findViewById(R.id.comboPais);
        txtPais = (TextView) findViewById(R.id.txtPais);
        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        nota = (EditText) findViewById(R.id.txtNota);

        String dato = getIntent().getStringExtra("dato");
        txtPais.setText("" + dato);

        //ObtenerListaContactos();
        /*
        ArrayAdapter<CharSequence> adp = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ListaContactos);
        pais.setAdapter(adp);

        pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                txtPais.setText(lista.get(position).getPais());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/
        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        Button btnContactos = (Button) findViewById(R.id.btnContactos);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarContacto();
            }
        });

        btnContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityVerContactos.class);
                startActivity(intent);
            }
        });

    }

/*
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
            fillCombo();
        }
    }//obtenerListaPersonas

    private void fillCombo() {
        ListaContactos = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++){
            ListaContactos.add(lista.get(i).getPais() +
                    +lista.get(i).getCodigo());
        }
    }//fillCombo
*/
    private void AgregarContacto() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDataBase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Transacciones.pais,txtPais.toString());
        valores.put(Transacciones.nombre, nombre.getText().toString());
        valores.put(Transacciones.telefono, telefono.getText().toString());
        valores.put(Transacciones.nota, nota.getText().toString());

        Long registro = db.insert(Transacciones.tablacontactos, Transacciones.id, valores);
        //toast
        Toast.makeText(getApplicationContext(), " INSERCCION EXITOSA " + registro.toString(), Toast.LENGTH_LONG).show();
        //cerrando base de datos
        db.close();

        ClearScreen();
    }

    private void ClearScreen() {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }

    public void clickNew(View view) {
        Intent intent = new Intent(this, ActivityPais.class);
        startActivity(intent);
    }
}