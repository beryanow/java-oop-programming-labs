package ru.nsu.g.beryanov.messenger.util;

import java.io.IOException;
import java.net.Socket;

public interface SocketBridge {
    Socket getSocket();

    void close() throws IOException;

    Letter read() throws IOException;

    void sendSuccess() throws IOException;

    void sendError(String s) throws IOException;

    void send(Letter l) throws IOException;
}
