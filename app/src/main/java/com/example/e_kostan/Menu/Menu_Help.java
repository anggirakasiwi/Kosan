package com.example.e_kostan.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.e_kostan.R;

public class Menu_Help extends AppCompatActivity {
TextView Help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__help);
        Help=(TextView)findViewById(R.id.help);
        Help.setText(Html.fromHtml("<H1>Email</H1><br><p>elfiramputri23@gmail.com</p><br><H1>Telpon</H1> <br><p>081367374669</p>"));
    }
    @Override
    public void onBackPressed(){
        Back();
    }

    private void Back() {
        Intent intent=new Intent(Menu_Help.this,Menu_Utama.class);
        startActivity(intent);
        finish();
    }
}