package com.precog.dabbagul;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SingleChatActivity extends BaseActivity {

    private static final String TAG = "SingleChatActivity";

    public static final String CHAT_ROOM_ID = "CHAT_ROOM_ID";
    public static final String CHAT_ROOM_NAME = "CHAT_ROOM_NAME";
    public static final String CHAT_ROOM_DP = "CHAT_ROOM_DP";
    public static final String CHAT_ROOM_EMAIL = "CHAT_ROOM_EMAIL";

    public String room_id;
    public String other_name;
    public String other_dp;
    public String other_email;

    private ChatRoomRepository chatRoomRepository;
//    private String userId = "";
    private EditText message;
    private Button send;

    private Context mContext;

    private RecyclerView chats;
    private SingleChatAdapter adapter;

//    private TextView otherUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);

        Intent intent = getIntent();
        room_id = intent.getStringExtra(CHAT_ROOM_ID);
        other_dp = intent.getStringExtra(CHAT_ROOM_DP);
        other_email = intent.getStringExtra(CHAT_ROOM_EMAIL);
        other_name = intent.getStringExtra(CHAT_ROOM_NAME);

        send = findViewById(R.id.button_chatbox_send);
        message = findViewById(R.id.edittext_chatbox);
        chats = findViewById(R.id.recylerview_message_list);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        chats.setLayoutManager(manager);

        mContext = this;

        chatRoomRepository = new ChatRoomRepository();

        logv(TAG, room_id);
        logv(TAG, other_dp);
        logv(TAG, other_email);
        logv(TAG, other_name);

        if(getSupportActionBar()!=null) {
            setTitle(other_name);
        }

        initUI();

        showChatMessages();

    }

    private void initUI() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getText().toString().isEmpty()) {
                    Toast.makeText(
                            mContext,
                            getString(R.string.error_message_failed),
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    addMessageToChatRoom();
                }
            }
        });
    }

    private void addMessageToChatRoom() {
        String chatMessage = message.getText().toString();
        message.setText("");
        send.setEnabled(false);
        chatRoomRepository.addMessageToChatRoom(
                room_id,
                userEmail,
                chatMessage,
                new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        send.setEnabled(true);
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        send.setEnabled(true);
                        Toast.makeText(
                                mContext,
                                getString(R.string.error_message_failed),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );
    }


    private void showChatMessages() {
        chatRoomRepository.getChats(room_id, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e(TAG, "listen failed", e);
                    loge(TAG, "Listen failed.");
                    return;
                }

                List<ChatMessage> messages = new ArrayList<>();
                for (QueryDocumentSnapshot doc : snapshots) {
                    logv(TAG, doc.getString("message"));
                    messages.add(
                            new ChatMessage(
                                    doc.getId(),
                                    doc.getString("chat_room_id"),
                                    doc.getString("sender_id"),
                                    doc.getString("message"),
                                    doc.getLong("sent")
                            )
                    );
                }

                adapter = new SingleChatAdapter(messages, userEmail);
                chats.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
