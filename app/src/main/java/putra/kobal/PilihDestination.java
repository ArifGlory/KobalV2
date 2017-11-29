package putra.kobal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.UnsupportedEncodingException;
import java.util.List;

import putra.kobal.Kelas.SewaModel;
import putra.kobal.Modules.DirectionKobal;
import putra.kobal.Modules.DirectionKobalListener;
import putra.kobal.Modules.Route;

public class PilihDestination extends AppCompatActivity implements DirectionKobalListener {

    TextView txtNamaAsal,txtAlamatAsal,txtNamaTujuan,txtAlamatTujuan,txtBiaya,txtJarak,txtWaktu;
    Button btnPilihTujuan,btnKirim;
    private int PLACE_PICKER_REQUEST = 1;
    private String namaAsal,alamatAsal,namaTujuan,alamatTujuan,jarak,waktu,biaya,origin,destination,jenis;
    private String latAsal,lonAsal,key,userID,nope,jenisSewa;
    private Double latTujuan,lonTujuan,distance;
    Intent i;
    private ProgressBar progressBar;
    private int biayaOrang = 7000;
    private int biayaBarang = 6000;
    private  int hasil;

    Firebase Kref,Uref;
    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    SewaModel sewaModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_destination);
        Firebase.setAndroidContext(this);
        fAuth = FirebaseAuth.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        btnKirim = (Button) findViewById(R.id.btnKirim);
        btnPilihTujuan = (Button) findViewById(R.id.btnPilihTujuan);
        txtAlamatAsal = (TextView) findViewById(R.id.txtAlamatAsal);
        txtNamaAsal = (TextView) findViewById(R.id.txtNamaAsal);
        txtAlamatTujuan = (TextView) findViewById(R.id.txtAlamatTujuan);
        txtNamaTujuan = (TextView) findViewById(R.id.txtNamaTujuan);
        txtBiaya = (TextView) findViewById(R.id.txtBiaya);
        txtWaktu = (TextView) findViewById(R.id.txtWaktu);
        txtJarak = (TextView) findViewById(R.id.txtJarak);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        i = getIntent();

        namaAsal = i.getStringExtra("nama");
        alamatAsal = i.getStringExtra("alamat");
        latAsal = i.getStringExtra("lat");
        lonAsal = i.getStringExtra("lon");
        jenis = i.getStringExtra("jenis");
        key = i.getStringExtra("key");

        Kref = new Firebase("https://kobal-d8264.firebaseio.com/").child("driver").child(key).child("zlist");
        Uref = new Firebase("https://kobal-d8264.firebaseio.com/").child("user").child(userID);

        txtNamaAsal.setText(namaAsal);
        txtAlamatAsal.setText(alamatAsal);

        try {

            Uref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    progressBar.setVisibility(View.VISIBLE);

                    nope = (String) dataSnapshot.child("nope").getValue();

                    progressBar.setVisibility(View.GONE);

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }catch (Exception e){
            Log.e("Eror Ambil data user","Erornya : "+e);
            Toast.makeText(getApplicationContext(),"Gagal Ambil data User: "+e.toString() ,Toast.LENGTH_LONG).show();
        }

        btnPilihTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(PilihDestination.this), PLACE_PICKER_REQUEST);

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


        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpanSewa();
            }
        });


    }

    private void simpanSewa(){

        sewaModel = new SewaModel(fAuth.getCurrentUser().getDisplayName(),
                nope,
                fAuth.getCurrentUser().getEmail(),
                alamatAsal,
                alamatTujuan,
                origin,
                destination,
                biaya,
                txtJarak.getText().toString(),
                txtWaktu.getText().toString(),
                jenisSewa);

        Kref.push().setValue(sewaModel);
        Toast.makeText(getApplicationContext(),"Permintaan Sewa Dikirim Ke Driver",Toast.LENGTH_LONG).show();

        i = new Intent(getApplicationContext(),ListSewaAngkot.class);
        startActivity(i);
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

                txtAlamatTujuan.setText(place.getAddress());
                txtNamaTujuan.setText(place.getName());

                alamatTujuan = (String) place.getAddress();
                namaTujuan = (String) place.getName();

                latTujuan = place.getLatLng().latitude;
                lonTujuan = place.getLatLng().longitude;

                kirimRequest();


            }
        }

    }

    private void kirimRequest(){
        progressBar.setVisibility(View.VISIBLE);
        origin = latAsal+ ","+lonAsal;
        destination = String.valueOf(latTujuan)+","+String.valueOf(lonTujuan);

        if (origin.isEmpty() || origin.equals("")){
            Toast.makeText(getApplicationContext(), "Origin address kosong !", Toast.LENGTH_SHORT).show();
            return;
        } else if (destination.isEmpty() || destination.equals("")){
            Toast.makeText(getApplicationContext(), "Destination address kosong !", Toast.LENGTH_SHORT).show();
            return;
        } else {
          //  Toast.makeText(getApplicationContext(),"Origin : "+origin+"& Destination = "+destination,Toast.LENGTH_SHORT).show();

            try {
                new DirectionKobal(this, origin, destination).execute();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Eror Direction kobal : "+e.toString(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onDirectionKobalStart() {

        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void onDirectionKobalSuccess(List<Route> routes) {
        progressBar.setVisibility(View.GONE);

        int no = 0;
        for (Route route : routes) {

            txtWaktu.setText("Jarak = "+route.duration.text);
            txtJarak.setText("Waktu = "+route.distance.text);
            String jarakS = route.distance.text;
            String jarakhasil = jarakS.substring(0,jarakS.lastIndexOf(" "));
            distance = Double.valueOf((String.valueOf(jarakhasil)));



            no++;
        }
        if (jenis.equals("0")){
            hasil = (int) (distance * biayaOrang);
            jenisSewa = "Angkutan Orang";
        }else {
            hasil = (int) (distance * biayaBarang);
            jenisSewa = "Angkutan Barang";
        }
        biaya = String.valueOf(hasil);


        txtBiaya.setText("Estimasi Biaya = Rp."+hasil);
        Toast.makeText(getApplicationContext(),"Jarak = "+distance,Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),"Biaya = "+hasil,Toast.LENGTH_SHORT).show();
        btnKirim.setEnabled(true);
    }
}
