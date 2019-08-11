package co.edu.udea.compumovil.gr01_20191.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class PlatosActivity extends AppCompatActivity {

  ImageView imagen;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_platos);
    imagen = (ImageView) findViewById(R.id.imagenPlato);
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
}
