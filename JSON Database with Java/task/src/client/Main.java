package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import server.Request;

import java.io.*;
import java.net.Socket;


public class Main {

    public static void main(String[] args) {

        System.out.println("Client started!");

        try (
                Socket socket = new Socket("127.0.0.1", 14055);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            Args jArgs = new Args();
            JCommander.newBuilder().addObject(jArgs).build().parse(args);
            Gson gson = new Gson();

            if ("exit".equals(jArgs.getType())) {
                String msg = "{\"type\":\"exit\"}";
                output.writeUTF(msg);
                System.out.println("Sent: " + msg);
                String receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
            } else if (jArgs.getFilename() != null) {
                FileReader fileReader = new FileReader("src/client/data/" + jArgs.getFilename());
                JsonReader jsonReader = new JsonReader(fileReader);
                Request request = new Request(gson.fromJson(fileReader, JsonObject.class));
                jsonReader.close();
                fileReader.close();
                String msg = request.getObject().toString();
                output.writeUTF(msg);
                System.out.println("Sent: " + msg);
                String receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
            } else {
                Request request = new Request(jArgs.getType(), jArgs.getKey(), jArgs.getValue());
                String msg = request.getObject().toString();
                output.writeUTF(msg);
                System.out.println("Sent: " + msg);
                String receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
