package co.innovamos.innovaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnReproducir;
    SeekBar barraVolumen;
    SeekBar barraProgreso;
    TextView txtEstado;
    TextView txtDuracion;
    MediaPlayer reproductor;
    Handler progresoHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnReproducir = (Button) findViewById(R.id.btn_reproducir);
        reproductor = MediaPlayer.create(this, R.raw.el_ganador);
        txtEstado = (TextView) findViewById(R.id.txt_estado);
        txtDuracion = (TextView) findViewById(R.id.txt_duracion);
        barraVolumen = (SeekBar) findViewById(R.id.barra_volumen);
        barraProgreso = (SeekBar) findViewById(R.id.barra_progreso);
        barraProgreso.setMax(reproductor.getDuration());
        barraVolumen.setMax(100);

        barraVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progreso = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progreso = progress;

                Toast.makeText(getApplicationContext(), "Cambiando el volumen a " + progreso, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Vas a subir el volumen ;)", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (reproductor != null) {
                    reproductor.setVolume(progreso * 1.0f / 100.0f, progreso * 1.0f / 100.0f);
                }
                txtEstado.setText(progreso + "/" + barraVolumen.getMax());
                Toast.makeText(getApplicationContext(), "Haz acabado.. de mover el volumen :p", Toast.LENGTH_SHORT).show();
            }
        });


        barraProgreso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (reproductor != null && fromUser){
                    reproductor.seekTo(progress);
                    txtDuracion.setText("Duracion: " + progress + "/" + reproductor.getDuration() / 60);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        reproductor.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                View vista = null;
                Toast.makeText(getApplicationContext(), "Audio reproducido correctamente", Toast.LENGTH_LONG).show();
                detenerAudio(vista);
            }
        });

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(reproductor != null)
                {
                    int posicion_actual = reproductor.getCurrentPosition();
                    barraProgreso.setProgress(posicion_actual);
                    txtDuracion.setText("Duracion: " + reproductor.getCurrentPosition() + "/" + reproductor.getDuration() / 60);

                }
                    progresoHandler.postDelayed(this, 1000);
            }
        });
    }

    public void reproducirAudio(View v) {

        if (reproductor == null) {
            reproductor = MediaPlayer.create(getApplicationContext(), R.raw.el_ganador);
            barraProgreso.setMax(reproductor.getDuration());
        }

        reproductor.start();
        Toast.makeText(this, "Reproduciendo...", Toast.LENGTH_SHORT).show();
    }

    public void pausarAudio(View v) {
        if (reproductor != null) {
            reproductor.pause();
        }
    }

    public void detenerAudio(View v) {
        if (reproductor != null) {
            reproductor.stop();
            reproductor.reset();
            reproductor.release();
            reproductor = null;
        }
    }

}
