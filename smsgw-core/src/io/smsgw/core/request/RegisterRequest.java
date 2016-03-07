package io.smsgw.core.request;

import io.smsgw.core.Request;
import io.smsgw.core.RequestNotValid;
import io.smsgw.core.RequestType;
import org.json.JSONObject;

public class RegisterRequest extends Request {

    private static final String KEY_ACCESS_KEY = "access_key";

    public RegisterRequest(JSONObject src) throws RequestNotValid {
        super(src);
    }

    public RegisterRequest(RequestType requestType) {
        super(requestType);
    }

    public RegisterRequest() {
        super(RequestType.REGISTER_GATEWAY);
    }

    public String getAccessKey(){
        return getContent().getString(KEY_ACCESS_KEY);
    }

    public void setAccessKey(String accessKey){
        getContent().put(KEY_ACCESS_KEY, accessKey);
    }

}
