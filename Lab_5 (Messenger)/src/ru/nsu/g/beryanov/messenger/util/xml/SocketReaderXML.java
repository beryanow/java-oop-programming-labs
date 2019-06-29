package ru.nsu.g.beryanov.messenger.util.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

public class SocketReaderXML {
    private final Socket socket;
    private final InputStream is;
    private final OutputStream os;

    public SocketReaderXML(Socket socket) throws IOException {
        this.socket = socket;
        this.is = this.socket.getInputStream();
        this.os = this.socket.getOutputStream();
    }

    public void close() throws IOException {
        this.socket.close();
    }

    public void send(String message) throws IOException {
        byte[] mes = message.getBytes();
        ByteBuffer buf = ByteBuffer.allocate(mes.length + 4);
        buf.putInt(mes.length).put(mes);
        os.write(buf.flip().array());
        os.flush();
    }

    public String read() throws IOException {
        try {
            byte[] buf;
            buf = is.readNBytes(4);
            int sizeOfCommand = ByteBuffer.allocate(4).put(buf).flip().getInt();
            buf = is.readNBytes(sizeOfCommand);
            return new String(buf);
        } catch (BufferUnderflowException e) {
            throw new IOException();
        }
    }

    public Socket getSocket() {
        return socket;
    }
}
