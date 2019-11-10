package com.precog.dabbagul;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomRepository {
    private static final String TAG = "ChatRoomRepository";

    private CollectionReference chatsDB = BaseActivity.chatsDB;

    public void getRooms(EventListener<QuerySnapshot> listener) {
//        chatsDB.
        //        chatsDB.whereEqualTo("id1", "id2")
        chatsDB.orderBy("id1")
                .addSnapshotListener(listener);
    }


    public void getChats(String roomId, EventListener<QuerySnapshot> listener) {
        chatsDB
                .whereEqualTo("chat_room_id", roomId)
                .orderBy("sent", Query.Direction.DESCENDING)
                .addSnapshotListener(listener);
    }

    public void addMessageToChatRoom(String roomId,
                                     String senderId,
                                     String message,
                                     final OnSuccessListener<DocumentReference> successCallback,
                                     final OnFailureListener failureCallback) {
        Map<String, Object> chat = new HashMap<>();
        chat.put("chat_room_id", roomId);
        chat.put("sender_id", senderId);
        chat.put("message", message);
        chat.put("sent", System.currentTimeMillis());

        chatsDB
                .add(chat)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        successCallback.onSuccess(documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        failureCallback.onFailure(e);
                    }
                });
    }

}
