package com.example.icebreaker.Subclasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Hobby")
public class Hobby extends ParseObject {

    public static final String KEY_NAME = "name";

    public String getName() { return getString("name"); }
    public void setName(String name) { put(KEY_NAME, name); }
}
