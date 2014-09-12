package com.example.james.myfragmentapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by james on 9/11/14.
 */
public class ChatApdapter extends ArrayAdapter<String> {
    public ChatApdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }
}
