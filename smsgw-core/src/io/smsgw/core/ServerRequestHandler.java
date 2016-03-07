package io.smsgw.core;

import io.smsgw.core.request.ApiRegisterRequest;
import io.smsgw.core.request.ApiRequest;
import io.smsgw.core.request.RegisterRequest;
import org.java_websocket.WebSocket;
import org.json.JSONObject;

public class ServerRequestHandler {

    private static final String TAG = "ServerRequestHandler";

    private ServerCallbacks serverCallbacks;

    public void setServerCallbacks(ServerCallbacks serverCallbacks){
        this.serverCallbacks = serverCallbacks;
    }

    public void handleMessage(JSONObject message, WebSocket socket) throws RequestNotValid {
        Request request = new Request(message);
        switch (request.getRequestType()) {
            case REGISTER_GATEWAY:
                if (serverCallbacks != null) {
                    serverCallbacks.onGatewayRegister(new RegisterRequest(message), socket);
                }
                break;
            case API_REQUEST:
                if (serverCallbacks != null) {
                    serverCallbacks.onApiRequest(new ApiRequest(message));
                }
                break;
            case API_REGISTER:
                if (serverCallbacks != null) {
                    serverCallbacks.onApiRegisterRequest(new ApiRegisterRequest(message), socket);
                }
                break;
            default:
                throw new RequestNotValid("Unknown request.");
        }
    }

    public void apiDisconnected(){
        serverCallbacks.onApiDisconnected();
    }

}
