package io.smsgw.gwclient;

import gnu.io.*;
import io.smsgw.core.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Locale;

public class Client {

    private static final String TAG = "Client";

    private GatewayManager gatewayManager;
    private ServerConnection serverConnection;
    private Configuration configuration;

    public Client() {
        configuration = Configuration.load();
        this.gatewayManager = new GatewayManager();
        this.serverConnection = new ServerConnection(gatewayManager);
    }

    private void preparePorts() {
        Log.c(TAG, "Searching for available ports...");
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        if (!ports.hasMoreElements()) {
            Log.e(TAG, "No ports found. Exit.");
            System.exit(1);
        }

        while (ports.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                Log.d(TAG, String.format(Locale.ENGLISH, "Found %s.", curPort.getName()));
            }
        }
    }

    private void loadGateways() {
        configuration.getGateways().forEach(gateway -> {
            CommPortIdentifier portIdentifier = null;
            try {
                portIdentifier = CommPortIdentifier
                        .getPortIdentifier(gateway.getPort());
                if (portIdentifier.isCurrentlyOwned()) {
                    Log.e(TAG, String.format(Locale.ENGLISH, "Port %s is already used. Exit.", portIdentifier.getName()));
                    System.exit(1);
                } else {
                    int timeout = 2000;
                    CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

                    if (commPort instanceof SerialPort) {
                        SerialPort serialPort = (SerialPort) commPort;
                        serialPort.setSerialPortParams(gateway.getBaudRate(),
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);

                        gateway.setInputStream(serialPort.getInputStream());
                        gateway.setOutputStream(serialPort.getOutputStream());
                        gatewayManager.addGateway(gateway);
                        Log.c(TAG, String.format(Locale.ENGLISH,
                                "Gateway on port %s configured and added to registration queue.",
                                commPort.getName()));
                    } else {
                        Log.e(TAG, "Only serial ports are currently supported. Exit.");
                        System.exit(1);
                    }
                }
            } catch (NoSuchPortException e) {
                e.printStackTrace();
                Log.e(TAG, "Port not found.");
                System.exit(1);
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
                Log.e(TAG, "Unsupported operation. Exit.");
                System.exit(1);
            } catch (PortInUseException e) {
                e.printStackTrace();
                Log.e(TAG, "Port is already in use. Exit.");
                System.exit(1);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "IOException handled. Exit.");
                System.exit(1);
            }
        });

    }


    public void start() {
        preparePorts();
        Log.c(TAG, "Ports prepared.");
        loadGateways();
        Log.c(TAG, "Gateways loaded. Beginning connection...");
        serverConnection.begin();
    }

}
