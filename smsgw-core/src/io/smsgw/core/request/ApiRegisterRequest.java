package io.smsgw.core.request;

import io.smsgw.core.RequestNotValid;
import io.smsgw.core.RequestType;
import org.json.JSONObject;

public class ApiRegisterRequest extends RegisterRequest{

    public ApiRegisterRequest(JSONObject src) throws RequestNotValid {
        super(src);
    }

    public ApiRegisterRequest() {
        super(RequestType.API_REGISTER);
    }

}
