package ru.nsu.g.beryanov.messenger.client;

import ru.nsu.g.beryanov.messenger.util.Letter;
import ru.nsu.g.beryanov.messenger.util.SocketBridge;

import java.io.IOException;

public class Listener extends Thread {
    private final Client client;
    private final SocketBridge socketBridge;

    public Listener(Client client, SocketBridge socketBridge) {
        this.client = client;
        this.socketBridge = socketBridge;
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Letter letter = socketBridge.read();
                client.runCommand(letter);
            } catch (IOException ignored) {
            }
        }
    }

    public void send(Letter letter) throws IOException {
        socketBridge.send(letter);
    }
}
