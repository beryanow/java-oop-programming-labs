package ru.nsu.g.beryanov.messenger.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Letter implements Serializable {
    static final long serialVersionUID = 1L;
    private final String name;
    private final HashMap<String, String> body = new HashMap<>();

    public Letter(String name) {
        this.name = name;
    }

    public HashMap<String, String> getBody() {
        return body;
    }

    public String getName() {
        return name;
    }

    public void injectUserList(LinkedList<String> allUserNames) {
        body.put("listusers", String.join(",", allUserNames));
    }
}
