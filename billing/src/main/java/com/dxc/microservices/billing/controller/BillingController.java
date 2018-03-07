package com.dxc.microservices.billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dxc.microservices.billing.model.Charge;
import com.dxc.microservices.billing.model.CustomerAccount;
import com.dxc.microservices.billing.service.BillingService;

@RepositoryRestController
public class BillingController {
    @Autowired
    private BillingService billingService;

    @RequestMapping(method = RequestMethod.POST, value = "/accounts/{customerId}/credit")
    @PreAuthorize("hasRole('ROLE_SUBSCRIBER')")
    public @ResponseBody ResponseEntity<?> credit(@PathVariable int customerId, @RequestBody Charge charge) {
        CustomerAccount acct = billingService.credit(customerId, charge);
        Resource<CustomerAccount> res = new Resource<>(acct);
        return ResponseEntity.ok(res);
    }
}
