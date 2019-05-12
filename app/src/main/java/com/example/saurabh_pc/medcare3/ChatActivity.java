package com.example.saurabh_pc.medcare3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    //private static final String TAG = "ChatActivity";
    ChatArrayAdapter chatArrayAdapter;
    EditText chatText;
    ListView chatList;
    Button sendbtn;
    Button begin;
    String  Authorization="";
    String Recieved="";
    Context mContext=this;
    int contentview;
    //boolean typing=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        contentview=R.layout.welcome;
        begin =(Button) findViewById(R.id.begin);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setContentView(R.layout.activity_chat);
                contentview=R.layout.activity_chat;
                int v=R.layout.activity_chat;
                chatList = (ListView) findViewById(R.id.chatList);
                chatText = (EditText) findViewById(R.id.msgtyped);
                changeview();
                chatArrayAdapter = new ChatArrayAdapter(mContext, new ArrayList<ChatMessage>(),chatList);
                chatList.setAdapter(chatArrayAdapter);
                chatArrayAdapter.add(new ChatMessage(1,"typing..."));
                //  chatArrayAdapter.getItem(0).setIsMine(1);
                new Welcome(mContext).execute();
            }
        });

    }
    public void changeview(){
        sendbtn = (Button) findViewById(R.id.send_btn);
        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String message = chatText.getText().toString();
                ChatMessage chatMessage = new ChatMessage(0, message);

                chatArrayAdapter.add(chatMessage);

                JSONObject jsn=new JSONObject();
                try {
                    jsn.put("message",message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new Chat(mContext).execute(Authorization,jsn.toString());
                chatText.setText("");
                chatArrayAdapter.add(new ChatMessage(1,"typing..."));
                chatList.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }
    public void ServerWelcome(JSONObject res){
        try {
            Recieved=(res.getString("message"));
            Log.d("REC",Recieved);
            Authorization=res.getString("uuid");
            chatArrayAdapter.remove((ChatMessage) chatArrayAdapter.chatList.getItemAtPosition(chatArrayAdapter.getCount()-1));
            chatArrayAdapter.notifyDataSetChanged();

            ChatMessage chatMessage = new ChatMessage(1, Recieved);
            chatArrayAdapter.add(chatMessage);
            chatList.setSelection(chatArrayAdapter.getCount() - 1);
        }catch(JSONException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
    //  this method recieves the server response
    public void ServerChat(JSONObject res){
        try {
            Recieved=(res.getString("message"));
            Log.d("nfndskjfnjkdsn   ",Recieved);
            chatArrayAdapter.remove((ChatMessage) chatArrayAdapter.chatList.getItemAtPosition(chatArrayAdapter.getCount()-1));
            chatArrayAdapter.notifyDataSetChanged();

            ChatMessage chatMessage = new ChatMessage(1, Recieved);
            chatArrayAdapter.add(chatMessage);
            chatList.setSelection(chatArrayAdapter.getCount() - 1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
