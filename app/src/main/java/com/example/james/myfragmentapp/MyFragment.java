package com.example.james.myfragmentapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by james on 9/11/14.
 */
public class MyFragment extends Fragment{

    ChatAdapter chatApdapter;
    Context context;


    public MyFragment()  {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);

        ListView myListView = (ListView) rootView.findViewById(R.id.my_list_view);
        final HandlerDatabase handler = new HandlerDatabase(context);
        Log.i("opening database", "Debug");
        handler.open();

        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle("Delete or Edit Chat")
                .setMessage("Delete this Entry?");

        final EditText edit = new EditText(context);
        alert.setView(edit);
        alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String change = edit.getText();
                    }
                })
                .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        final ArrayList<Chat> listChats = new ArrayList<Chat>();

        final ChatAdapter adapter = new ChatAdapter(getActivity(),R.layout.chat_item, listChats);
        myListView.setAdapter(adapter);

        final EditText editText = (EditText)rootView.findViewById(R.id.my_edittext);

        Button myButton = (Button)rootView.findViewById(R.id.my_button);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                Chat chat = new Chat("id", "user", message);
                handler.addChatToDatabase("id", message);

                listChats.add(chat);
                editText.getText().clear();
                adapter.notifyDataSetChanged();
//                Log.i("debug","button");

             }
        });


        return rootView;
    }
}
