package io.smsgw.finder;

import gnu.io.CommPortIdentifier;
import io.smsgw.core.Log;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Locale;

public class Main {

    private static final String TAG = "Port finder";

    public static void main(String[] args) {

//        int a = 0;
//        String s = "A\n";
//        System.out.println(Arrays.toString(s.getBytes()));
//
//        try
//        {
//            int c = 0;
//            while ( ( c = System.in.read()) > -1 )
//            {
//                System.out.println(c);
//            }
//        }
//        catch ( IOException e )
//        {
//            e.printStackTrace();
//        }

        Log.c(TAG, "Searching for available ports...");
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        while (ports.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                Log.d(TAG, String.format(Locale.ENGLISH, "Found %s.", curPort.getName()));
            }
        }

        Log.c(TAG, "Done.");
    }

}
