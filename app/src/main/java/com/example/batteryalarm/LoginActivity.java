package com.example.batteryalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DBHelper DB = new DBHelper(this);
        EditText username, password;
        Button submit, register;

        username = (EditText) findViewById(R.id.edtUser);
        password = (EditText) findViewById(R.id.edtPass);
        submit = (Button) findViewById(R.id.btnSubmit);
        register = (Button) findViewById(R.id.btnRegister);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please fill-up all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkRes = DB.checkUserCredentials(username.getText().toString(), password.getText().toString());
                    if(checkRes){
                        Intent loginPage = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(loginPage);
                        Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        password.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });


    }
}