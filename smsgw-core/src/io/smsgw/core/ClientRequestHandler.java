package io.smsgw.core;


import io.smsgw.core.request.CommandRequest;
import org.json.JSONObject;

public class ClientRequestHandler {

    private static final String TAG = "ClientRequestHandler";

    private ClientCallbacks clientCallbacks;

    public void setClientCallbacks(ClientCallbacks clientCallbacks){
        this.clientCallbacks = clientCallbacks;
    }

    public void handleMessage(JSONObject message) throws RequestNotValid {
        Request request = new Request(message);
        switch (request.getRequestType()) {
            case COMMAND:
                if (clientCallbacks != null) {
                    clientCallbacks.onCommand(new CommandRequest(message));
                }
                break;
            default:
                throw new RequestNotValid("Unknown request.");
        }
    }

}
