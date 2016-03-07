package io.smsgw.gwserver;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class Configuration {

    private static final String CONFIGURATION_FILE_NAME = "server.json";

    private static final String KEY_SERVER_CONFIG = "server";

    private static final String KEY_API_URL = "api_host";
    private static final String KEY_API_PROTOCOL = "api_protocol";
    private static final String KEY_PORT = "port";
    private static final String KEY_LOG = "log";
    private static final String KEY_LOG_PATH = "log_path";
    private static final String KEY_API_KEY = "api_key";

    private JSONObject serverConfig;
    private boolean isValid;

    public static Configuration load(){
        return new Configuration();
    }

    private Configuration(){
        try {
            File base = new File(Configuration.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
            File configFile = new File(base, CONFIGURATION_FILE_NAME);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonConfig = (JSONObject) jsonParser.parse(new FileReader(configFile));
            serverConfig = (JSONObject) jsonConfig.get(KEY_SERVER_CONFIG);
            isValid = true;
        } catch (URISyntaxException | ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(){
        return isValid;
    }

    public String getServerPort(){
        return getString(serverConfig, KEY_PORT);
    }

    private String getString(JSONObject jsonObject, String key){
        return (String) jsonObject.get(key);
    }

    private boolean getBoolean(JSONObject jsonObject, String key){
        return (boolean) jsonObject.get(key);
    }

    private int getInt(JSONObject jsonObject, String key){
        return Integer.valueOf((String) jsonObject.get(key));
    }

    public int getServerPortInt(){
        return getInt(serverConfig, KEY_PORT);
    }

    public String getApiKey(){
        return getString(serverConfig, KEY_API_KEY);
    }

    public String getApiUrl(){
        return getString(serverConfig, KEY_API_PROTOCOL) + "://" + getString(serverConfig, KEY_API_URL);
    }

}
