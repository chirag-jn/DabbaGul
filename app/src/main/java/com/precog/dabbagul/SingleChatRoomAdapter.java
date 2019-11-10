package com.precog.dabbagul;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SingleChatRoomAdapter extends RecyclerView.Adapter<SingleChatRoomAdapter.SingleChatRoomViewHolder> {

    interface OnChatRoomClickListener {
        void onClick(ChatRoom chatRoom);
    }

    private List<ChatRoom> chatRooms;

    private OnChatRoomClickListener listener;

    public SingleChatRoomAdapter(List<ChatRoom> chatRooms, OnChatRoomClickListener listener) {
        this.chatRooms = chatRooms;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(SingleChatRoomViewHolder holder, int position) {
        holder.bind(chatRooms.get(position));
    }

    @Override
    public int getItemCount() {
        Log.v("ChatsFragment", chatRooms.size()+"");
        return chatRooms.size();
    }

    @Override
    public SingleChatRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("ChatsFragment", "hi");
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_chat_item,
                parent,
                false
        );
        return new SingleChatRoomViewHolder(view, listener);
    }

    class SingleChatRoomViewHolder extends RecyclerView.ViewHolder {
        TextView email;
        ChatRoom chatRoom;
        TextView name;

        public SingleChatRoomViewHolder(View itemView, SingleChatRoomAdapter.OnChatRoomClickListener listener) {
            super(itemView);
            email = itemView.findViewById(R.id.chat_item_email);
            name = itemView.findViewById(R.id.chat_item_person_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(chatRoom);
                }
            });
        }

        public void bind(ChatRoom chatRoom) {
            this.chatRoom = chatRoom;
            email.setText(chatRoom.getId1());
            name.setText(chatRoom.getOtherName());
            Log.v("ChatsFragment", chatRoom.getOtherName());

        }
    }

}
