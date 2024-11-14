package server;

import client.ClientHandler;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final Set<ClientHandler> clientHandlers = new HashSet<>();
    private static final int PORT = 1337;

    public void broadcast(String message, ClientHandler excludeUser) {
        for (ClientHandler client : clientHandlers) {
            if (client != excludeUser) {
                client.sendMessage(message);
            }
        }
    }
    public void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    public void BootServer(){
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("server running on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getInetAddress()+ " has connected");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

