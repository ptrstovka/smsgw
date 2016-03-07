package io.smsgw.gwclient;


import io.smsgw.core.Log;

import java.util.Locale;

public class Main {

    private static final String TAG = "Client";

    public static void main(String[] args) {

        Log.c(TAG, String.format(Locale.ENGLISH, "Starting SMSgw.io client v%s", Common.APP_VERSION));
        Configuration configuration = Configuration.load();

        if(!configuration.isValid()){
            Log.w(TAG, "Configuration file is invalid. Exit.");
            System.exit(1);
        }

        if(configuration.gatewaysCount() == 0){
            Log.w(TAG, "No gateways configured. Exit.");
            System.exit(1);
        }

        Log.c(TAG, "Configuration loaded!");
        Log.c(TAG, String.format(Locale.ENGLISH, "Loaded %d gateways.", configuration.gatewaysCount()));

        Client client = new Client();
        client.start();

    }

}
