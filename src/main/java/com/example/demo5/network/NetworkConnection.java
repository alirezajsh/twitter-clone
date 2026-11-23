package com.example.demo5.network;

import java.util.List;

public interface NetworkConnection {
    boolean connect(String host, int port);
    boolean send(NetworkPacket packet);
    NetworkPacket listen() throws Exception;
    void disconnect();
    List<NetworkPacket> syncHistory(int userId);
    boolean isConnected();
}