package saugat.splashscreen_slide.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import saugat.splashscreen_slide.R;
import saugat.splashscreen_slide.other.DBHelper;


public class Login extends AppCompatActivity {

    protected Button btn_login;
    protected EditText uName,password;
    protected Boolean authorized;
    protected TextView register;
    DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        setToolbarTitle();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_login = (Button) findViewById(R.id.login);
        uName = (EditText) findViewById(R.id.editText_uName) ;
        password = (EditText) findViewById(R.id.editText_pwd);
        register = (TextView) findViewById(R.id.txt_register);

        myDb = new DBHelper(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authorized = myDb.ValidUser(String.valueOf(uName.getText()),String.valueOf(password.getText()));

                if(authorized){
                    Toast.makeText(getApplicationContext(), "You Are Authorized!!",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "You Are Not Authorized!!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }

    private void setToolbarTitle() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.login_register);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
