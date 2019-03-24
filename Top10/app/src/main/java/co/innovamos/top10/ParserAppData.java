package co.innovamos.top10;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by usuario on 13/02/2017.
 */

public class ParserAppData {
    private static final String TAG = "ParserAppData";
    private ArrayList<AppData> apps;

    public ParserAppData() {
        this.apps = new ArrayList<>();
    }

    public ArrayList<AppData> getApps() {
        return this.apps;
    }

    public boolean parseData(String xmlData) {
        boolean estado = true;
        AppData datoActual = null;
        boolean enEntrada = false;
        boolean tieneImagen = false;
        String valorTexto = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int tipoEvento = parser.getEventType();
            while (tipoEvento != XmlPullParser.END_DOCUMENT) {
                String nombreEtiqueta = parser.getName();
                switch (tipoEvento) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parseData: Empezamos a leer la etiqueta " + nombreEtiqueta);
                        if ("entry".equalsIgnoreCase(nombreEtiqueta)) {
                            enEntrada = true;
                            datoActual = new AppData();

                        } else if ("image".equalsIgnoreCase(nombreEtiqueta) && enEntrada) {
                            String resolucionImagen = parser.getAttributeValue(null, "height");
                            if (resolucionImagen != null) {
                                tieneImagen = "100".equalsIgnoreCase(resolucionImagen);
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        valorTexto = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parseData: Cerramos la etiqueta " + nombreEtiqueta);
                        if (enEntrada) {
                            if ("entry".equalsIgnoreCase(nombreEtiqueta)) {
                                apps.add(datoActual);
                                enEntrada = false;
                            } else if ("name".equalsIgnoreCase(nombreEtiqueta)) {
                                datoActual.setTitulo(valorTexto);
                            } else if ("artist".equalsIgnoreCase(nombreEtiqueta)) {
                                datoActual.setArtista(valorTexto);
                            } else if ("releaseDate".equalsIgnoreCase(nombreEtiqueta)) {
                                datoActual.setFechaLanzamiento(valorTexto);
                            } else if ("summary".equalsIgnoreCase(nombreEtiqueta)) {
                                datoActual.setResumen(valorTexto);
                            } else if ("image".equalsIgnoreCase(nombreEtiqueta)) {
                                if (tieneImagen) {
                                    Log.d(TAG, "parseData: Hemos guardado la imagen");
                                    datoActual.setUrlImagen(valorTexto);
                                    tieneImagen = false;
                                }
                            }
                        }
                        break;
                    default:
                        //
                        break;
                }

                tipoEvento = parser.next();
            }

            for (AppData app : apps) {
                Log.d(TAG, app.toString());
            }

        } catch (Exception e) {
            estado = false;
            e.printStackTrace();
        }
        return estado;
    }
}
