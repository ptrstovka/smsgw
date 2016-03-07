package io.smsgw.core;

import io.smsgw.core.request.ApiRegisterRequest;
import io.smsgw.core.request.ApiRequest;
import io.smsgw.core.request.RegisterRequest;
import org.java_websocket.WebSocket;

public interface ServerCallbacks {

    void onGatewayRegister(RegisterRequest registerRequest, WebSocket socket);

    void onApiRequest(ApiRequest apiRequest);

    void onApiRegisterRequest(ApiRegisterRequest apiRegisterRequest, WebSocket socket);

    void onApiDisconnected();

}
