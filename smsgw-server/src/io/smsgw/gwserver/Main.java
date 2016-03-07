package io.smsgw.gwserver;

import io.smsgw.core.Command;
import io.smsgw.core.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

public class Main {

    private static final String TAG = "Server";

    public static void main(String[] args) {

        boolean debugMode = false;

        for (String arg : args) {
            if(arg.equals("debug")) {
                debugMode = true;
            }
        }

        Log.c(TAG, String.format(Locale.ENGLISH, "Server started v%s", Common.APP_VERSION));

        Configuration configuration = Configuration.load();
        if(!configuration.isValid()){
            Log.w(TAG, "Configuration file is not valid. Exit.");
            System.exit(1);
        }

        Log.c(TAG, "Configuration loaded.");
        Log.c(TAG, String.format(Locale.ENGLISH, "Starting server on port %s.", configuration.getServerPort()));

        ServerConnector serverConnector = new ServerConnector();
        serverConnector.startServer();

        if(debugMode) {
            Log.d(TAG, "Debug shell enabled.");
            final String C_TAG = "Console";

            BufferedReader systemInput = new BufferedReader(new InputStreamReader(System.in));
            label:
            while (true) {
                String input = null;
                try {
                    input = systemInput.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(input == null){
                    continue;
                }

                if (!input.equals("")) {
                    if(input.contains(" ")){
                        String arguments[] = input.split(" ");
                        try {
                            switch (arguments[0]){
                                case "send":
                                    String gatewayKey = arguments[1];
                                    String command = arguments[2];
                                    serverConnector.sendCommandToGateway(gatewayKey, new Command(command));
                                    break;
                            }
                        } catch (Exception e){
                            Log.w(C_TAG, "unknown command");
                        }
                    } else {
                        switch (input) {
                            case "exit":
                                serverConnector.stopServer();
                                Log.d(TAG, "Shutting down server...");
                                break label;
                            case "colsh":
                                Log.setColored();
                                break;
                            case "list":
                                Log.d(C_TAG, "List not available.");
                                break;
                            case "help":
                                Log.d(C_TAG, "Command list:" +
                                        "\nhelp     - show this help" +
                                        "\nlist     - show all connected gateways to this server" +
                                        "\ncolsh    - enable or disable colored shell (not working in windows cmd)" +
                                        "\nexit     - shut down this server");
                                break;
                            default:
                                Log.w(C_TAG, "unknown command");
                                break;
                        }
                    }
                }
            }

            Log.d(TAG, "Server down.");
            System.exit(0);
        }
    }

}
