package com.dxc.microservices.subscription.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.dxc.microservices.subscription.model.Movie;

@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Movie findByTitle(@Param("title") String title);
    List<Movie> findByGenre(@Param("genre") String genre);
}
