package ru.nsu.g.beryanov.messenger.server.client;

public class Client {
    private final String nickname;

    public Client() {
        this.nickname = "";
    }

    public Client(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

}
