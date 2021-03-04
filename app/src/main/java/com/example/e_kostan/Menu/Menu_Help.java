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
        Help.setText(Html.fromHtml("<i>elfiramputri23@gmail.com</i> <br><i>elfiramputri23@gmail.com</i>"));
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