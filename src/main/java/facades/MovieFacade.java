package facades;

import entities.Movie;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //CRUD
    public Movie createMovie(Long id, int year, String name, String[] actors) {
        Movie movie = new Movie(id, year, name, actors);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            return movie;
        } finally {
            em.close();
        }
    }
    
    public Movie getMovie(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Movie movie = em.find(Movie.class, id);
            return movie;
        } finally {
            em.close();
        }
    }
    
    public void populateDB() {
        EntityManager em = emf.createEntityManager();
        //Add Movie
        String[] actors = {"Actor 1", "And actor 2"};
        Movie e1 = createMovie(1L, 2000, "ABC", actors);
        Movie e2 = createMovie(2L, 2001, "DEF", actors);
        Movie e3 = createMovie(3L, 2002, "GHI", actors);
    }

}
