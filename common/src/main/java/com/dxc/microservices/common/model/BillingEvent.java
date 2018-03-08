package com.dxc.microservices.common.model;

/**
 * A model class to encapsulate a billing event.  Action can be "BILL" or "CREDIT".
 * 
 * @author liemmn
 *
 */
public class BillingEvent {
    private String action;
    private int customerId;
    private double amount;
    
    public BillingEvent() {} // Required for Kafka serialization/deserialization
    
    public BillingEvent(String action, int customerId, double amount) {
        this.action = action;
        this.customerId = customerId;
        this.amount = amount;
    }
    
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
