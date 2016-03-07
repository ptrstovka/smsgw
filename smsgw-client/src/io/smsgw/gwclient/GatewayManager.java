package io.smsgw.gwclient;

import io.smsgw.core.Command;
import io.smsgw.core.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class GatewayManager {

    private static final String TAG = "Gateway";

    private ArrayList<Gateway> gateways;

    public GatewayManager() {
        setGateways(new ArrayList<>());
    }

    public ArrayList<Gateway> getGateways() {
        return gateways;
    }

    public void setGateways(ArrayList<Gateway> gateways) {
        this.gateways = gateways;
    }

    public void addGateway(Gateway gateway) {
        this.gateways.add(gateway);
        executeCommand(gateway.getAccessKey(), new Command("AT+CMGF=1\n"));
    }

    public Gateway getGateway(String gatewayKey) {
        for (Gateway gateway : gateways) {
            if (gateway.getAccessKey().equals(gatewayKey)) {
                return gateway;
            }
        }
        return null;
    }

    public void executeCommand(String gatewayKey, Command command) {
        Log.c(TAG, "Executing command " + command.getCommandValue());
        Gateway gateway = getGateway(gatewayKey);
        if (gateway != null) {
            try {
                byte[] data = command.getCommandValue().getBytes();
                //noinspection ForLoopReplaceableByForEach
                for (int i = 0; i < data.length; i++) {
                    gateway.getOutputStream().write(data[i]);
                }
                gateway.getOutputStream().flush();
                Log.c(TAG, "Command executed!");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, String.format(Locale.ENGLISH, "Cannot write to output stream on %s.", gateway.getPort()));
            }
        }
    }

}
