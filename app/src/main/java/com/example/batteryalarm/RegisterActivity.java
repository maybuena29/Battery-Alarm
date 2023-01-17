package com.example.batteryalarm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private String adminPass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        EditText name, address, contact, username, password;
        Button register, cancel, modify;
        DBHelper DB = new DBHelper(this);

        name = (EditText) findViewById(R.id.edtName);
        address = (EditText) findViewById(R.id.edtAddress);
        contact = (EditText) findViewById(R.id.edtContact);
        username = (EditText) findViewById(R.id.edtUsername);
        password = (EditText) findViewById(R.id.edtPass);

        register = (Button) findViewById(R.id.btnRegister);
        cancel = (Button) findViewById(R.id.btnCancel);
        modify = (Button) findViewById(R.id.btnModify);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = name.getText().toString();
                String uAddress = address.getText().toString();
                String uContact = contact.getText().toString();
                String uUsername = username.getText().toString();
                String uPassword = password.getText().toString();

                if(uName.equals("") || uAddress.equals("") || uContact.equals("") || uUsername.equals("") || uPassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill-up all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkInsertData = DB.insertUserData(uName, uAddress, uContact, uUsername, uPassword);
                    if(checkInsertData){
                        Toast.makeText(getApplicationContext(), "User Successfully Created!", Toast.LENGTH_SHORT).show();
                        Intent loginPage = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(loginPage);
                    }else{
                        Toast.makeText(getApplicationContext(), "User Creation Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginPage);
                finish();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Enter Admin Password");
                final EditText input = new EditText(RegisterActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adminPass = input.getText().toString();
                        if(adminPass.equals("admin123")){
                            Toast.makeText(getApplicationContext(), "Welcome Admin!", Toast.LENGTH_SHORT).show();
                            Intent modifyUserPage = new Intent(RegisterActivity.this, ModifyUserData.class);
                            startActivity(modifyUserPage);
                        }else{
                            Toast.makeText(getApplicationContext(), "Wrong Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

    }
}















