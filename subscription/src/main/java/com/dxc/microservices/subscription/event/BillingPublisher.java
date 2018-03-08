package com.dxc.microservices.subscription.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.rest.core.annotation.HandleAfterLinkSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.dxc.microservices.common.model.BillingEvent;
import com.dxc.microservices.subscription.model.Movie;
import com.dxc.microservices.subscription.model.Subscription;

@Component
@RepositoryEventHandler(Subscription.class) // Listens to JPA events for Subscription
@EnableBinding(Source.class)                // Binds to default Kafka output channel
public class BillingPublisher {
    private Source source;
    
    @Autowired
    public BillingPublisher(Source source){
        this.source = source;
    }
    
    @HandleAfterLinkSave
    void handleSubscribedMovies(Subscription subscription, Object linked) {
        for (Movie movie : subscription.getMovies()) {
            publishBillingEvent("BILL", subscription.getCustomerId(), movie.getPrice());
        }
    }

    private void publishBillingEvent(String action, int customerId, double amount) {
        BillingEvent event = new BillingEvent(action, customerId, amount);
        source.output().send(MessageBuilder.withPayload(event).build());
    }
}
