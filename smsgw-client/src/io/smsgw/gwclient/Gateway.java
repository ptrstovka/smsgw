package io.smsgw.gwclient;

import java.io.InputStream;
import java.io.OutputStream;

public class Gateway {

    private String accessKey;
    private String port;
    private boolean log;
    private String logPath;
    private int baudRate;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Gateway(String accessKey, String port, boolean log, String logPath, int baudRate) {
        setAccessKey (accessKey);
        setPort(port);
        setLog(log);
        setLogPath( logPath);
        setBaudRate(baudRate);
    }

    public String getAccessKey() {
        return accessKey;
    }

    private void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getPort() {
        return port;
    }

    private void setPort(String port) {
        this.port = port;
    }

    public boolean isLog() {
        return log;
    }

    private void setLog(boolean log) {
        this.log = log;
    }

    public String getLogPath() {
        return logPath;
    }

    private void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getBaudRate() {
        return baudRate;
    }

    private void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }
}
