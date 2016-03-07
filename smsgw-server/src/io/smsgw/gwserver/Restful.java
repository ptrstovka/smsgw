package io.smsgw.gwserver;

import io.smsgw.core.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Restful {

    private static final String TAG = "Restful";

    private static Configuration configuration = Configuration.load();

    public static GatewayData getGatewayData(String accessKey) {
        String serverResponse = RestClient.httpGet(configuration.getApiUrl() + "/api/auth/gateway?key=" + accessKey);
        Log.c(TAG, serverResponse);;
        try {

            JSONObject data = new JSONObject(serverResponse);
            if(data.has("error")) {
                return null;
            }

            JSONObject srvData = data.getJSONObject("data");

            GatewayData gwData = new GatewayData();

            if(srvData.isNull("operators")) {
                return gwData;
            }

            JSONArray operators = srvData.getJSONArray("operators");

            for(int i = 0; i < operators.length(); i++){
                gwData.addOperator(operators.getString(i));
            }

            return gwData;
        } catch (JSONException e) {
            Log.d(TAG, "Got exception.");
            return null;
        }
    }

}
