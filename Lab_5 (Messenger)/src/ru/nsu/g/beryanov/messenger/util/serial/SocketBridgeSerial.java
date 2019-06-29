package ru.nsu.g.beryanov.messenger.util.serial;

import ru.nsu.g.beryanov.messenger.util.Letter;
import ru.nsu.g.beryanov.messenger.util.SocketBridge;

import java.io.IOException;
import java.net.Socket;

public class SocketBridgeSerial implements SocketBridge {
    private SocketReaderSerial socketReaderSerial;

    public SocketBridgeSerial(Socket socket) throws IOException {
        this.socketReaderSerial = new SocketReaderSerial(socket);
    }

    public Socket getSocket() {
        return socketReaderSerial.getSocket();
    }

    public void close() throws IOException {
        socketReaderSerial.close();
    }

    public Letter read() throws IOException {
        return (Letter) socketReaderSerial.read();
    }

    public void sendSuccess() {
    }

    public void sendError(String error) throws IOException{
        Letter letter = new Letter("error");
        letter.getBody().put("message", error);
        socketReaderSerial.send(letter);
    }

    public void send(Letter letter) throws IOException {
        socketReaderSerial.send(letter);
    }
}