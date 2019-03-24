package co.innovamos.radiocheckbuttons;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static co.innovamos.radiocheckbuttons.MainActivity.GENERO;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnRegresar;
    private TextView txtBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnRegresar = (Button) findViewById(R.id.btn_regresar);
        txtBienvenida = (TextView) findViewById(R.id.txt_bienvenida);

        String s = getIntent().getStringExtra(GENERO);
        txtBienvenida.append(s);
    }

    public void clickRegresar(View view) {
        this.finish();
    }

}
