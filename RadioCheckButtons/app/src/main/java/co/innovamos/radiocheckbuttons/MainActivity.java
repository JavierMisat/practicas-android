package co.innovamos.radiocheckbuttons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup grupoGenero;
    CheckBox checkContrato;

    public static final String GENERO = "Genero" + MainActivity.class;
    private static final String TAG = "MainActivity";

    private RadioButton genero = null;
    private RadioButton genHombre;
    private RadioButton genMujer;
    private String contrato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grupoGenero = (RadioGroup) findViewById(R.id.grupoGenero);
        genHombre = (RadioButton) findViewById(R.id.radio_hombre);
        genMujer = (RadioButton) findViewById(R.id.radio_mujer);
        checkContrato = (CheckBox) findViewById(R.id.check_contrato);

        checkContrato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox contrato = (CheckBox) v;
                genHombre.setClickable(contrato.isChecked());
                genMujer.setClickable(contrato.isChecked());
            }
        });
    }

    public void clickIngresar(View v) {

        String mensaje = "";
        int radioSeleccionado = grupoGenero.getCheckedRadioButtonId();

        if (radioSeleccionado > -1) {
            genero = (RadioButton) findViewById(radioSeleccionado);
        } else {
            mensaje = "Debes seleccionar tu genero";
        }

        if (checkContrato.isChecked()) {
            contrato = "Aceptó";
        } else {
            mensaje = "Debe aceptar los terminos del contrato";
        }

        if (checkContrato.isChecked() && radioSeleccionado > -1) {
            mensaje = "Gracias por registrarte, eres "
                    + genero.getText().toString() + " y haz aceptado el contrato";
            launchNewActivity(genero.getText().toString());
        }

        Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

    public void launchNewActivity(String gender) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(GENERO, genero.getText().toString());
        startActivity(intent);
    }

}