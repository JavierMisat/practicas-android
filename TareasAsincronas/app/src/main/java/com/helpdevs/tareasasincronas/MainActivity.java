package com.helpdevs.tareasasincronas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button boton;
    Button boton2;
    Button boton3;
    ProgressBar barraCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.btn1);
        boton2 = (Button) findViewById(R.id.btn2);
        boton3 = (Button) findViewById(R.id.btn3);
        barraCarga = (ProgressBar) findViewById(R.id.progressBar);

        boton.setOnClickListener(this);
        boton2.setOnClickListener(this);
        boton3.setOnClickListener(this);
    }

    private void unSegundo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    void hilos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    unSegundo();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Tarea realizada", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }


    private class EjemploAsyncTask extends AsyncTask<Void, Integer, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barraCarga.setMax(100);
            barraCarga.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i = 0; i < 10; i++) {
                unSegundo();
                publishProgress(i*10);
                if(isCancelled()){
                    break;
                }
            }
            return true;
        }


        @Override
        protected void onPostExecute(Boolean resultado) {
           // super.onPostExecute(resultado);
            if(resultado)
            {
                Toast.makeText(getBaseContext(), "Se ha realizado la tarea...", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled(Boolean resultado) {
            super.onCancelled(resultado);
            Toast.makeText(getBaseContext(), "La tarea ha sido cancelada...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            barraCarga.setProgress(values[0].intValue());
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                for (int i = 0; i < 10; i++) {
                    unSegundo();
                }
                break;
            case R.id.btn2:
                hilos();
                break;
            case R.id.btn3:
                EjemploAsyncTask probando = new EjemploAsyncTask();
                probando.execute();
            default:
                break;
        }
    }
}
