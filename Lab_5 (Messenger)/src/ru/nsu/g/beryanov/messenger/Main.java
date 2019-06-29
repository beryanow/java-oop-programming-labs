package ru.nsu.g.beryanov.messenger;

import ru.nsu.g.beryanov.messenger.client.ChatGraph;
import ru.nsu.g.beryanov.messenger.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length >= 2) {
            if (args[1].equals("serial") || args[1].equals("xml")) {
                if (args[0].equals("client")) {
                    ChatGraph gui = new ChatGraph(args[1]);
                    gui.setVisible(true);
                }
                if (args[0].equals("server"))
                    new Server(args[1]).run();
            }
        }
    }
}
