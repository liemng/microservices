package com.dxc.microservices.subscription.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dxc.microservices.subscription.model.Subscription;

@RepositoryRestResource(collectionResourceRel = "subscriptions", path = "subscriptions")
public interface SubscriptionRepository extends PagingAndSortingRepository<Subscription, Long> {
    List<Subscription> findByCustomerId(@Param("customerId") int customerId);
    
    @SuppressWarnings("unchecked")
    @Override
    @PreAuthorize("hasRole('ROLE_SUBSCRIBER')")
    Subscription save(Subscription subscription);
}
