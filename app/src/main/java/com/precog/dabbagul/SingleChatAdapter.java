package com.precog.dabbagul;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SingleChatAdapter extends RecyclerView.Adapter<SingleChatAdapter.ChatViewHolder> {
    private static final int SENT = 0;
    private static final int RECEIVED = 1;

    private String userId;
    private List<ChatMessage> chats;

    public SingleChatAdapter(List<ChatMessage> chats, String userId) {
        this.chats = chats;
        this.userId = userId;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_my_message,
                    parent,
                    false
            );
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_chat_single_other_message,
                    parent,
                    false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.bind(chats.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getSenderId().contentEquals(userId)) {
            return SENT;
        } else {
            return RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public ChatViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.text_message_body);
        }

        public void bind(ChatMessage chat) {
            message.setText(chat.getMessage());
        }
    }
}