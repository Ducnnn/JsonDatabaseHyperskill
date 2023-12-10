package server;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class Database {
    private JsonObject base;

    public Database() {
        base = new JsonObject();
    }

    protected void delete(JsonArray key) {
        if(key.size() > 1) {
            JsonObject jo = base.getAsJsonObject(key.get(0).getAsString());
            for (int i = 1; i < key.size() - 1; i++) {
                jo = jo.getAsJsonObject(key.get(i).getAsString());
            }
            jo.remove(key.get(key.size() - 1).getAsString());
        } else base.remove(key.get(0).getAsString());
    }

    protected void set(JsonArray key, JsonElement value) {
        if (key.size() > 1) {
            JsonObject jo = base.getAsJsonObject(key.get(0).getAsString());
            for (int i = 1; i < key.size() - 1; i++) {
                jo = jo.getAsJsonObject(key.get(i).getAsString());
            }
            jo.addProperty(key.get(key.size() - 1).getAsString(), value.getAsString());
        } else {
            base.add(key.get(0).getAsString(), value);
        }

    }

    protected JsonElement get(JsonArray key) {
        if (key.size() > 1) {
            JsonObject jo = base.getAsJsonObject(key.get(0).getAsString());
            for (int i = 1; i < key.size() - 1; i++) {
                jo = jo.getAsJsonObject(key.get(i).getAsString());
            }

            return jo.get(key.get(key.size() - 1).getAsString());
        } else  return base.get(key.get(0).getAsString());
    }

    protected boolean contains(JsonArray key) {
        if(key.size() > 1) {
            JsonObject jo = base.getAsJsonObject(key.get(0).getAsString());
            for (int i = 1; i < key.size() - 1; i++) {

                jo = jo.getAsJsonObject(key.get(i).getAsString());
            }
            return jo.has(key.get(key.size() - 1).getAsString());
        } else return base.has(key.get(0).getAsString());
    }
}
