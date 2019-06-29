package ru.nsu.g.beryanov.messenger.server.manager;

import ru.nsu.g.beryanov.messenger.server.Connection;
import ru.nsu.g.beryanov.messenger.server.logger.Logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.TimerTask;

public class ConnectionsManager extends TimerTask {

    private final LinkedList<Connection> connections = new LinkedList<>();
    private int usersLimit;

    public LinkedList<Connection> getConnections() {
        return new LinkedList<>(connections);
    }

    @Override
    public void run() {
        for (Connection connection : this.getConnections()) {
            if (!connection.isOn()) {
                this.remove(connection);
            }
        }
    }

    public void add(Connection connection) {
        if (this.connections.size() >= usersLimit) {
            try {
                connection.getSocketBridge().sendError("Server is full.");
                Logger.log("Connection from " + connection.getInetAddress() + " refused: Server if full.");
            } catch (IOException e) {
                // ignore
            }
            return;
        }
        this.connections.add(connection);
    }

    public void remove(Connection connection) {
        this.connections.remove(connection);
    }

    public void setLimit(Integer limit) {
        usersLimit = limit;
    }
}
