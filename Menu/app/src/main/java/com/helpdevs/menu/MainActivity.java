package com.helpdevs.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printToLogs(View view) {
        // Find first menu item TextView and print the text to the logs
        TextView text1 = (TextView) findViewById(R.id.menu_item_1);
        String menu1 = text1.getText().toString();

        // Find second menu item TextView and print the text to the logs
        TextView text2 = (TextView) findViewById(R.id.menu_item_2);
        String menu2 = text2.getText().toString();

        // Find third menu item TextView and print the text to the logs
        TextView text3 = (TextView) findViewById(R.id.menu_item_3);
        String  menu3 = text3.getText().toString();

        //Show log info
        Log.i("Memu Items", menu1 + "\n" + menu2 + "\n" + menu3);

    }
}