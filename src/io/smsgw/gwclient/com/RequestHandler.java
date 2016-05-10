package io.smsgw.gwclient.com;


import org.json.JSONObject;
import sk.handytaxi.applib.callbacks.*;
import sk.handytaxi.applib.communication.messages.*;
import sk.handytaxi.applib.communication.request.Ping;

public class RequestHandler {

    private static final String TAG = "RequestHandler";

    private ClientCallbacks clientCallbacks;
    private BasicCallbacks basicCallbacks;
    private ConnectionCallbacks connectionCallbacks;
    private DriverOrderCallbacks driverOrderCallbacks;
    private StatusCallbacks statusCallbacks;

    public void setClientCallbacks(ClientCallbacks clientCallbacks) {
        this.clientCallbacks = clientCallbacks;
    }

    public void setBasicCallbacks(BasicCallbacks basicCallbacks) {
        this.basicCallbacks = basicCallbacks;
    }

    public void setConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {
        this.connectionCallbacks = connectionCallbacks;
    }

    public void setDriverOrderCallbacks(DriverOrderCallbacks driverOrderCallbacks) {
        this.driverOrderCallbacks = driverOrderCallbacks;
    }

    public void setStatusCallbacks(StatusCallbacks statusCallbacks) {
        this.statusCallbacks = statusCallbacks;
    }

    public void handleMessage(JSONObject message) throws RequestNotValid {
        Message request = new Message(message);
        switch (request.getRequestType()) {
            case HELLO:
                if (basicCallbacks != null) {
                    basicCallbacks.onHelloResponse(new HelloResponse(message));
                }
                break;
            case PING:
                if (basicCallbacks != null) {
                    basicCallbacks.onPing(new Ping(message));
                }
                break;
            case NEW_DRIVER_LOCATION:
                if (clientCallbacks != null) {
                    clientCallbacks.onLocationStatus(new LocationStatus(message));
                }
                break;
            case DRIVER_GET_BUSY:
                if (statusCallbacks != null) {
                    statusCallbacks.onGetBusy(new DriverGetBusy(message));
                }
                break;
            case DRIVER_GET_ONLINE:
                if (statusCallbacks != null) {
                    statusCallbacks.onGetOnline(new DriverGetOnline(message));
                }
                break;
            case DRIVER_GET_OFFLINE:
                if (statusCallbacks != null) {
                    statusCallbacks.onGetOffline(new DriverGetOffline(message));
                }
                break;
            case ORDER_ALREADY_DISPATCHED:
                if (driverOrderCallbacks != null) {
                    driverOrderCallbacks.onOrderDispatched(new OrderDispatched(message));
                }
                break;
            case ORDER_CONFIRMATION:
                if (driverOrderCallbacks != null) {
                    driverOrderCallbacks.onOrderConfirmation(new OrderConfirmation(message));
                }
                break;
            case ORDER_REQUEST:
                if (driverOrderCallbacks != null) {
                    driverOrderCallbacks.onNewOrder(new NewOrder(message));
                }
                break;
            case DRIVER_DISCONNECTED:
                if (connectionCallbacks != null) {
                    connectionCallbacks.onDriverDisconnected(new DriverDisconnected(message));
                }
                break;
            case DRIVER_CONNECTED:
                if (connectionCallbacks != null) {
                    connectionCallbacks.onDriverConnected(new DriverConnected(message));
                }
                break;
            case CUSTOMER_ORDER_STATUS:
                if (clientCallbacks != null) {
                    clientCallbacks.onOrderStatusUpdate(new OrderStatus(message));
                }
                break;
            default: // FUCK do not forget to add break statement FUCK
                throw new RequestNotValid("Unknown response type.");
        }
    }

}
