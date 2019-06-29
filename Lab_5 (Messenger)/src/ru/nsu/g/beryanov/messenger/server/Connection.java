package ru.nsu.g.beryanov.messenger.server;

import ru.nsu.g.beryanov.messenger.util.Letter;
import ru.nsu.g.beryanov.messenger.util.SocketBridge;
import ru.nsu.g.beryanov.messenger.util.serial.SocketBridgeSerial;
import ru.nsu.g.beryanov.messenger.util.xml.SocketBridgeXML;
import ru.nsu.g.beryanov.messenger.server.client.Client;
import ru.nsu.g.beryanov.messenger.server.logger.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Connection extends Thread {
    private final Server server;
    private final InetAddress inetAddress;
    private Client client;
    private boolean isAlive;
    private SocketBridge socketBridge;

    public Client getClient() {
        return client;
    }

    public Connection(Server server, Socket socket) {
        this.isAlive = true;
        this.server = server;
        try {
            switch (server.getVersion()) {
                case "serial":
                    this.socketBridge = new SocketBridgeSerial(socket);
                    break;
                case "xml":
                    this.socketBridge = new SocketBridgeXML(socket);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.isAlive = false;
        }
        this.inetAddress = this.socketBridge.getSocket().getInetAddress();
        this.client = new Client();
        this.start();
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    @Override
    public void run() {
        Logger.log("Client connected from " + this.inetAddress + " in Thread " + this.getId());
        while (isAlive) {
            this.runCommand(this.readCommand());
        }
        this.server.deleteConnection(this);
    }

    private void runCommand(Letter letter) {
        if (letter == null) {
            return;
        }
        /*
         * MESSAGE
         */
        if (letter.getName().equals("message")) {
            String textMessage = letter.getBody().get("name") + ":" + letter.getBody().get("message");
            Logger.log(textMessage);
            sendSuccess();
            sendToAll(letter);
            server.storeMessage(letter);
        }
        /*
         * LOGOUT
         */
        if (letter.getName().equals("logout")) {
            try {
                sendSuccess();
                socketBridge.close();
                Logger.log(letter.getBody().get("name") + " logged out.");
                sendToAll(letter);
                isAlive = false;
            } catch (IOException ignored) {
            }
        }
        /*
         * LOGIN
         */
        if (letter.getName().equals("login")) {
            Client client1 = server.addClient(new Client(letter.getBody().get("name")));
            if (client1 == null) {
                sendError("Current nickname is already used.");
                isAlive = false;
                return;
            }
            client = client1;
            Logger.log(letter.getBody().get("name") + " logged in.");
            sendSuccess();
            sendToAll(letter);
            for (Letter lastletter : this.server.getLastMessages()) {
                send(lastletter);
            }
        }
        /*
         * LIST
         */
        if (letter.getName().equals("listusers")) {
            Logger.log(this.client.getNickname() + " requested list of users.");
            letter.injectUserList(server.getAllUserNames());
            send(letter);
        }
    }

    private void sendToAll(Letter letter) {
        for (Connection connection : server.getAllConnections()) {
            connection.send(letter);
        }
    }

    private void sendError(String error) {
        try {
            socketBridge.sendError(error);
        } catch (IOException e) {
            isAlive = false;
        }
    }

    private void sendSuccess() {
        try {
            socketBridge.sendSuccess();
        } catch (IOException e) {
            isAlive = false;
        }
    }

    private void send(Letter letter) {
        try {
            socketBridge.send(letter);
        } catch (IOException e) {
            this.isAlive = false;
        }
    }

    public boolean isOn() {
        return this.isAlive;
    }

    private Letter readCommand() {
        try {
            return socketBridge.read();
        } catch (IOException e) {
            this.isAlive = false;
        }
        return null;
    }

    public SocketBridge getSocketBridge() {
        return socketBridge;
    }
}
