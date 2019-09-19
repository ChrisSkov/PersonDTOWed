package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import entities.PersonDTO;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("xxx")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/Person",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("count")
    @GET
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    public String getRenameMeCount()
    {
        long count = FACADE.getRenameMeCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":" + count + "}";  //Done manually so no need for a DTO
    }

    @Path("/all")
    @GET
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    public String getAllPeople()
    {
        List<PersonDTO> dto = new ArrayList();
        for (Person p : FACADE.getAllPeople())
        {
            dto.add(new PersonDTO(p));
        }
        return new Gson().toJson(dto);
    }

    @Path("/{id}")
    @GET
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    public String getSingle(@PathParam("id") int id)
    {
        PersonDTO dto = new PersonDTO(FACADE.getPerson(id));
        return new Gson().toJson(dto);
    }

    @Path("/delete/{id}")
    @PUT
    public String deletePerson(@PathParam("id") int id)
    {
        return new Gson().toJson(FACADE.deletePerson(id));
    }

    @Path("/add/{fName}/{lName}/{phone}")
    @POST
    @Produces(
    {
        MediaType.APPLICATION_JSON
    })
    @Consumes(
    {
        MediaType.APPLICATION_JSON
    })
    public String addPerson(@PathParam("fName") String fName, @PathParam("lName") String lName, @PathParam("phone") String phone)
    {
        PersonDTO dto = new PersonDTO(FACADE.addPerson(fName, lName, phone));
        return new Gson().toJson(dto);
    }

}
