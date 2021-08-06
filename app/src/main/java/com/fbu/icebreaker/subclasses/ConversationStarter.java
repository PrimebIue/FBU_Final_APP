package com.fbu.icebreaker.subclasses;

import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("ConversationStarters")
public class ConversationStarter extends ParseObject implements Parcelable {

    private static final String KEY_CONVO_STRING = "convoString";
    private static final String KEY_CONVO_TAGS = "convoTags";

    public String getConvoString() { return getString(KEY_CONVO_STRING); }
    public void setConvoString(String convoString) { put(KEY_CONVO_STRING, convoString); }

    public List<String> getConvoTags() { return getList(KEY_CONVO_TAGS); }
    public void setConvoTags(List<String> convoTags) { put(KEY_CONVO_TAGS, convoTags); }
}
