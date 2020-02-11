package facades;

import utils.EMF_Creator;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;
    private Movie r1;
    private Movie r2;

    public MovieFacadeTest() {
    }
    
    @BeforeAll
    public static void setUpClassV2() {
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        String[] actors = {"Actor 1", "And actor 2"};
        r1 = new Movie(2000, "ABC", actors);
        r2 = new Movie(2001, "DEF", actors);
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Movie").executeUpdate();
            em.persist(r1);
            em.persist(r2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testMovieCount() {
        assertEquals(2,facade.getMovieCount());
    }
    
    @Test
    public void testGetAllMovies(){
        List<Movie> movies = facade.getAllMovies();
        assertThat(movies, everyItem(hasProperty("name")));
    }
    
    @Test
    public void testGetMoviesByName(){
        List<Movie> movies = facade.getMoviesByName(r1.getName());
        assertEquals(movies.get(0).getYear(), r1.getYear());
        //We use year here in order to make sure that we have the right movie.
    }
    
    @Test
    public void testGetMoviesById(){
        Movie movies = facade.getMovie(r2.getId());
        assertEquals(movies.getYear(), r2.getYear());
        //We use year here in order to make sure that we have the right movie.
    }


}
