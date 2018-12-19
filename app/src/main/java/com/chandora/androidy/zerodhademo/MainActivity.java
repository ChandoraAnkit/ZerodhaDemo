package com.chandora.androidy.zerodhademo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zerodhatech.kiteconnect.KiteConnect;
import com.zerodhatech.kiteconnect.kitehttp.SessionExpiryHook;
import com.zerodhatech.kiteconnect.kitehttp.exceptions.KiteException;
import com.zerodhatech.models.Margin;
import com.zerodhatech.models.Order;
import com.zerodhatech.models.User;

import org.json.JSONException;

import java.io.IOException;
import java.security.Permission;
import java.security.Permissions;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {


    WebView mWebview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String api_key = "u3mlhhy976n8i7q5";


        mWebview = findViewById(R.id.webview);




        final KiteConnect kiteSdk = new KiteConnect(api_key);

        kiteSdk.setUserId("ankit_ifgdgd");

        String url = kiteSdk.getLoginURL();

        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final WebResourceRequest request) {
                Log.i("MY_LOGIN", "onCreate: "+request.getUrl());


                final String token = request.getUrl().getQueryParameter("request_token");
                String status = request.getUrl().getQueryParameter("status");


                if (token != null &&!token.equalsIgnoreCase("null") && status != null && status.equalsIgnoreCase("success")){


                    Log.i("MY_URL", "shouldOverrideUrlLoading: "+request.getUrl());
                    Log.i("CREDS", "shouldOverrideUrlLoading: "+token);
                    Log.i("CREDS", "shouldOverrideUrlLoading: "+status);



                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                try {

                                    User userModel = null;
                                    userModel = kiteSdk.generateSession(token, "qlwgh9fyrahjrl39qauxbnylrlzkx1gd");


                                    Log.i("ACCESS", "shouldOverrideUrlLoading: "+userModel.accessToken);
                                    Log.i("PUBLIC", "shouldOverrideUrlLoading: "+userModel.publicToken);

                                    kiteSdk.setAccessToken(userModel.accessToken);
                                    kiteSdk.setPublicToken(userModel.publicToken);

                                    Margin margins = kiteSdk.getMargins("equity");

//                                    Log.i("MY_CASH", "shouldOverrideUrlLoading: "+margins);
//                                    Log.i("MY_DEBITS", "shouldOverrideUrlLoading: "+margins.utilised.debits);

                                    Intent resultIntent = new Intent();
                                    resultIntent.putExtra("RESULT",request.getUrl().toString());
                                    setResult(Activity.RESULT_OK,resultIntent);
                                    finish();



                                } catch (KiteException e) {
                                    finish();
                                    Log.i("KITE_EXCEPTION", "run: "+e.message);
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    finish();

                                    e.printStackTrace();
                                } catch (IOException e) {
                                    finish();

                                    e.printStackTrace();
                                }

                            }
                        }).start();


                }


                return super.shouldOverrideUrlLoading(view, request);
            }
        });



// Initialize Kiteconnect using apiKey.


        mWebview.loadUrl(url);




    }
}
