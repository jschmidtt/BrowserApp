package edu.temple.browserapp;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView urlTextView;
    WebView webView;
    //TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //display = findViewById(R.id.htmlTextView);
        urlTextView = findViewById(R.id.urlEditText);
        webView = findViewById(R.id.webView);

        //Button Setup
        findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    public void run(){
                        try {
                            //Grab url from urlTextView
                            URL url = new URL(urlTextView.getText().toString());
                            BufferedReader reader = new BufferedReader(
                                    new InputStreamReader(
                                            url.openStream()));

                            //String
                            StringBuilder sb = new StringBuilder();
                            String tmpString;
                            while ((tmpString = reader.readLine()) != null){
                                sb.append(tmpString);
                            }

                            //Send Msg to Handler
                            Message msg = Message.obtain();
                            msg.obj = sb.toString();
                            responseHandler.sendMessage(msg);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });//End Button
    }

    //Handler to set the display text
    Handler responseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //WebView object loading data
            webView.loadData((String) msg.obj, "text/html", "UTF-8");
            //display.setText((String) msg.obj);
            return false;
        }
    });

    //ActionBar---Inflate Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_control, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ActionBar---Handling Menu Clicks

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

    int id = item.getItemId();

        if(id == R.id.action_settings){
            Toast.makeText(this, "You Clicked Settings!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_go){

        }

        return super.onOptionsItemSelected(item);
    }

    //URL METHOD
    private void loadUrlFromTextView() {
        new Thread(){
            public void run(){
                try {
                    //Grab url from urlTextView
                    URL url = new URL(urlTextView.getText().toString());
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    url.openStream()));

                    //String
                    StringBuilder sb = new StringBuilder();
                    String tmpString;
                    while ((tmpString = reader.readLine()) != null){
                        sb.append(tmpString);
                    }

                    //Send Msg to Handler
                    Message msg = Message.obtain();
                    msg.obj = sb.toString();
                    responseHandler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

