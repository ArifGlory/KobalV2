package putra.kobal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import putra.kobal.Adapter.RecycleAdapteraListPermintaan;

public class PermintaanSewa extends AppCompatActivity {

    public static TextView txt_infoPS;
    public static ProgressBar progressBar;

     Intent i;
    RecyclerView recycler_permintaan;
    RecycleAdapteraListPermintaan adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permintaan_sewa);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txt_infoPS = (TextView) findViewById(R.id.txtInfoPS);
        recycler_permintaan = (RecyclerView) findViewById(R.id.recycler_permintaanSewa);

        adapter = new  RecycleAdapteraListPermintaan(this);
        recycler_permintaan.setAdapter(adapter);
        recycler_permintaan.setLayoutManager(new LinearLayoutManager(this));

    }



    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        i = new Intent(getApplicationContext(),BerandaDriver.class);
        startActivity(i);

    }
}
