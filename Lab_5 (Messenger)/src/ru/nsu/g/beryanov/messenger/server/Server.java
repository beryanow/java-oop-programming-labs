package ru.nsu.g.beryanov.messenger.server;

import ru.nsu.g.beryanov.messenger.util.Letter;
import ru.nsu.g.beryanov.messenger.server.client.Client;
import ru.nsu.g.beryanov.messenger.server.logger.Logger;
import ru.nsu.g.beryanov.messenger.server.manager.ClientManager;
import ru.nsu.g.beryanov.messenger.server.manager.ConnectionsManager;
import ru.nsu.g.beryanov.messenger.server.manager.MessageManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

public class Server {
    private final String version;
    private final ClientManager clientManager;
    private final ConnectionsManager connectionsManager;
    private final MessageManager messageManager;
    private final Properties properties;
    private ServerSocket serverSocket;

    public Server(String version) {
        this.version = version;
        this.properties = new Properties();
        try {
            this.properties.load(this.getClass().getResourceAsStream("Server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!this.properties.containsKey("port")) {
            this.properties.setProperty("port", "8888");
        }
        if (!this.properties.containsKey("history")) {
            this.properties.setProperty("history", "10");
        }
        if (!this.properties.containsKey("logFile")) {
            this.properties.setProperty("logFile", "logs.txt");
        }
        if (!this.properties.containsKey("maxUsers")) {
            this.properties.setProperty("maxUsers", "32");
        }

        /*CREATING LOGGER*/
        Logger.setLogFile(this.properties.getProperty("logFile"));

        try {
            serverSocket = new ServerSocket(Integer.parseInt(properties.getProperty("port")));
            Logger.log("Server on port " + properties.getProperty("port") + " is on.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.clientManager = new ClientManager();
        this.connectionsManager = new ConnectionsManager();
        this.connectionsManager.setLimit(Integer.valueOf(this.properties.getProperty("maxUsers")));
        this.messageManager = new MessageManager();

        Timer timer = new Timer();
        timer.schedule(this.connectionsManager, 0, 30000);//every 10 sec
    }

    public LinkedList<String> getAllUserNames() {
        LinkedList<String> userNames = new LinkedList<>();
        for (Client client : clientManager.getAllClients())
            userNames.add(client.getNickname());
        return userNames;
    }

    public void run() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            this.connectionsManager.add(new Connection(this, socket));
        }
    }

    public LinkedList<Connection> getAllConnections() {
        return this.connectionsManager.getConnections();
    }

    public void deleteConnection(Connection connection) {
        this.clientManager.remove(connection.getClient());
        this.connectionsManager.remove(connection);
        Logger.log(connection.getClient().getNickname() + " disconnected");
    }

    public void storeMessage(Letter letter) {
        this.messageManager.store(letter);
    }

    public List<Letter> getLastMessages() {
        return this.messageManager.getMessages(Integer.valueOf(this.properties.getProperty("history")));
    }

    public Client addClient(Client client) {
        if (clientManager.containsName(client.getNickname()))
            return null;
        else {
            clientManager.add(client);
            return client;
        }
    }

    public String getVersion() {
        return version;
    }
}