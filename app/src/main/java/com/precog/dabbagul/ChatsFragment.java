package com.precog.dabbagul;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChatsFragment extends BaseFragment {

    public static ArrayList<Request> requests = new ArrayList<Request>();
    public RequestAdapter requestAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_requests, container, false);
        return rootView;

//        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ListView lv = (ListView) view.findViewById(R.id.chats_list);

        requests = new ArrayList<>();
        requestAdapter = new RequestAdapter(this, getActivity(), requests);

        Log.e("CHECK", "" + myProfileObj.email);
        BaseFragment.db.collection("requests").whereEqualTo("receiver_email", "dhruvvermaa@gmail.com")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        loge("CHECK", "yo");
                        if (e != null) {
                            loge("Firebase Error", "Listen Failed");
                            return;
                        }
                        for(DocumentChange dc: queryDocumentSnapshots.getDocumentChanges()) {
                            Request newElem = dc.getDocument().toObject(Request.class);
                            switch (dc.getType()) {
                                case ADDED:
                                    requests.add(newElem);
                                    break;
                                case MODIFIED:
                                    loge("RequestsFragment", "ye kya ho rha hai");
                                    break;
                                case REMOVED:
                                    loge("RequestsFragment", "Removed one :)");
                                    requests.remove(newElem);
                                    break;
                                default:
                                    break;
                            }
                            requestAdapter.notifyDataSetChanged();
                        }
                    }
                });

        Log.e("CHECK", "REQ: " + requestAdapter);
        lv.setAdapter(requestAdapter);
    }
}