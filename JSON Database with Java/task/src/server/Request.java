package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Request {
    private final JsonObject request;
    public Request(String type, String key, String value) {
        request = new JsonObject();
        request.addProperty("type", type);
        request.addProperty("key", key);
        request.addProperty("value", value);
    }
    public Request(String type) {
        request = new JsonObject();
        request.addProperty("type", type);
    }
    public Request(JsonObject request){
        this.request = request;
    }
    public Request(String type, String key, JsonObject value) {
        request = new JsonObject();
        request.addProperty("type", type);
        request.addProperty("key", key);
        request.add("value", value);
    }



    public String getType() {
        return request.get("type").getAsString();
    }

    public String getKey() {
        return request.get("key").toString();
    }

    public JsonElement getValue() {
        return request.get("value");
    }
    public JsonObject getObject(){
        return request;
    }
}
