package io.smsgw.gwclient.com;

import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Request {

    private static final String KEY_HEADER = "header";
    private static final String KEY_CONTENT = "content";

    private static final String KEY_REQUEST_TYPE = "type";
    private static final String KEY_REQUEST_ID = "request_id";

    private JSONObject header, content;


    public Request(JSONObject src) throws RequestNotValid{
        this.header = src.getJSONObject(KEY_HEADER);
        this.content = src.getJSONObject(KEY_CONTENT);
    }

    public Request(RequestType requestType) {
        this.header = new JSONObject();
        this.content = new JSONObject();
        setRequestId(Request.generateRequestId());
        setRequestType(requestType);
    }

    public JSONObject getContent() {
        return content;
    }


    public void setContent(JSONObject content) {
        this.content = content;
    }

    public JSONObject getHeader() {
        return header;
    }

    public void setHeader(JSONObject header) {
        this.header = header;
    }

    public String getRequestId() {
        return getHeader().getString(KEY_REQUEST_ID);
    }

    public void setRequestId(String requestId) {
        getHeader().put(KEY_REQUEST_ID, requestId);
    }

    public RequestType getRequestType() {
        return RequestType.getRequestType(getHeader().getString(KEY_REQUEST_TYPE));
    }

    public void setRequestType(RequestType requestType) {
        getHeader().put(KEY_REQUEST_TYPE, requestType.getValue());
    }

    public JSONObject buildJsonRequest(){
        JSONObject request = new JSONObject();
        request.put(KEY_HEADER, getHeader());
        request.put(KEY_CONTENT, getContent());
        return request;
    }

    public String buildStringRequest(){
        return buildJsonRequest().toString();
    }

    /* --------------------- Request ID generator -------------------- */
    public static String generateRequestId(){
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(130, secureRandom).toString();
    }
    /* --------------------- Request ID generator -------------------- */

}
