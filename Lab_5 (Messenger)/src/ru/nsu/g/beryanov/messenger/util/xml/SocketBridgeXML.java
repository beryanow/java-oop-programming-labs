package ru.nsu.g.beryanov.messenger.util.xml;

import ru.nsu.g.beryanov.messenger.util.SocketBridge;
import ru.nsu.g.beryanov.messenger.util.Letter;

import java.io.IOException;
import java.net.Socket;

public class SocketBridgeXML implements SocketBridge {
    private SocketReaderXML socketReaderXML;

    public SocketBridgeXML(Socket socket) throws IOException {
        this.socketReaderXML = new SocketReaderXML(socket);
    }

    public Socket getSocket() {
        return socketReaderXML.getSocket();
    }

    public void close() throws IOException {
        socketReaderXML.close();
    }

    public Letter read() throws IOException {
        return XMLParser.parseIn(socketReaderXML.read());
    }

    public void sendSuccess() throws IOException {
        socketReaderXML.send(XMLParser.makeSuccess());
    }

    public void sendError(String error) throws IOException {
        socketReaderXML.send(XMLParser.makeError(error));
    }

    public void send(Letter letter) throws IOException {
        socketReaderXML.send(XMLParser.parseOut(letter));
    }
}
