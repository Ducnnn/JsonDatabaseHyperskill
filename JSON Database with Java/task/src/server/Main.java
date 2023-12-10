package server;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(14055)) {
            System.out.println("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(6);
            while (!server.isClosed()) {
                Socket socket = server.accept();
                ClientThread clientThread = new ClientThread(socket, server);
                executor.submit(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
