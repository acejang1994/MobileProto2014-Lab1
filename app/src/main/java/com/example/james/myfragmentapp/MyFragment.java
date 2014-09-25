package com.example.james.myfragmentapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    String userName = "James";


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
        final HandlerDatabase handler = new HandlerDatabase(context);
        handler.open();
        ListView myListView = (ListView) rootView.findViewById(R.id.my_list_view);

        final ArrayList<Chat> listChats = handler.getAllChats();

        final ChatAdapter adapter = new ChatAdapter(getActivity(),R.layout.chat_item, listChats);
        myListView.setAdapter(adapter);

        final EditText editText = (EditText)rootView.findViewById(R.id.my_edittext);


        Log.i("opening database", "Debug");


        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Delete or Edit Chat")
                        .setMessage("Delete this Entry or Edit?");

                final EditText edit = new EditText(context);
                alert.setView(edit);
                alert.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String change = edit.getText().toString();
                        Chat chat = adapter.getItem(i);
                        chat.setMessage(change);

                        handler.addChatToDatabase(chat);
                        adapter.notifyDataSetChanged();

                    }
                })
                        .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                Chat chat = adapter.getItem(i);
                                String id = chat.getId();
                                handler.deleteChatById(id);
                                listChats.remove(i);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        });



        Button myButton = (Button)rootView.findViewById(R.id.my_button);
        Button changeUser = (Button)rootView.findViewById(R.id.user_button);
        changeUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setMessage("Change Username");

                final EditText edit = new EditText(context);
                alert.setView(edit);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        String change = edit.getText().toString();
                        userName = change;

                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)

                        .show();



            }
        });
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();

                Chat chat = new Chat(userName, userName, message);
                chat.setId(userName + chat.getTime());
                handler.addChatToDatabase(chat);

                listChats.add(chat);
                editText.getText().clear();
                adapter.notifyDataSetChanged();
//                Log.i("debug","button");

             }
        });


        return rootView;
    }
}
