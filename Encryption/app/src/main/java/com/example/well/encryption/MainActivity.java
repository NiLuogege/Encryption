package com.example.well.encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.well.encryption.encryption.DES_3Utils;

public class MainActivity extends AppCompatActivity {

    private String mEncrypt;
    private String key = "luochennn";
    String data = "日了鬼了";
    private byte[] mSecretArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.encrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 加密
                mSecretArr = DES_3Utils.encryptMode(data.getBytes());
                if (BuildConfig.DEBUG) Log.e("MainActivity", new String(mSecretArr));

            }
        });
        findViewById(R.id.decrypt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] myMsgArr = DES_3Utils.decryptMode(mSecretArr);
                if (BuildConfig.DEBUG) Log.e("MainActivity", new String(myMsgArr));

            }
        });
    }
}
