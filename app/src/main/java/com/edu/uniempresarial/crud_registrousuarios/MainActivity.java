package com.edu.uniempresarial.crud_registrousuarios;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.edu.uniempresarial.crud_registrousuarios.classes.User;
import com.edu.uniempresarial.crud_registrousuarios.model.UserDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public  static final String TAG = "Validate";
    private EditText etDocumento, etUsuario, etNombres, etApellidos, etContra;
    private ListView listUsers;
    private Button btnRegistrar, btnModificar, btnEliminar,btnlimpiar;
    private int documento;
    String usuario, nombres, apellidos, contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
        //userList();
        btnRegistrar.setOnClickListener(view -> {
            if (etDocumento.getText().toString().equals("") ||
                    etUsuario.getText().toString().equals("") ||
                    etNombres.getText().toString().equals("") ||
                    etApellidos.getText().toString().equals("") ||
                    etContra.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            }else if(etDocumento.length() > 10){
                Toast.makeText(MainActivity.this, "El documento no puede tener mas de 10 digitos", Toast.LENGTH_SHORT).show();
            }else{
                registerUser(view);
            }
        });
        btnModificar.setOnClickListener(view -> {
            if (etDocumento.getText().toString().equals("") ||
                    etUsuario.getText().toString().equals("") ||
                    etNombres.getText().toString().equals("") ||
                    etApellidos.getText().toString().equals("") ||
                    etContra.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            }else if(etDocumento.length() > 10){
                Toast.makeText(MainActivity.this, "El documento no puede tener mas de 10 digitos", Toast.LENGTH_SHORT).show();
            }else{
                updateUser(view);
            }
        });

        btnEliminar.setOnClickListener(view -> {
            if (etDocumento.getText().toString().equals("")){
                Toast.makeText(MainActivity.this, "Debe llenar el campo documento para poder eliminar el registro", Toast.LENGTH_SHORT).show();
            } else if (etDocumento.length() > 10){
                Toast.makeText(MainActivity.this, "El documento no puede tener mas de 10 digitos", Toast.LENGTH_SHORT).show();
            }else{
                deleteUser(view);
            }
        });
    }

    private void initializeVariables(){
        etDocumento = findViewById(R.id.etDocumento);
        etUsuario = findViewById(R.id.etUsuario);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etContra = findViewById(R.id.etContra);
        listUsers = findViewById(R.id.lvLista);
        btnlimpiar = findViewById(R.id.btnLimpiar);
        btnModificar = findViewById(R.id.btnModificar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }

    private boolean checkFields(){
        documento = Integer.parseInt(etDocumento.getText().toString());
        usuario = etUsuario.getText().toString();
        nombres = etNombres.getText().toString();
        apellidos = etApellidos.getText().toString();
        contra = etContra.getText().toString();
        Log.i(TAG, "Documento: "+documento);
        return true;
    }

    public void registerUser(View view){
        if (checkFields()){
            UserDAO userDAO = new UserDAO(this, view);
            User user = new User(documento, usuario, nombres, apellidos, contra, 1);
            userDAO.insertUser(user);
            //userList();
            limpiarUser(view);
        }
    }

    public void callUserList(View view){
        userList();
    }
    private void userList(){
        UserDAO userDAO = new UserDAO(this, listUsers);
        ArrayList<User> userList = userDAO.getUserList();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listUsers.setAdapter(adapter);
    }

    public void limpiarUser(View view){
        etDocumento.setText("");
        etUsuario.setText("");
        etNombres.setText("");
        etApellidos.setText("");
        etContra.setText("");
        ArrayList<User> userList = new ArrayList<>();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listUsers.setAdapter(adapter);
    }

    public static ContentValues contentValues = new ContentValues();
    public void updateUser(View view){
        documento = Integer.parseInt(etDocumento.getText().toString());
        usuario = etUsuario.getText().toString();
        nombres = etNombres.getText().toString();
        apellidos = etApellidos.getText().toString();
        contra = etContra.getText().toString();

        if (!(documento == 0) && !(usuario.equals("")) && !(nombres.equals("")) && !(apellidos.equals("")) && !(contra.equals(""))){
            contentValues.put("usu_document", documento);
            contentValues.put("usu_user", usuario);
            contentValues.put("usu_names", nombres);
            contentValues.put("usu_last_names", apellidos);
            contentValues.put("usu_pass", contra);
            contentValues.put("usu_status", 1);
        }

        UserDAO userDAO = new UserDAO(this, view);
        User user = new User(documento, usuario, nombres, apellidos, contra, 1);
        userDAO.updateDB(user);
        userList();
    }

    public void deleteUser(View view){
        documento = Integer.parseInt(etDocumento.getText().toString());
        UserDAO userDAO = new UserDAO(this, view);
        User user = new User(documento, usuario, nombres, apellidos, contra, 0);
        userDAO.changeStatus(user);
        userList();
    }
}