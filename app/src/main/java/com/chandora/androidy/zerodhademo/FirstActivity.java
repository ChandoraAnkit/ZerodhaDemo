package com.chandora.androidy.zerodhademo;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {

    TextView textView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        textView = findViewById(R.id.resultText);

    }

    public void onClickButton(View view) {

        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent,0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0){


            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("RESULT");
                textView.setText(result);
            }
        }

    }
}
