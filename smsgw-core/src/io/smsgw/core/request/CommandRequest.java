package io.smsgw.core.request;

import io.smsgw.core.Command;
import io.smsgw.core.Request;
import io.smsgw.core.RequestNotValid;
import io.smsgw.core.RequestType;
import org.json.JSONObject;

public class CommandRequest extends Request {

    private static final String KEY_COMMAND = "command";
    private static final String KEY_GW_ACCESS_KEY = "gw_access_key";

    public CommandRequest(JSONObject src) throws RequestNotValid {
        super(src);
    }

    public CommandRequest(RequestType requestType) {
        super(requestType);
    }

    public CommandRequest() {
        super(RequestType.COMMAND);
    }

    public Command getCommand(){
        return new Command(getContent().getString(KEY_COMMAND));
    }

    public void setCommand(Command command){
        getContent().put(KEY_COMMAND, command.getCommandValue());
    }

    public String getGatewayKey(){
        return getContent().getString(KEY_GW_ACCESS_KEY);
    }

    public void setGatewayKey(String key){
        getContent().put(KEY_GW_ACCESS_KEY, key);
    }

}
