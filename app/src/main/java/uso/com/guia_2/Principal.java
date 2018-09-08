package uso.com.guia_2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class Principal extends AppCompatActivity {
    //Declaracion de Variables
    private EditText txtURL;
    private TextView lblEstado;
    private Button btnDescargar;

    private RadioButton rdbtnCambio;
    private RadioButton rdbtnNoCambio;
    private EditText txtNombreArchivo;

    private ProgressBar prbPorcentaje;
    private Boolean CambioNombre = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Inicializacion de Variables
        txtURL       = findViewById(R.id.txtURL);
        lblEstado    = findViewById(R.id.lblEstado);
        btnDescargar = findViewById(R.id.btnDescargar);

        rdbtnCambio = findViewById(R.id.rdbtnCambio);
        rdbtnNoCambio = findViewById(R.id.rdbtnNoCambio);
        txtNombreArchivo = findViewById(R.id.txtNombreArchivo);

        prbPorcentaje = findViewById(R.id.prbPorcentaje);

        verifyStoragePermissions(this);

        rdbtnCambio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txtNombreArchivo.setEnabled(true);
                    txtNombreArchivo.setHint("Nombre_del_archivo.extensión");
                    CambioNombre = true;
                }
            }
        });
        rdbtnNoCambio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    txtNombreArchivo.setEnabled(false);
                    txtNombreArchivo.setHint("");
                    CambioNombre = false;
                }
            }
        });
    }

    public void BotonDescargar(View v){
        if(CambioNombre){
            new Descargar(Principal.this, lblEstado, btnDescargar, prbPorcentaje, txtNombreArchivo.getText().toString()).execute(txtURL.getText().toString());
        }else{
            new Descargar(Principal.this, lblEstado, btnDescargar, prbPorcentaje, "None").execute(txtURL.getText().toString());
        }
    }

    //esto es para activar perimiso de escritura y lectura en versiones de android 6 en adelante
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //persmission method.
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
