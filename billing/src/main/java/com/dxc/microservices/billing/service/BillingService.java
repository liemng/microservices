package com.dxc.microservices.billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dxc.microservices.billing.dao.CustomerAccountRepository;
import com.dxc.microservices.billing.model.Charge;
import com.dxc.microservices.billing.model.CustomerAccount;

@Service
public class BillingService {
    @Autowired
    private CustomerAccountRepository repo;
    
    public CustomerAccount bill(int customerId, Charge charge) {
        CustomerAccount acct = repo.findOne(customerId);
        double balance = acct.getBalance();
        acct.setBalance(balance - charge.getAmount());
        repo.save(acct);
        return acct;
    }
    
    public CustomerAccount credit(int customerId, Charge charge) {
        CustomerAccount acct = repo.findOne(customerId);
        double balance = acct.getBalance();
        acct.setBalance(balance + charge.getAmount());
        repo.save(acct);
        return acct;
    } 
}
