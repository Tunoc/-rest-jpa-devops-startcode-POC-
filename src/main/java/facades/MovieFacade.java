package facades;

import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

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
    public Movie createMovie(int year, String name, String[] actors) {
        Movie movie = new Movie(year, name, actors);
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

    public List<Movie> getAllMovies() {
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT m FROM Movie m").getResultList();
    }

    public long getMovieCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long movieCount = (long) em.createQuery("SELECT COUNT(m) FROM Movie m").getSingleResult();
            return movieCount;
        } finally {
            em.close();
        }
    }

    public List<Movie> getMoviesByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m WHERE m.name = :name", Movie.class);
            query.setParameter("name", name);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void populateDB() {
        EntityManager em = emf.createEntityManager();
        //Add Movie
        String[] actors = {"Actor 1", "And actor 2"};
        Movie e1 = createMovie(2000, "ABC", actors);
        Movie e2 = createMovie(2001, "DEF", actors);
        Movie e3 = createMovie(2002, "GHI", actors);
    }

}
