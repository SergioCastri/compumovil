package co.edu.udea.compumovil.gr01_20191.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
  private Spinner spinner;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }
  public void verPlatos(View view){
    Intent i = new Intent(this, PlatosActivity.class);
    startActivity(i);
  }

  public void verBebidas(View view){
    Intent i = new Intent(this, BebidasActivity.class);
    startActivity(i);
  }

}
