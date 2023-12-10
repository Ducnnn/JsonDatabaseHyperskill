package server;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DatabaseSerializer  {

    public static void serialize(Database database, String Path) throws IOException {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock writeLock = lock.writeLock();
        Gson gson = new Gson();
        writeLock.lock();
        FileWriter fileWriter = new FileWriter(Path);
        fileWriter.write(gson.toJson(database));
        fileWriter.close();
        writeLock.unlock();
    }

    public static Database deserialize(String Path) throws IOException {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        readLock.lock();
        FileReader fileReader = new FileReader(Path);
        JsonReader jsonReader = new JsonReader(fileReader);
        Gson gson = new Gson();
        Database database = gson.fromJson(jsonReader, Database.class);
        fileReader.close();
        jsonReader.close();
        readLock.unlock();
        return database;
    }
}
