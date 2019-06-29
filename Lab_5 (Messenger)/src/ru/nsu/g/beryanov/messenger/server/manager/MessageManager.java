package ru.nsu.g.beryanov.messenger.server.manager;

import ru.nsu.g.beryanov.messenger.util.Letter;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private final List<Letter> messages;

    public MessageManager() {
        this.messages = new ArrayList<>();
    }

    public void store(Letter letter) {
        this.messages.add(letter);
    }

    public List<Letter> getMessages(Integer history) {
        return this.messages.subList(Math.max(this.messages.size() - history, 0), this.messages.size());
    }
}
