package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket socket;
    private final ServerSocket server;

    public ClientThread(Socket socket, ServerSocket server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run(){
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            Gson gson = new Gson();
            Database database = DatabaseSerializer.deserialize("src/server/data/db.json");

            ThreadLocal<String> inpMsg = new ThreadLocal<>();
            ThreadLocal<Request> localRequest = new ThreadLocal<>();
            ThreadLocal<String> outMsg = new ThreadLocal<>();
            inpMsg.set(input.readUTF());
            localRequest.set( new Request( gson.fromJson(inpMsg.get(), JsonObject.class)));
            System.out.println("Received: " + inpMsg.get());

            if ("exit".equals(localRequest.get().getType())) {
                output.writeUTF("OK");
                socket.close();
                server.close();
                return;
            }
            outMsg.set(RequestProcessor.process(localRequest.get(), database));
            output.writeUTF(outMsg.get());

            DatabaseSerializer.serialize(database, "src/server/data/db.json");

            System.out.println("Sent: " + outMsg.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
