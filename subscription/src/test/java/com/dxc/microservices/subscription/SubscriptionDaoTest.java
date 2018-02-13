package com.dxc.microservices.subscription;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.dxc.microservices.subscription.dao.MovieRepository;
import com.dxc.microservices.subscription.dao.SubscriptionRepository;
import com.dxc.microservices.subscription.model.Movie;
import com.dxc.microservices.subscription.model.Subscription;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubscriptionDaoTest {
    
    @Autowired
    private TestEntityManager em;
    
    @Autowired
    private MovieRepository mr;
    
    @Autowired
    private SubscriptionRepository sr;

    @Test
    public void testFindByCustomerId() {
        Subscription s = new Subscription();
        s.setCustomerId(127);
        Movie starwars = mr.findByTitle("Star Wars");
        s.addMovie(starwars);
        s.addMovie(mr.findByTitle("American Pie"));
        em.persistAndFlush(s);
        
        List<Subscription> subs = sr.findByCustomerId(127);
        assertNotNull(subs);
        assertEquals(subs.size(), 1);
        Subscription actual = subs.get(0);
        assertEquals(actual.getMovies().size(), 2);
        actual.removeMovie(starwars);
        em.persistAndFlush(actual);
        
        Subscription modSub = sr.findByCustomerId(127).get(0);
        assertEquals(modSub.getMovies().size(), 1);
        assertEquals(modSub.getMovies().iterator().next().getTitle(), "American Pie");
    }

}
