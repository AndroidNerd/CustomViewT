package com.example.nerd.customviewt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nerd.verview.VerCode;

public class MainActivity extends AppCompatActivity {
    Button btn;
    VerCode ver_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ver_code= (VerCode) findViewById(R.id.ver_code);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,ver_code.getmTxt(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
