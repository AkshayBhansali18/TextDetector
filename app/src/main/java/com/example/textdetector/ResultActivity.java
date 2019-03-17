package com.example.textdetector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView=(TextView)findViewById(R.id.textView);
        Button button=(Button)findViewById(R.id.button);
        Intent intent=getIntent();
        String result=intent.getStringExtra("result");
        textView.append("\n"+result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
