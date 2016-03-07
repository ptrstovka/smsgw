package io.smsgw.gwclient;

import io.smsgw.core.*;
import io.smsgw.core.request.CommandRequest;
import io.smsgw.core.request.RegisterRequest;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

public class ServerConnection implements ClientCallbacks {

    private static final String TAG = "Connection";

    private Configuration configuration;
    private WebSocketClient client;
    private ClientRequestHandler clientRequestHandler;
    private GatewayManager gatewayManager;

    public ServerConnection(GatewayManager gatewayManager){
        configuration = Configuration.load();
        this.gatewayManager = gatewayManager;
        clientRequestHandler = new ClientRequestHandler();
        clientRequestHandler.setClientCallbacks(this);
        client = buildClient();
    }


    public void begin(){
        client.connect();
        Log.c(TAG, "Connecting to server...");
    }

    private WebSocketClient buildClient(){
        //noinspection ConstantConditions
        return new WebSocketClient(getServerUriFromConfig(configuration), new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.c(TAG, "Connected! Now registering connected gateways...");
                registerGateways();
            }

            @Override
            public void onMessage(String message) {
                JSONObject data = new JSONObject(message);
                try {
                    clientRequestHandler.handleMessage(data);
                } catch (RequestNotValid requestNotValid) {
                    requestNotValid.printStackTrace();
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.e(TAG, "Server hanged up. Connection closed. Exit.");
                System.exit(1);
            }

            @Override
            public void onError(Exception ex) {
                ex.printStackTrace();
            }
        };
    }

    private URI getServerUriFromConfig(Configuration configuration){
        try {
            return new URI(configuration.getServerProtocol() + "://" + configuration.getServerHost() + ":" + configuration.getServerPort());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    private void registerGateways(){
        gatewayManager.getGateways().forEach(this::registerGateway);
    }

    private void registerGateway(Gateway gateway){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setAccessKey(gateway.getAccessKey());
        sendRequest(registerRequest);
        Log.c(TAG, "Registered! Gateway is ready.");
    }

    private void sendRequest(Request request){
        this.client.send(request.buildStringRequest());
    }

    @Override
    public void onCommand(CommandRequest commandRequest) {
        gatewayManager.executeCommand(commandRequest.getGatewayKey(), commandRequest.getCommand());
    }
}
