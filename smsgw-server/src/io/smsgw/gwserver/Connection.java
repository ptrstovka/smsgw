package io.smsgw.gwserver;

import org.java_websocket.WebSocket;

import java.util.ArrayList;

public class Connection {

    private WebSocket socket;
    private ArrayList<Gateway> gateways;

    private boolean isApi = false;
    private String apiKey;

    public Connection(WebSocket socket) {
        setSocket(socket);
        this.gateways = new ArrayList<>();
    }

    public Connection(WebSocket socket, boolean isApi, String apiKey) {
        this.gateways = new ArrayList<>();
        this.socket = socket;
        this.isApi = isApi;
        this.apiKey = apiKey;
    }

    public WebSocket getSocket() {
        return socket;
    }

    private void setSocket(WebSocket socket) {
        this.socket = socket;
    }

    public void addGateway(Gateway gateway){
        this.gateways.add(gateway);
    }

    public boolean isApi() {
        return isApi;
    }

    public String getApiKey() {
        return apiKey;
    }

    public boolean hasKey(String key){
        for (Gateway gateway: gateways) {
            if(gateway.getAccessKey().equals(key)){
                return true;
            }
        }
        return false;
    }


    public String hasThisOperator(String operator){
        for (Gateway gateway: gateways) {
            if(gateway.hasOperator(operator)) {
                return gateway.getAccessKey();
            }
        }
        return null;
    }

    public String hasAllOperator() {
        for(Gateway gateway: gateways) {
            if(gateway.hasAllOperator()) {
                return gateway.getAccessKey();
            }
        }
        return null;
    }

}
