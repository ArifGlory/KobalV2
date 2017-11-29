package putra.kobal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.Calendar;

import putra.kobal.Adapter.RecycleAdapteraChat;
import putra.kobal.Kelas.ChatModel;

public class ChatKeDriver extends AppCompatActivity {

    RecyclerView recycler_chat;
    RecycleAdapteraChat adapter;
    Firebase Kref;
    Button btn_kirim;
    EditText et_pesan;
    String time,pesan;
    String from_id,namaDriver;
    public  static String to_id;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_ke_driver);
        Firebase.setAndroidContext(this);
        Kref = new Firebase("https://kobal-d8264.firebaseio.com/").child("chat");

        i = getIntent();
        et_pesan = (EditText) findViewById(R.id.etEditPesan);
        from_id = BerandaActivity.id;
        to_id = i.getStringExtra("to_id");
        namaDriver = i.getStringExtra("namaDriver");
        getSupportActionBar().setTitle("Kepada : "+namaDriver);

        recycler_chat = (RecyclerView) findViewById(R.id.recycler_chat);
        adapter = new RecycleAdapteraChat(this);
        recycler_chat.setAdapter(adapter);
        recycler_chat.setLayoutManager(new LinearLayoutManager(this));


    }

    public void Kirim(View view) {
        pesan = et_pesan.getText().toString();
        Calendar calendar = Calendar.getInstance();
        int bulan = calendar.get(Calendar.MONTH)+1;
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - System.currentTimeMillis();
        if(timeDifference < oneDayInMillis){
            //formattedTime = DateFormat.format("hh:mm a", System.currentTimeMillis()).toString();
            //Toast.makeText(this,""+calendar.get(Calendar.DATE)+" - "+bulan+" - "+calendar.get(Calendar.YEAR)+" "+DateFormat.format("hh:mm a", System.currentTimeMillis()).toString(),Toast.LENGTH_LONG).show();
            time = ""+calendar.get(Calendar.DATE)+"-"+bulan+"-"+calendar.get(Calendar.YEAR)+" "+ DateFormat.format("hh:mm a", System.currentTimeMillis()).toString();

        }else{
            //formattedTime = DateFormat.format("dd MMM - hh:mm a", System.currentTimeMillis()).toString();
            //Toast.makeText(this,""+calendar.get(Calendar.DATE)+" - "+bulan+" - "+calendar.get(Calendar.YEAR)+" "+DateFormat.format("dd MMM - hh:mm a", System.currentTimeMillis()).toString(),Toast.LENGTH_LONG).show();
            time = ""+calendar.get(Calendar.DATE)+" - "+bulan+" - "+calendar.get(Calendar.YEAR)+" "+DateFormat.format("dd MMM - hh:mm a", System.currentTimeMillis()).toString();

        }

        ChatModel chatModel = new ChatModel(from_id,to_id,pesan,time);
        Kref.push().setValue(chatModel);
        et_pesan.setText(null);
    }
}
