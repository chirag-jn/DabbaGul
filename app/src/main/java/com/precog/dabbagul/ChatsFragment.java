package com.precog.dabbagul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatsFragment extends BaseFragment {

    private static final String TAG = "ChatsFragment";

    private RecyclerView chatList;

    private ChatRoomRepository chatRoomRepository;
    private SingleChatRoomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
        chatList = view.findViewById(R.id.chat_list);
        chatRoomRepository = new ChatRoomRepository();
        initUI();
        getChatRooms();
    }

    private void initUI() {
        chatList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getChatRooms() {
        chatRoomRepository.getRooms(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if(e!=null) {
                    loge(TAG, "it failed uff");
                }
                List<ChatRoom> rooms = new ArrayList<>();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    logv(TAG, doc.getId());
                    rooms.add(new ChatRoom(doc.getId(), doc.getString("id1"), doc.getString("id2"), doc.getString("name1"), doc.getString("name2"), doc.getString("dp1"), doc.getString("dp2")));
                }
                adapter = new SingleChatRoomAdapter(rooms, listener);
                chatList.setAdapter(adapter);
//                chatList.setLayoutManager(new LinearLayoutManager(this));
                adapter.notifyDataSetChanged();
            }
        });
    }

    SingleChatRoomAdapter.OnChatRoomClickListener listener = new SingleChatRoomAdapter.OnChatRoomClickListener() {
        @Override
        public void onClick(ChatRoom chatRoom) {
            Intent intent = new Intent(getActivity(), SingleChatActivity.class);
            intent.putExtra(SingleChatActivity.CHAT_ROOM_ID, chatRoom.getId());
            intent.putExtra(SingleChatActivity.CHAT_ROOM_NAME, chatRoom.getOtherName());
            intent.putExtra(SingleChatActivity.CHAT_ROOM_EMAIL, chatRoom.getOtherID());
            intent.putExtra(SingleChatActivity.CHAT_ROOM_DP, chatRoom.getOtherDP());
            startActivity(intent);
        }
    };
}
