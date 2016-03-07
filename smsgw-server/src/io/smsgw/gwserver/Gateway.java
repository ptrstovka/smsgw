package io.smsgw.gwserver;

public class Gateway {

    public String accessKey;
    public GatewayData gatewayData;

    public Gateway(String accessKey, GatewayData gatewayData) {
        this.accessKey = accessKey;
        this.gatewayData = gatewayData;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public GatewayData getGatewayData() {
        return gatewayData;
    }

    public void setGatewayData(GatewayData gatewayData) {
        this.gatewayData = gatewayData;
    }

    public boolean hasOperator(String operator) {
        return gatewayData.hasOperator(operator);
    }

    public boolean hasAllOperator(){
        return this.gatewayData.hasAll();
    }

}
