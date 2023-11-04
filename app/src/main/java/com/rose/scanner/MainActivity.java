package com.rose.scanner;

import static android.webkit.URLUtil.isValidUrl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private Button scanButton;
    private TextView resulTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton = findViewById(R.id.btncodigo);
        resulTextView = findViewById(R.id.txtresultado);

        scanButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setPrompt("Escánea un código de barras o QR");
                integrator.initiateScan();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            String scanContent = result.getContents();
            resulTextView.setText("Resultado del escaneo:"+ scanContent);

            if(isValidUrl(scanContent)){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanContent));
                startActivity(browserIntent);
            }
        }
    }

    private boolean isValidUrl(String url) {
        return url != null && (url.startsWith("https://")|| url.startsWith("https://"));
    }
}