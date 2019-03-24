package com.helpdevs.usoasynctask;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar barraCarga;
    Button boton1;
    LinearLayout layout;
    TextView txtAvance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (LinearLayout) findViewById(R.id.layout_color);
        txtAvance = (TextView) findViewById(R.id.txtAvance);
        boton1 = (Button) findViewById(R.id.btn1);
        barraCarga = (ProgressBar) findViewById(R.id.barraCarga);
        boton1.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn1:
              TareaSincrona tarea = new TareaSincrona();
              tarea.execute();
              break;
          default:
              return;
      }
    }


    public class TareaSincrona extends AsyncTask<Void, Integer, Void> {

        private int x = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barraCarga.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            barraCarga.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values[0]);
            int red = generarAleatorio();
            int green = generarAleatorio();
            int blue = generarAleatorio();
            layout.setBackgroundColor(Color.rgb(red, green, blue));
            txtAvance.setText(values[0].toString());
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                while(x < 200){
                    Thread.sleep(50);
                    x++;
                    publishProgress(x);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        private int generarAleatorio()
        {
            return new Random().nextInt(256);
        }
    }

}
