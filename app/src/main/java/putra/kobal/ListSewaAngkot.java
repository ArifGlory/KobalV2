package putra.kobal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import putra.kobal.Adapter.RecycleAdapteraListSewa;

public class ListSewaAngkot extends AppCompatActivity {

    RecyclerView recycler_listSewa;
    RecycleAdapteraListSewa adapter;
    public static ProgressBar progressBar;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sewa_angkot);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        recycler_listSewa = (RecyclerView) findViewById(R.id.recycler_listsewa);
        adapter = new RecycleAdapteraListSewa(this);
        recycler_listSewa.setAdapter(adapter);
        recycler_listSewa.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        i = new Intent(getApplicationContext(),BerandaActivity.class);
        startActivity(i);

    }

}
