package com.example.james.myfragmentapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by james on 9/11/14.
 */
public class MyFragment extends Fragment{

    ChatApdapter chatApdapter;
    Context context;


    public MyFragment()  {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        ListView myListView = (ListView) rootView.findViewById(R.id.my_list_view);
        final HandlerDatabase handler = new HandlerDatabase(context);
        Log.i("opening database", "Debug");
        handler.open();



        final ArrayList<Chat> listChats = new ArrayList<Chat>();

        final ChatApdapter adapter = new ChatApdapter(getActivity(),R.layout.chat_item, listChats);
        myListView.setAdapter(adapter);

        final EditText editText = (EditText)rootView.findViewById(R.id.my_edittext);

        Button myButton = (Button)rootView.findViewById(R.id.my_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                Chat chat = new Chat("id", "user", message);
                handler.addChatToDatabase("id", message);
                handler.updateChat(chat);


                listChats.add(chat);
                editText.getText().clear();
                adapter.notifyDataSetChanged();
//                Log.i("debug","button");

             }
        });


        return rootView;
    }
}
