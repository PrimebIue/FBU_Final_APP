package com.fbu.icebreaker.hobbyPairingLogic;

import com.fbu.icebreaker.subclasses.Hobby;
import com.fbu.icebreaker.subclasses.PairingsByTag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class HobbyMethods {

    public List<Hobby> getEqualHobbies(List<Hobby> hobbies1, List<Hobby> hobbies2) {
        Set<String> setQr = new HashSet<>();
        List<Hobby> equalHobbies = new ArrayList<>();

        for (Hobby hobby : hobbies1) {
            setQr.add(hobby.getObjectId());
        }

        for (Hobby hobby : hobbies2) {
            if (setQr.contains(hobby.getObjectId())) {
                equalHobbies.add(hobby);
            }
        }
        return equalHobbies;
    }

    public List<PairingsByTag> getSimilarHobbiesByTag(List<Hobby> hobbies1, List<Hobby> hobbies2) {
        List<PairingsByTag> pairingsByTags = new ArrayList<>();
        List<Hobby> equalHobbies = getEqualHobbies(hobbies1, hobbies2);
        Hashtable<String, List<Hobby>> userHobbiesPerTag = new Hashtable<>();
        Hashtable<String, List<Hobby>> qrHobbiesPerTag = new Hashtable<>();
        Set<Hobby> setAlreadyPairedHobbies = new HashSet<>();

        for (Hobby hobby : equalHobbies) {
            setAlreadyPairedHobbies.add(hobby);
        }

        for (Hobby hobby : hobbies1) {
            if (!setAlreadyPairedHobbies.contains(hobby)) {
                for(String tag : hobby.getTags()) {
                    List<Hobby> hobbyListTag;

                    if (userHobbiesPerTag.get(tag) == null) {
                        hobbyListTag = new ArrayList<>();
                    } else {
                        hobbyListTag = userHobbiesPerTag.get(tag);
                    }
                    hobbyListTag.add(hobby);
                    userHobbiesPerTag.put(tag, hobbyListTag);
                }
            }
        }

        for (Hobby hobby : hobbies2) {
            if (!setAlreadyPairedHobbies.contains(hobby)) {
                for (String tag : hobby.getTags()) {
                    List<Hobby> qrHobbyListTag;

                    if (userHobbiesPerTag.get(tag) != null) {
                        if (qrHobbiesPerTag.get(tag) == null) {
                            qrHobbyListTag = new ArrayList<>();
                        } else {
                            qrHobbyListTag = qrHobbiesPerTag.get(tag);
                        }
                        qrHobbyListTag.add(hobby);
                        qrHobbiesPerTag.put(tag, qrHobbyListTag);
                    }
                }
            }
        }

        for (String tag : qrHobbiesPerTag.keySet()) {

            PairingsByTag currPairing = new PairingsByTag();

            qrHobbiesPerTag.get(tag).addAll(userHobbiesPerTag.get(tag));

            currPairing.setPairedTag(tag);
            currPairing.setPairedHobbies(qrHobbiesPerTag.get(tag));
            pairingsByTags.add(currPairing);
        }
        
        return pairingsByTags;
    }
}
