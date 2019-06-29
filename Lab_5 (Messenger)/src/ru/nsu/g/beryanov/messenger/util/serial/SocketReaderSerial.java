package ru.nsu.g.beryanov.messenger.util.serial;

import java.io.*;
import java.net.Socket;
import java.nio.BufferUnderflowException;

public class SocketReaderSerial {
    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;

    public SocketReaderSerial(Socket socket) throws IOException {
        this.socket = socket;
        this.is = this.socket.getInputStream();
        this.os = this.socket.getOutputStream();
    }

    public void close() throws IOException {
        this.socket.close();
    }

    public void send(Object object) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(object);
        oos.flush();
    }

    public Object read() throws IOException {
        Object obj = null;
        ObjectInputStream ois = new ObjectInputStream(is);
        try {
            obj = ois.readObject();
        } catch (BufferUnderflowException e) {
            throw new IOException();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Socket getSocket() {
        return socket;
    }
}
