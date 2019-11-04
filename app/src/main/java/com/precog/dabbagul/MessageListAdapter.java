package com.precog.dabbagul;

public class MessageListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<BaseMessage> mMessageList;

    public MessageListAdapter(Context context, List<BaseMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }
}