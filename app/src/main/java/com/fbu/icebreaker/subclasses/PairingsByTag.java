package com.fbu.icebreaker.subclasses;

import java.util.List;

public class PairingsByTag {

    private String pairedTag;
    private List<Hobby> pairedHobbies;

    public String getPairedTag() { return pairedTag; }
    public void setPairedTag(String tag) { pairedTag = tag; }

    public List<Hobby> getPairedHobbies() { return pairedHobbies; }
    public void setPairedHobbies(List<Hobby> hobbies) { pairedHobbies = hobbies; }
}
