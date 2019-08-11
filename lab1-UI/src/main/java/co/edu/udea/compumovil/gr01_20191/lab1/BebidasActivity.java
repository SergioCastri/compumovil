package co.edu.udea.compumovil.gr01_20191.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BebidasActivity extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener{
  private ImageView imagen;
  private EditText nombreBebida;
  private Button btnRegistrar;
  private EditText precioBebida;
  private EditText ingredientesBebida;
  private TextView informacionBebida;
  private Uri mUri;
  private String info;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bebidas);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    NavigationView navigationView = findViewById(R.id.nav_view);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    navigationView.setNavigationItemSelectedListener(this);

    btnRegistrar = (Button) findViewById(R.id.registrarBebida);
    imagen = (ImageView) findViewById(R.id.imagenBebida);
    nombreBebida = (EditText) findViewById(R.id.nombreBebida);
    precioBebida = (EditText) findViewById(R.id.precioBebida);
    ingredientesBebida = (EditText) findViewById(R.id.ingredientesBebida);
    informacionBebida = (TextView) findViewById(R.id.informacionBebida);

    if (savedInstanceState != null){
      mUri = savedInstanceState.getParcelable("ImagenBebida");
      info = savedInstanceState.getParcelable("InformacionBebida");
      imagen.setImageURI(mUri);
      informacionBebida.setText(info);
    }
    SharedPreferences sharedPr = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
    nombreBebida.setText(sharedPr.getString("NombreBebida", ""));
    precioBebida.setText(sharedPr.getString("PrecioBebida", ""));
    ingredientesBebida.setText(sharedPr.getString("IngredientesBebida", ""));
    informacionBebida.setText(sharedPr.getString("InformacionBebida", ""));
    btnRegistrar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registrarBebida(v);
      }
    });

  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (mUri !=null) {
      outState.putParcelable("ImagenBebida", mUri);
    }
    if (informacionBebida != null) {
      outState.putString("InformacionBebida", informacionBebida.getText().toString());
    }
  }

  @Override
  protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    informacionBebida.setText(savedInstanceState.getString("InformacionBebida"));

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
      mUri = path;
      imagen.setImageURI(path);
    }
  }

  public void registrarBebida(View view){
    String textNombre= nombreBebida.getText().toString();
    String textPrecio= precioBebida.getText().toString();
    String textIngredientes= ingredientesBebida.getText().toString();

    String labelIngredientes = getString(R.string.ingredientes);
    String labelNombre = getString(R.string.nombreDeLaBebida);
    String labelPrecio = getString(R.string.precio);
    String labelInformacion= getString(R.string.informacionGeneral);

    SharedPreferences sharedP = getSharedPreferences("archivoSP", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedP.edit();
    editor.putString("NombreBebida", textNombre);
    editor.putString("PrecioBebida", textPrecio);
    editor.putString("IngredientesBebida", textIngredientes);
    informacionBebida.setText(String.format("%s\n\n%s: %s\n%s: %s\n%s: %s", labelInformacion, labelNombre, textNombre, labelPrecio, textPrecio, labelIngredientes, textIngredientes));
    String textInformacion= informacionBebida.getText().toString();

    editor.putString("InformacionBebida", textInformacion);
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

      nombreBebida.setText("");
      precioBebida.setText("");
      ingredientesBebida.setText("");
      informacionBebida.setText("");
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
