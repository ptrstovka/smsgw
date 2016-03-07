package io.smsgw.gwserver;

import io.smsgw.core.Log;
import io.smsgw.core.RequestNotValid;
import io.smsgw.core.ServerRequestHandler;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public class SocketServer extends WebSocketServer {

    private static final String TAG = "Server Core";

    private ServerRequestHandler requestHandler;
    private ConnectionManager connectionManager;

    public SocketServer(InetSocketAddress address, ServerRequestHandler requestHandler, ConnectionManager connectionManager) {
        super(address);
        this.requestHandler = requestHandler;
        this.connectionManager = connectionManager;
    }

    @Override
    public void start() {
        super.start();
        Log.c(TAG, "Server running.");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Log.c(TAG, "Connection closed.");
        if(conn.equals(connectionManager.getApiConnection())) {
            requestHandler.apiDisconnected();
        } else {
            connectionManager.removeConnection(conn);
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        JSONObject data = new JSONObject(message);
        try {
            requestHandler.handleMessage(data, conn);
        } catch (RequestNotValid requestNotValid) {
            requestNotValid.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }
}
