package com.example.demo5.network;

import com.example.demo5.repository.MySQLConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private ServerSocket serverSocket;
    private final ExecutorService pool = Executors.newCachedThreadPool();
    private volatile boolean running = true;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        while (running) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress());
            pool.execute(new ClientHandler(clientSocket));
        }
    }

    public void shutdown() {
        running = false;
        try { if (serverSocket != null) serverSocket.close(); } catch (IOException ignored) {}
        pool.shutdownNow();
        System.out.println("Server shutdown.");
    }

    public static void main(String[] args) {
        try {

            new ChatServer().start(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}