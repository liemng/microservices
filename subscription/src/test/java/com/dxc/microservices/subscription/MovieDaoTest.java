package com.dxc.microservices.subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.dxc.microservices.subscription.dao.MovieRepository;
import com.dxc.microservices.subscription.model.Movie;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieDaoTest {
    
    @Autowired
    private TestEntityManager em;
    
    @Autowired
    private MovieRepository mr;

    @Test
    public void testFindByTitle() {
        Movie movie = mr.findByTitle("Star Wars");
        assertNotNull(movie);
        assertTrue(movie.getTitle().equals("Star Wars"));       
        em.remove(movie);
        assertNull(mr.findByTitle(movie.getTitle()));
    }
    
    @Test
    public void testFindByGenre() {
        List<Movie> movies = mr.findByGenre("Comedy");
        assertEquals(movies.size(), 3);
    }

}
