package co.edu.udea.compumovil.gr01_20191.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class PlatosActivity extends AppCompatActivity implements View.OnClickListener {

  ImageView imagen;
  Button botonTiempoPreparacion;
  EditText tiempoPreparacion;
  EditText nombrePlato;
  Button btnRegistrar;
  EditText precio;
  EditText ingredientes;
  TextView informacion;
  RadioGroup platoFuerte;
  CheckBox noche;
  CheckBox manana;

  private int hora, minutos;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_platos);

    btnRegistrar = (Button) findViewById(R.id.registrar);

    imagen = (ImageView) findViewById(R.id.imagenPlato);
    botonTiempoPreparacion = (Button) findViewById(R.id.tiempoPreparacion);
    tiempoPreparacion =  (EditText) findViewById(R.id.tiempo);
    nombrePlato = (EditText) findViewById(R.id.nombrePlato);
    precio = (EditText) findViewById(R.id.precio);
    ingredientes = (EditText) findViewById(R.id.ingredientes);
    informacion = (TextView) findViewById(R.id.informacion);
    platoFuerte = (RadioGroup) findViewById(R.id.radioGrupo);
    noche = (CheckBox) findViewById(R.id.checkBoxNoche);
    manana = (CheckBox) findViewById(R.id.checkBoxManana);

    SharedPreferences sharedPr = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
    nombrePlato.setText(sharedPr.getString("Nombre", ""));
    precio.setText(sharedPr.getString("Precio","" ));
    ingredientes.setText(sharedPr.getString("Ingredientes","" ));
    tiempoPreparacion.setText(sharedPr.getString("Tiempo de preparación","" ));



    botonTiempoPreparacion.setOnClickListener(this);
    tiempoPreparacion.setOnClickListener(this);
  }

  public void subirImagen(View view) {
    cargarImagen();
  }

  public void cargarImagen(){
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/");
    startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"), 10);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK){
      Uri path = data.getData();
      imagen.setImageURI(path);
    }
  }

  @Override
  public void onClick(View view) {

        final Calendar c = Calendar.getInstance();
        hora = c.get(Calendar.HOUR_OF_DAY);
        minutos = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
          @Override
          public void onTimeSet(TimePicker timePicker, int horaDia, int minutosdia) {
            String tiempoHoras, tiempoMinutos;
            if(horaDia == 1){
              tiempoHoras = " Hora y ";
            }else {
              tiempoHoras = " Horas y ";
            }
            if(minutosdia == 1){
              tiempoMinutos = " Minuto ";
            }else {
              tiempoMinutos = " Minutos ";
            }
            tiempoPreparacion.setText(horaDia + tiempoHoras + minutosdia + tiempoMinutos);
          }
        }, hora, minutos, false);
        timePickerDialog.show();


  }

  public void registrar(View view){
    String textNombre= nombrePlato.getText().toString();
    String textPrecio= precio.getText().toString();
    String textIngredientes= ingredientes.getText().toString();
    String textTiempoPreparacion= tiempoPreparacion.getText().toString();

    SharedPreferences sharedP = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedP.edit();
    editor.putString("Nombre", textNombre);
    editor.putString("Tiempo de preparación", textTiempoPreparacion);
    editor.putString("Precio", textPrecio);
    editor.putString("Ingredientes", textIngredientes);
    editor.apply();
    informacion.setText(String.format("Información del plato \nNombre: %s\nPrecio: %s\nIngredientes: %s\nTiempo de preparación: %s\n", textNombre, textPrecio, textIngredientes, textTiempoPreparacion));
  }
}
