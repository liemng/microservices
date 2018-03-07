package com.dxc.microservices.billing.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dxc.microservices.billing.model.CustomerAccount;

@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts")
public interface CustomerAccountRepository extends PagingAndSortingRepository<CustomerAccount, Integer> {

}
