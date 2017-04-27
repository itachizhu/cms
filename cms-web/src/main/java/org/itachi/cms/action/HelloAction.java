package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;
import org.itachi.cms.dto.AdmuserDTO;
import org.itachi.cms.dto.TestDTO;
import org.itachi.cms.dto.UserDTO;
import org.itachi.cms.mapper.AdmUserGroupMapper;
import org.itachi.cms.repository.TestRepository;
import org.itachi.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 20:01
 */
@Path("/test")
public class HelloAction extends BaseAction {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private AdmUserGroupMapper admUserGroupMapper;

    @GET
    @Path("/hello")
    @Produces(APPLICATION_JSON_UTF8)
    public UserDTO hello() throws Exception {
        return userRepository.getUserByW3Id("z00314494");
    }


    @GET
    @Path("/t")
    @Produces(APPLICATION_JSON_UTF8)
    public List<TestDTO> getTest() throws Exception {
        return testRepository.getTest();
    }

    @POST
    @Path("/deltest")
    @Produces(APPLICATION_JSON_UTF8)
    public int delTest(@FormParam("ids") String ids) throws Exception {

        String[] numbers = ids.split(",");

        int[] roleids = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            roleids[i] = Integer.parseInt(numbers[i]);
        }


        return testRepository.deleteRoleDTO(roleids);

    }


    @POST
    @Path("/null_size")
    @Produces(APPLICATION_JSON_UTF8)
    public String nullandsize(@NotNull(message = "xxxnull") @Size(min = 1, max = 24, message = "xxxsize") @FormParam("name") String name) throws Exception {
        System.out.println(name);
        return "ok";


    }

    @POST
    @Path("/pt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void insertTest(TestDTO testDTO) throws Exception {
        testRepository.insertTest(testDTO);
    }

    @GET
    @Path("/index")
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() throws Exception {
        return new Viewable("/index");
    }


    @POST
    @Path("/finduser")
    @Produces(MediaType.APPLICATION_JSON)
    public AdmuserDTO finduser(@BeanParam AdmuserDTO admuserDTO) throws Exception {
        return admuserDTO;
    }
}
