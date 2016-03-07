package io.smsgw.gwclient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Configuration {

    private static final String CONFIGURATION_FILE_NAME = "config.json";

    private static final String KEY_GATEWAYS_ARRAY = "gateways";
    private static final String KEY_SERVER_CONFIG = "server";

    private static final String KEY_HOST = "host";
    private static final String KEY_PROTOCOL = "protocol";
    private static final String KEY_PORT = "port";

    private static final String KEY_ACCESS_KEY = "access_key";
    private static final String KEY_SERIAL_PORT = "port";
    private static final String KEY_BAUD_RATE = "baud_rate";
    private static final String KEY_LOG = "log";
    private static final String KEY_LOG_PATH = "log_path";

    private JSONObject serverConfig;
    private JSONArray gateways;
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
            gateways = (JSONArray) jsonConfig.get(KEY_GATEWAYS_ARRAY);
            isValid = true;
        } catch (URISyntaxException | ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(){
        return isValid;
    }

    public int gatewaysCount(){
        return gateways.size();
    }

    public String getServerHost(){
        return getString(serverConfig, KEY_HOST);
    }

    public String getServerPort(){
        return getString(serverConfig, KEY_PORT);
    }

    public String getServerProtocol(){
        return getString(serverConfig, KEY_PROTOCOL);
    }

    public ArrayList<Gateway> getGateways(){
        ArrayList<Gateway> gateways = new ArrayList<>();
        for(int i = 0; i < gatewaysCount(); i++){
            gateways.add(getGatewayAtIndex(i));
        }
        return gateways;
    }

    public Gateway getGatewayAtIndex(int index){
        JSONObject gatewayJson = (JSONObject) gateways.get(index);
        return new Gateway(
                getString(gatewayJson, KEY_ACCESS_KEY),
                getString(gatewayJson, KEY_SERIAL_PORT),
                getBoolean(gatewayJson, KEY_LOG),
                getString(gatewayJson, KEY_LOG_PATH),
                getInteger(gatewayJson, KEY_BAUD_RATE)
        );
    }

    private int getInteger(JSONObject jsonObject, String key){
        return Integer.valueOf((String) jsonObject.get(key));
    }

    private String getString(JSONObject jsonObject, String key){
        return (String) jsonObject.get(key);
    }

    private boolean getBoolean(JSONObject jsonObject, String key){
        return (boolean) jsonObject.get(key);
    }

}
