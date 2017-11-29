package putra.kobal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PilihOrigin extends AppCompatActivity {

    TextView txtNamaTempat,txtAlamat;
    Button btnPilihLokasi,btnLanjut;
    Spinner sp_jenis;
    private int PLACE_PICKER_REQUEST = 1;
    private String namaTempat,alamat,key;
    private Double Glat, Glon;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_origin);

        sp_jenis = (Spinner) findViewById(R.id.sp_jenis);
        btnLanjut = (Button) findViewById(R.id.btnLanjut);
        btnPilihLokasi = (Button) findViewById(R.id.btnPilihOrigin);
        txtNamaTempat = (TextView) findViewById(R.id.txtNamaTempat);
        txtAlamat = (TextView) findViewById(R.id.txtAlamat);

        i = getIntent();
        key = i.getStringExtra("key");

        btnPilihLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(PilihOrigin.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    Log.d("eror picker 1 = ",e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Log.d("eror picker 2 = ",e.toString());
                }

            }
        });

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String jenis = String.valueOf(sp_jenis.getSelectedItemPosition());
                i = new Intent(getApplicationContext(),PilihDestination.class);
                i.putExtra("nama",namaTempat);
                i.putExtra("alamat",alamat);
                i.putExtra("lat",Glat.toString());
                i.putExtra("lon",Glon.toString());
                i.putExtra("jenis",jenis);
                i.putExtra("key",key);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format(
                        "Place: %s \n" +
                                "Alamat: %s \n" +
                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
                //tvPlaceAPI.setText(toastMsg);
                //Toast.makeText(getApplicationContext()," "+toastMsg,Toast.LENGTH_SHORT).show();

                txtAlamat.setText(place.getAddress());
                txtNamaTempat.setText(place.getName());

                alamat = (String) place.getAddress();
                namaTempat = (String) place.getName();

                Glat = place.getLatLng().latitude;
                Glon = place.getLatLng().longitude;
                btnLanjut.setEnabled(true);

            }
        }

    }


}
