package io.smsgw.gwclient.com;

public class RequestNotValid extends Exception {

    public RequestNotValid(){
        super("Request is not valid. Check request.");
    }

    public RequestNotValid(String message){
        super(message);
    }

}
