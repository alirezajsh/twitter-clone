package com.example.demo5.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientNetworkConnection implements NetworkConnection {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private volatile boolean connected = false;

    @Override
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean send(NetworkPacket packet) {
        try {
            out.writeObject(packet);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    @Override
    public NetworkPacket listen() throws Exception {
        return (NetworkPacket) in.readObject();
    }

    @Override
    public void disconnect() {
        connected = false;
        try { if (socket != null) socket.close(); } catch (IOException ignored) {}
        try { if (out != null) out.close(); } catch (IOException ignored) {}
        try { if (in != null) in.close(); } catch (IOException ignored) {}
    }

    @Override
    public List<NetworkPacket> syncHistory(int userId) {

        send(new NetworkPacket(NetworkPacket.RequestType.SYNC_HISTORY, userId));
        List<NetworkPacket> history = new ArrayList<>();
        try {
            NetworkPacket pkt;
            while ((pkt = listen()) != null && pkt.getType() != NetworkPacket.RequestType.DISCONNECT) {
                history.add(pkt);
            }
        } catch (Exception ignored) {}
        return history;
    }

    @Override
    public boolean isConnected() {
        return connected && socket != null && !socket.isClosed();
    }
}