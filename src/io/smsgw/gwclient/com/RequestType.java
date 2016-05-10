package io.smsgw.gwclient.com;

public enum RequestType {

    HELLO("hello"), UNKNOWN("unknown"), PING("ping"), PONG("pong"),
    NEW_DRIVER_LOCATION("driver_new_location"),
    NEW_LOCATION_OR_STATUS_MESSAGE("driver_new_location_or_status_message"),
    DRIVER_GET_ONLINE("driver_get_online"),
    DRIVER_GET_OFFLINE("driver_get_offline"),
    DRIVER_GET_BUSY("driver_get_busy"),
    DRIVER_CONNECTED("driver_connected"),
    DRIVER_DISCONNECTED("driver_disconnected"),
    ORDER_REQUEST("order_request"),
    ORDER_CONFIRMATION("order_confirmation"),
    ORDER_RESPOND("order_respond"),
    ORDER_ALREADY_DISPATCHED("order_dispatched"),
    CUSTOMER_ORDER_STATUS("customer_order_status");

    private final String value;
    RequestType(String value) {this.value = value;}
    public String getValue(){return this.value;}

    public static RequestType getRequestType(String value){
        switch (value) {
            case "hello": return HELLO;
            case "ping": return PING;
            case "pong": return PONG;
            case "driver_new_location": return NEW_DRIVER_LOCATION;
            case "driver_new_location_or_status_message": return NEW_LOCATION_OR_STATUS_MESSAGE;
            case "driver_get_online": return DRIVER_GET_ONLINE;
            case "driver_get_offline": return DRIVER_GET_OFFLINE;
            case "driver_get_busy": return DRIVER_GET_BUSY;
            case "driver_connected": return DRIVER_CONNECTED;
            case "driver_disconnected": return DRIVER_DISCONNECTED;
            case "order_request": return ORDER_REQUEST;
            case "order_confirmation":return ORDER_CONFIRMATION;
            case "order_respond":return ORDER_RESPOND;
            case "order_dispatched": return ORDER_ALREADY_DISPATCHED;
            case "customer_order_status": return CUSTOMER_ORDER_STATUS;
            default: return UNKNOWN;}
    }

}
