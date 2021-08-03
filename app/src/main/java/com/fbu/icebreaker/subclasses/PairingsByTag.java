package com.fbu.icebreaker.subclasses;

import java.util.List;

public class PairingsByTag {

    private List<Hobby> pairedHobbies;
    private String pairedTag;

    public String getPairedTag() {
        return pairedTag;
    }

    public void setPairedTag(String tag) {
        pairedTag = tag;
    }

    public List<Hobby> getPairedHobbies() {
        return pairedHobbies;
    }

    public void setPairedHobbies(List<Hobby> hobbies) {
        pairedHobbies = hobbies;
    }
}
