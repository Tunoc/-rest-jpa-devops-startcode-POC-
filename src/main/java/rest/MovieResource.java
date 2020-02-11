package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("xxx")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/movieDB",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final MovieFacade FACADE = MovieFacade.getMovieFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson1(@PathParam("id") Long id) {
        Movie movie = FACADE.getMovie(id);
        return new Gson().toJson(movie);
    }
    
    @GET
    @Path("/populate/database")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson5() {
        FACADE.populateDB();
        String msg = "Updated DB";
        return new Gson().toJson(msg);
    }

}
