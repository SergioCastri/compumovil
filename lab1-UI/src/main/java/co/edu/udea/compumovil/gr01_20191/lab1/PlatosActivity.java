package co.edu.udea.compumovil.gr01_20191.lab1;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

public class PlatosActivity extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
  private ImageView imagen;
  private Button botonTiempoPreparacion;
  private EditText tiempoPreparacion;
  private EditText nombrePlato;
  private Button btnRegistrar;
  private EditText precio;
  private EditText ingredientes;
  private TextView informacion;
  private RadioGroup platoFuerte;
  private CheckBox noche;
  private CheckBox manana;
  private CheckBox tarde;
  private Uri mUri;
  private int hora, minutos;
  private String info;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_platos);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);

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
    tarde = (CheckBox) findViewById(R.id.checkBoxTarde);

    if (savedInstanceState != null){
      mUri = savedInstanceState.getParcelable("Imagen");
      info = savedInstanceState.getParcelable("Informacion");
      imagen.setImageURI(mUri);
      informacion.setText(info);
    }
      SharedPreferences sharedPr = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
      nombrePlato.setText(sharedPr.getString("Nombre", ""));
      precio.setText(sharedPr.getString("Precio", ""));
      ingredientes.setText(sharedPr.getString("Ingredientes", ""));
      tiempoPreparacion.setText(sharedPr.getString("Tiempo de preparación", ""));
      informacion.setText(sharedPr.getString("Informacion", ""));


    botonTiempoPreparacion.setOnClickListener(this);
    tiempoPreparacion.setOnClickListener(this);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mUri !=null) {
      outState.putParcelable("Imagen", mUri);
    }
    if (informacion != null) {
      outState.putString("Informacion", informacion.getText().toString());
    }
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    informacion.setText(savedInstanceState.getString("Informacion"));

  }

  public void subirImagen(View view) {
    cargarImagen();
  }

  public void cargarImagen(){
    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/");
    startActivityForResult(intent.createChooser(intent, getString(R.string.seleccioneApp)), 10);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode == RESULT_OK){
      Uri path = data.getData();
      mUri = path;
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
          tiempoHoras =  getString(R.string.hora);

        }else {
          tiempoHoras = getString(R.string.horas);
        }
        if(minutosdia == 1){
          tiempoMinutos = getString(R.string.minuto);
        }else {
          tiempoMinutos = getString(R.string.minutos);
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

    String labelIngredientes = getString(R.string.ingredientes);
    String labelNombre = getString(R.string.nombreDelPlato);
    String labelPrecio = getString(R.string.precio);
    String labelTiempo = getString(R.string.tiempoDePreparacion);
    String labelInformacion= getString(R.string.informacionGeneral);

    SharedPreferences sharedP = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedP.edit();
    editor.putString("Nombre", textNombre);
    editor.putString("Tiempo de preparación", textTiempoPreparacion);
    editor.putString("Precio", textPrecio);
    editor.putString("Ingredientes", textIngredientes);
    informacion.setText(String.format("%s\n\n%s: %s\n%s: %s\n%s: %s\n%s: %s", labelInformacion, labelNombre, textNombre, labelTiempo, textTiempoPreparacion, labelIngredientes, textIngredientes, labelPrecio, textPrecio));
    String textInformacion= informacion.getText().toString();

    editor.putString("Informacion", textInformacion);
    editor.apply();
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.platos, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement


    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.limpiar) {

      nombrePlato.setText("");
      precio.setText("");
      ingredientes.setText("");
      tiempoPreparacion.setText("");
      informacion.setText("");
      imagen.setImageResource(R.drawable.ic_menu_gallery);
    } else if (id == R.id.salir) {
      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intent.putExtra("EXIT", true);
      startActivity(intent);
    }
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }
}
