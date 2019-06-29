package ru.nsu.g.beryanov.messenger.server.manager;

import ru.nsu.g.beryanov.messenger.server.client.Client;

import java.util.LinkedList;

public class ClientManager {
    private final LinkedList<Client> clients = new LinkedList<>();

    public LinkedList<Client> getAllClients(){
        return new LinkedList<>(clients);
    }

    public void add(Client client){
        clients.add(client);
    }

    public void remove(Client client){
        clients.remove(client);
    }

    public boolean containsName(String name){
        for (Client client : clients){
            if (client.getNickname().equals(name))
                return true;
        }
        return false;
    }
}
