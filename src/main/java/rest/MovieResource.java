package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Movie;
import utils.EMF_Creator;
import facades.MovieFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

//Todo Remove or change relevant parts before ACTUAL use
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/startcode",
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
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson2(@PathParam("name") String name) {
        List<Movie> movie = FACADE.getMoviesByName(name);
        return new Gson().toJson(movie);
    }
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJson3() {
        List<Movie> movie = FACADE.getAllMovies();
        return new Gson().toJson(movie);
    }
    
    @GET
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    public String getJson4() {
        long count = FACADE.getMovieCount();
        return "{\"count\":"+count+"}";
    }
    
    @GET
    @Path("/populate/database")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson5() {
        FACADE.populateDB();
        String msg = "Updated DB";
        return new Gson().toJson(msg);
    }
    
    @GET
    @Path("lotto/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String lotto(@PathParam("id") int id) {
        if(id!=5){
            throw new WebApplicationException("UUUPS");
        }
       return "{\"lotto\":{\"lottoId\":5,\"winning-numbers\":[2,45,34,23,7,5,3],\"winners\":[{\"winnerId\":23,\"numbers\":[2,45,34,23,3,5]},{\"winnerId\":54,\"numbers\":[52,3,12,11,18,22]}]}}";
    }


}
