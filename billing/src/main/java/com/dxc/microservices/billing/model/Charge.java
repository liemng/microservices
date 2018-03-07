package com.dxc.microservices.billing.model;

public class Charge {
    private double amount;
    
    public Charge() {}
    
    public Charge(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
}
