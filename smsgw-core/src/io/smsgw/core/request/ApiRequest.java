package io.smsgw.core.request;

import io.smsgw.core.Request;
import io.smsgw.core.RequestNotValid;
import io.smsgw.core.RequestType;
import org.json.JSONObject;

public class ApiRequest extends Request {

    private static final String KEY_NUMBER = "number";
    private static final String KEY_CONTENT = "content";

    public ApiRequest(JSONObject src) throws RequestNotValid {
        super(src);
    }
    public ApiRequest() {
        super(RequestType.API_REQUEST);
    }

    public String getNumber(){
        return getContent().getString(KEY_NUMBER);
    }

    public String getSmsContent(){
        return getContent().getString(KEY_CONTENT);
    }

    public void setNumber(String number){
        getContent().put(KEY_NUMBER, number);
    }

    public void setSmsContent(String content){
        getContent().put(KEY_CONTENT, content);
    }

}
