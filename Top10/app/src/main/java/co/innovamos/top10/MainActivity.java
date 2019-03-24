package co.innovamos.top10;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Arrancando la AsincTask");
        TareaDescarga descarga = new TareaDescarga();
        descarga.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml");
        Log.d(TAG, "onCreate: Finalizado");
    }


    private class TareaDescarga extends AsyncTask<String, Void, String> {

        private static final String TAG = "TareaDescarga";

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "onCreate: Empieza la descarga de" + params[0]);
            String rssFeed = descargarXML(params[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error en la descarga del RSS");
            }

            return rssFeed;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: El parametro que ha llegado es : " + s.toString());
            ParserAppData parserAppData = new ParserAppData();
            parserAppData.parseData(s);
        }
    }

    private String descargarXML(String urlPath) {
        StringBuilder xmlResultado = new StringBuilder();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            int respuesta = conexion.getResponseCode();
            Log.d(TAG, "descargarXML: El codigo de respuesta recibido es " + respuesta);

           /* InputStream inputStream = conexion.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader)
            */

            BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            int charsRead;
            char[] inputBuffer = new char[2000];

            while (true) {
                charsRead = reader.read(inputBuffer);

                Log.d(TAG, "descargarXML: Acabo de leer " + charsRead + " del servidor");

                if (charsRead < 0) {
                    break;
                }
                if (charsRead > 0) {
                    xmlResultado.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }
            }
            reader.close();

            return xmlResultado.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "descargarXML: URL inválida" + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "descargarXML: IO Exception" + e.getMessage());
        }catch (SecurityException e){
            Log.e(TAG, "descargarXML: Tienes problema de seguridad :" + e.getMessage());
        }
        return null;

    }

}


