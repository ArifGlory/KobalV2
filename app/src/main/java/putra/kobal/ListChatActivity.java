package putra.kobal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import putra.kobal.Adapter.RecycleAdapteraChat;
import putra.kobal.Adapter.RecycleAdapteraListChat;

public class ListChatActivity extends AppCompatActivity {

    RecyclerView recycler_listchat;
    RecycleAdapteraListChat adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat);

        recycler_listchat = (RecyclerView) findViewById(R.id.recycler_listchat);

        adapter = new RecycleAdapteraListChat(this);
        recycler_listchat.setAdapter(adapter);
        recycler_listchat.setLayoutManager(new LinearLayoutManager(this));
    }
}
