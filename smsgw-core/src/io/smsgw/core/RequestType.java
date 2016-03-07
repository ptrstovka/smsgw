package io.smsgw.core;

public enum RequestType {

    REGISTER_GATEWAY("register"), UNKNOWN("unknown"), COMMAND("command"),
    API_REQUEST("api_request"), API_REGISTER("api_register");

    private final String value;
    RequestType(String value) {this.value = value;}
    public String getValue(){return this.value;}

    public static RequestType getRequestType(String value){
        switch (value) {
            case "register": return REGISTER_GATEWAY;
            case "command": return COMMAND;
            case "api_request": return API_REQUEST;
            case "api_register": return API_REGISTER;
            default: return UNKNOWN;}
    }

}
