package com.dxc.microservices.billing.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import com.dxc.microservices.billing.model.Charge;
import com.dxc.microservices.billing.service.BillingService;

@EnableBinding(Sink.class)  // Binds to default Kafka input channel
public class BillingConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(BillingConsumer.class);
    @Autowired
    private BillingService billingService;

    @StreamListener(Sink.INPUT)
    public void loggerSink(BillingEvent billing) {
        LOG.info(billing.getAction() + " customer " + billing.getCustomerId() + " for " + billing.getAmount());
        if (billing.getAction().equalsIgnoreCase("bill")) 
            billingService.bill(billing.getCustomerId(), new Charge(billing.getAmount()));
        else 
            billingService.credit(billing.getCustomerId(), new Charge(billing.getAmount()));
    }
}
