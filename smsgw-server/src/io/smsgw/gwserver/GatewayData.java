package io.smsgw.gwserver;

import java.util.ArrayList;

public class GatewayData {

    private ArrayList<String> operators;

    public GatewayData() {
        operators = new ArrayList<>();
    }

    public void addOperator(String operator) {
        this.operators.add(operator);
    }

    public boolean hasOperator(String operator){
        return this.operators.contains(operator);
    }

    public boolean hasAll(){
        return this.operators.isEmpty();
    }

}
