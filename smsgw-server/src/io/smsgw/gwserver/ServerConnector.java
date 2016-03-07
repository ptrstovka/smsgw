package io.smsgw.gwserver;

import io.smsgw.core.*;
import io.smsgw.core.request.ApiRegisterRequest;
import io.smsgw.core.request.ApiRequest;
import io.smsgw.core.request.CommandRequest;
import io.smsgw.core.request.RegisterRequest;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Locale;

public class ServerConnector implements ServerCallbacks {

    private static final String TAG = "Server";

    private ConnectionManager connectionManager;
    private ServerRequestHandler requestHandler;
    private SocketServer socketServer;
    private Configuration configuration;

    public ServerConnector() {
        configuration = Configuration.load();
        connectionManager = new ConnectionManager();
        requestHandler = new ServerRequestHandler();
        requestHandler.setServerCallbacks(this);
        socketServer = new SocketServer(new InetSocketAddress(configuration.getServerPortInt()),
                requestHandler, connectionManager);
    }

    public void startServer(){
        socketServer.start();
    }

    @Override
    public void onGatewayRegister(RegisterRequest registerRequest, WebSocket socket) {
        Log.c(TAG, String.format(Locale.ENGLISH, "Gateway %s is trying to register.", registerRequest.getAccessKey()));

        GatewayData gatewayData = Restful.getGatewayData(registerRequest.getAccessKey());

        if(gatewayData == null) {
            Log.w(TAG, "Unknown gateway connected.");
            socket.close();
            return;
        }

        Connection connection = connectionManager.getConnection(socket);

        if(connection == null){
            connection = new Connection(socket);
            connection.addGateway(new Gateway(registerRequest.getAccessKey(), gatewayData));
            connectionManager.addConnection(connection);
            Log.c(TAG, String.format(Locale.ENGLISH, "Gateway %s successfully registered!", registerRequest.getAccessKey()));
            return;
        }

        connectionManager.getConnection(socket).addGateway(new Gateway(registerRequest.getAccessKey(), gatewayData));
        Log.c(TAG, String.format(Locale.ENGLISH, "Gateway %s successfully registered!", registerRequest.getAccessKey()));
    }

    @Override
    public void onApiRequest(ApiRequest apiRequest) {
        Log.c(TAG, "Got API request.");
        String gatewayKey = connectionManager.getBestGateway(apiRequest.getNumber());

        if(gatewayKey == null) {
            Log.w(TAG, "Cannot find the best gateway for SMS. Please reconfigure.");
            return;
        }

        Connection connection = connectionManager.getConnection(gatewayKey);
        if(connection == null){
            Log.w(TAG, "API request to unknown connection!");
            return;
        }

        Log.c(TAG, "Found the best gateway.");

        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setCommand(CommandBuilder.getInstance().buildCommand(apiRequest.getNumber(), apiRequest.getSmsContent()));
        commandRequest.setGatewayKey(gatewayKey);
        connection.getSocket().send(commandRequest.buildStringRequest());
    }

    @Override
    public void onApiRegisterRequest(ApiRegisterRequest apiRegisterRequest, WebSocket socket) {
        Log.c(TAG, String.format(Locale.ENGLISH, "API %s is trying to register.", apiRegisterRequest.getAccessKey()));

        Configuration configuration = Configuration.load();
        if(!apiRegisterRequest.getAccessKey().equals(configuration.getApiKey())){
            Log.w(TAG, "API is attempting to connect with wrong API key.");
            socket.close();
            return;
        }

        Connection apiConnection = new Connection(socket, true, apiRegisterRequest.getAccessKey());
        connectionManager.setApiConnection(apiConnection);
        Log.c(TAG, String.format(Locale.ENGLISH, "API %s successfully registered!", apiRegisterRequest.getAccessKey()));
    }

    @Override
    public void onApiDisconnected() {
        connectionManager.setApiConnection(null);
    }

    public void sendCommandToGateway(String accessKey, Command command){
        Connection connection = connectionManager.getConnection(accessKey);

        if(connection == null){
            Log.e(TAG, "No such gateway.");
            return;
        }

        CommandRequest commandRequest = new CommandRequest();
        commandRequest.setGatewayKey(accessKey);
        commandRequest.setCommand(command);
        connection.getSocket().send(commandRequest.buildStringRequest());
    }

    public void stopServer(){
        try {
            socketServer.stop();
        } catch (IOException | InterruptedException e) {
            Log.w(TAG, "Cannot stop server.");
        }
    }



}
