package ru.nsu.g.beryanov.messenger.client;

import ru.nsu.g.beryanov.messenger.util.Letter;
import ru.nsu.g.beryanov.messenger.util.serial.SocketBridgeSerial;
import ru.nsu.g.beryanov.messenger.util.xml.SocketBridgeXML;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private String username;
    private final ChatGraph gui;
    private boolean isLoggedIn = false;
    private Listener listener = null;

    public Client(ChatGraph gui) {
        this.gui = gui;
        username = "";
    }

    public void sendToServer(String event) {
        if (event.isEmpty()) {
            return;
        }
        if (event.equals("/help")) {
            gui.receive("Available commands:\n/login <username> <ip-address>:<port>" +
                    "\n/logout" +
                    "\n/list" +
                    "\n/help");
            return;
        }
        Letter letter;
        if (event.startsWith("/login")) {
            letter = new Letter("login");
            if (event.split("\\s").length >= 2)
                letter.getBody().put("name", event.split("\\s")[1]);
            else
                return;
        } else if (event.startsWith("/logout")) {
            letter = new Letter("logout");
            letter.getBody().put("name", username);
        } else if (event.startsWith("/list"))
            letter = new Letter("listusers");
        else {
            letter = new Letter("message");
            letter.getBody().put("message", event);
            letter.getBody().put("name", username);
        }
        //
        if (letter.getName().equals("login")) {
            if (!isLoggedIn) {
                if (event.split("\\s").length >= 3) {
                    try {
                        if (event.split("\\s")[2].split(":").length >= 2) {
                            String addr = event.split("\\s")[2].split(":")[0];
                            int port = Integer.parseInt(event.split("\\s")[2].split(":")[1]);
                            switch (gui.getVersion()) {
                                case "serial":
                                    listener = new Listener(this, new SocketBridgeSerial(new Socket(addr, port)));
                                    break;
                                case "xml":
                                    listener = new Listener(this, new SocketBridgeXML(new Socket(addr, port)));
                                    break;
                            }
                        } else {
                            gui.receive("Please log in. Type:\n/login <username> <ip-address>:<port>");
                            return;
                        }
                    } catch (IOException e) {
                        gui.receive("Cannot connect to " + event.split("\\s")[2]);
                        return;
                    }
                    username = letter.getBody().get("name");
                    try {
                        listener.send(letter);
                    } catch (IOException ignored) {
                    }
                } else {
                    return;
                }
            } else {
                gui.receive("You're already logged in.");
                return;
            }
        } else if (this.isLoggedIn) try {
            if (letter.getName().equals("logout")) {
                isLoggedIn = false;
                gui.receive("You've logged out.");
            }
            listener.send(letter);
        } catch (IOException ignored) {
        }
        else gui.receive("Please log in. Type:\n/login <username> <ip-address>:<port>");
    }

    public void runCommand(Letter letter) {
        if (letter == null)
            return;
        else if (letter.getName().equals("error")) {
            gui.receive(letter.getBody().get("message"));
            return;
        }
        if (!isLoggedIn) {
            this.isLoggedIn = true;
            gui.setFrameName(this.username);
            gui.receive("You are logged in as " + this.username);
        } else {
            switch (letter.getName()) {
                case "listusers":
                    gui.receive(letter.getBody().get("listusers"));
                    break;
                case "login":
                    gui.receive(letter.getBody().get("name") + " logged in.");
                    break;
                case "logout":
                    gui.receive(letter.getBody().get("name") + " logged out.");
                    break;
                case "message":
                    gui.receive(letter.getBody().get("name") + ":" + letter.getBody().get("message"));
                    break;
            }
        }
    }
}
