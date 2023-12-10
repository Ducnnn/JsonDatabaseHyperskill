package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class RequestProcessor {
    private final static String ERROR = "ERROR";
    private final static String OK = "OK";
    private final static String RESPONSE = "response";
    private final static String REASON = "reason";
    private final static String NO_SUCH_KEY = "no such key";
    private final static String VALUE = "value";

    protected static String process(Request request, Database DATABASE) {
        Gson gson = new Gson();
        String command = request.getType();

        String keyString = request.getKey();
        JsonArray key;
        if(isJsonArray(keyString)){
            key = gson.fromJson(keyString, JsonArray.class);
        } else {
            key = new JsonArray(1);
            key.add(keyString);
        }
        switch (command.toLowerCase()) {
            case "get" -> {
                JsonObject response = new JsonObject();
                if (DATABASE.contains(key)) {
                    response.addProperty(RESPONSE, OK);
                    response.add(VALUE, DATABASE.get(key));
                } else {
                    response.addProperty(RESPONSE, ERROR);
                    response.addProperty(REASON, NO_SUCH_KEY);
                }
                return response.toString();
            }
            case "set" -> {
                DATABASE.set(key, request.getValue());
                JsonObject response = new JsonObject();
                response.addProperty(RESPONSE, OK);
                return response.toString();
            }
            case "delete" -> {
                JsonObject response = new JsonObject();
                if (DATABASE.contains(key)) {
                    DATABASE.delete(key);
                    response.addProperty(RESPONSE, OK);
                } else {
                    response.addProperty(RESPONSE, ERROR);
                    response.addProperty(REASON, NO_SUCH_KEY);
                }
                return response.toString();
            }
            default -> {
                return ERROR;
            }
        }
    }

    private static boolean isJsonArray(String key) {
        return key.matches("\\[(\\\"\\w+\\\")(\\,\\s?\\\"\\w+\\\")*\\]");
    }

}
