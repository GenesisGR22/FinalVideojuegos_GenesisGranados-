package com.example.finalandroid;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalandroid.Entities.Imagen;
import com.example.finalandroid.Entities.ImgBase64;
import com.example.finalandroid.Entities.Movimiento;
import com.example.finalandroid.Services.IImage;
import com.example.finalandroid.Services.IMovimiento;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewmovimientoActivity extends AppCompatActivity {
    ImageButton buttonBase64;
    public String link, elemento;
    public EditText latitudA, longitudA, motivo, monto;
    public double latitud, longitud;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int P_CAMERA = 2;
    public int idCuenta = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmovimiento);

        latitudA = findViewById(R.id.latitud);
        longitudA = findViewById(R.id.longitud);
        motivo = findViewById(R.id.motivo);
        monto = findViewById(R.id.monto);

        Spinner spinner = findViewById(R.id.tipo);
        buttonBase64 = (ImageButton) findViewById(R.id.textBase64);
        Button ubicacion = findViewById(R.id.btnGetUbication);
        Button crear = findViewById(R.id.newMovimiento);

        Intent intent = getIntent();
        idCuenta = intent.getIntExtra("idCuenta",0);

        ArrayList<String> tipos = new ArrayList<>();
        tipos.add("INGRESO");
        tipos.add("GASTO");

        ArrayAdapter adapterArray = new ArrayAdapter(NewmovimientoActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,tipos);
        spinner.setAdapter(adapterArray);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String elementoList = (String) spinner.getAdapter().getItem(i);
                Toast.makeText(getApplicationContext(), elementoList, Toast.LENGTH_SHORT).show();
                elemento = elementoList;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                //nothing
            }
        });


        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MAIN_APP", "2 link " + link);
                Log.i("MAIN_APP", "3 elemento " + elemento);

                String MontoM = monto.getText().toString().trim();;
                String motivoM = motivo.getText().toString().trim();;
                String latitudAM = latitudA.getText().toString().trim();
                String longitudAM = longitudA.getText().toString().trim();;



                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://637427ec08104a9c5f7b28eb.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                IMovimiento service = retrofit.create(IMovimiento.class);
                Movimiento movie = new Movimiento();
                movie.setIdCuenta(idCuenta);
                movie.setMonto(Integer.parseInt(MontoM));
                movie.setTipo(elemento);
                movie.setMotivo(motivoM);
                movie.setLatitud(latitudAM);
                movie.setLongitud(longitudAM);
                movie.setImagen(link);

                Call<Movimiento> call = service.create(movie);

                call.enqueue(new Callback<Movimiento>() {
                    @Override
                    public void onResponse(Call<Movimiento> call, Response<Movimiento> response) {
                        if (response.code() == 200){
                            Log.i("CONEXION","OK");
                        }else{
                            Log.i("CONEXION","BAD");
                        }
                    }

                    @Override
                    public void onFailure(Call<Movimiento> call, Throwable t) {
                        Log.i("CONEXION","FALLO EN EL SERVIDOR");
                        /*AppDataBase db = AppDataBase.getInstance(getApplicationContext());
                        db.iMovieDao().insert(movie);*/
                    }
                });
                Intent intent = new Intent(NewmovimientoActivity.this,MovimientosActivity.class);
                startActivity(intent);
            }
        });

        buttonBase64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PermisoCamara()) {
                    dispatchTakePictureIntent();
                }else{
                    SolicitarPermiso();
                }
            }
        });

        ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(NewmovimientoActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewmovimientoActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewmovimientoActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
                } else {
                    locationStart();
                }
            }
        });

    }

    private boolean PermisoCamara() { // retorna verdadero si la condicion se cumple, si tiene permisos true, sino false, se cambia en CALL_PHONE
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void SolicitarPermiso() {
        String[] permisos = new String[] {Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(this, permisos, P_CAMERA);
    }
    private void dispatchTakePictureIntent () {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.setType("image/");
        Log.i("MYAPP","que es? " + intent.resolveActivity(getPackageManager()));
        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                buttonBase64.setImageBitmap(imageBitmap);

                //final InputStream imageStream = getContentResolver().openInputStream(imageBitmap);
                //final Bitmap selectedImage = BitmapFactory.decodeStream(imageBitmap);

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://api.imgur.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ImgBase64 imagen = new ImgBase64();
                imagen.image = encodeImage(imageBitmap);

                Log.i("MY_APP", "Despues de codificar: "+encodeImage(imageBitmap));

                IImage services = retrofit2.create(IImage.class);
                services.create(imagen).enqueue(new Callback<Imagen>() {
                    @Override
                    public void onResponse(Call<Imagen> call, Response<Imagen> response) {
                        Imagen data = response.body();
                        link = data.data.link;
                        Log.i("MAIN_APP", "1 link " + link);
                    }

                    @Override
                    public void onFailure(Call<Imagen> call, Throwable t) {
                        Log.i("CONEXION","FALLO EN EL SERVIDOR");
                    }
                });

            }else {
                Toast.makeText(this, "No cargo ninguna imagen", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Algo salio mal", Toast.LENGTH_LONG).show();
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {

        NewmovimientoActivity newmovimientoActivity;
        public NewmovimientoActivity getMainActivity() {
            return newmovimientoActivity;
        }

        public void setMainActivity(NewmovimientoActivity mainActivity) {
            this.newmovimientoActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();
            Log.i("CONEXION","Latitud: " + latitud + longitud);
            latitudA.setText("" + loc.getLatitude());
            longitudA.setText("" + loc.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            Toast.makeText(getApplicationContext(), "GPS Desactivado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            Toast.makeText(getApplicationContext(), "GPS Activado", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }
    }

}