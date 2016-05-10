package io.smsgw.gwserver;

import io.smsgw.core.Log;
import org.java_websocket.WebSocket;

import java.util.ArrayList;

public class ConnectionManager {

    private ArrayList<Connection> connections;
    private Connection apiConnection;

    public ConnectionManager() {
        connections = new ArrayList<>();
    }

    public Connection getConnection(String accessKey) {
        for (Connection connection : connections) {
            if (connection.hasKey(accessKey)) {
                return connection;
            }
        }
        return null;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void removeConnection(WebSocket socket) {
        Connection connection = getConnection(socket);
        if(connection != null){
            connections.remove(connection);
        }
    }

    public Connection getConnection(WebSocket socket) {
        for (Connection connection : connections) {
            if (connection.getSocket().equals(socket)) {
                return connection;
            }
        }
        return null;
    }

    public Connection getApiConnection() {
        return apiConnection;
    }

    public void setApiConnection(Connection apiConnection) {
        this.apiConnection = apiConnection;
    }

    /**
     * Find the best gateway for given phone number
     * based on gateway sending rules.
     *
     * @param number String phone number from request
     * @return String access key of calculated gateway
     *         null when no gateway is found
     */
    public String getBestGateway(String number){

        String prefix = number;

        // if the number is in format 09xx xxx xxx
        if(number.length() == 10) {
            prefix = number.substring(1, 4);
        }

        // if the number is in format +4xx xxx xxx xxx
        if(number.length() == 12 || number.length() == 13) {
            if(number.contains("+")) {
                prefix = number.substring(4, 7);
            } else {
                prefix = number.substring(3, 6);
            }

        }

        // if the number is in format 004xx xxx xxx xxx
        if(number.length() == 14) {
            prefix = number.substring(5, 8);
        }

        for (Connection connection : connections) {
            String conn = connection.hasThisOperator(prefix);
            if(conn != null) {
               return conn;
            }
        }

        for (Connection connection: connections) {
            String conn = connection.hasAllOperator();
            if(conn != null) {
                return conn;
            }
        }

        return null;

    }

}
