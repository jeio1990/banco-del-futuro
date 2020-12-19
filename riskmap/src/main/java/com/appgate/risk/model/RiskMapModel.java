package com.appgate.risk.model;

public class RiskMapModel {

    private String[][] riskValues;
    private String provider;
    private int serviceLevel;

    public String[][] getRiskValues() {
        return riskValues;
    }

    public void setRiskValues(String[][] riskValues) {
        this.riskValues = riskValues;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public int getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(int serviceLevel) {
        this.serviceLevel = serviceLevel;
    }
}
