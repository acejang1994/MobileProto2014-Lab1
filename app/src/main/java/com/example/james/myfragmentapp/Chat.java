package com.example.james.myfragmentapp;

/**
 * Created by james on 9/21/14.
 */
public class Chat {

    private String id, name, message;
    private long date;

    public Chat(String id, String name, String message){
        this.id = id;
        this.name = name;
        this.message = message;
        this.date = System.currentTimeMillis();

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return date;
    }

    public String getMessage() {
        return message;
    }


}
