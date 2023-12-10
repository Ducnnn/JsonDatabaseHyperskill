package client;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonArray;

public class Args {
    @Parameter(names = {"-type", "-t"})
    private String type = null;
    @Parameter(names = {"-k", "-key"})
    private String key = null;
    @Parameter(names = {"-v", "-value"})
    private String value = null;
    @Parameter(names = {"-in", "-input"})
    private String filename = null;
    public String getFilename(){
        return filename;
    }
    public String getType() {
        return type;
    }
    public String getKey(){
        return key;
    }
    public String getValue(){
        return value;
    }
}
