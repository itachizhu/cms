package org.itachi.cms.action;

import org.itachi.cms.dto.UserDTO;
import org.itachi.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

    @GET
    @Path("/hello")
    @Produces(APPLICATION_JSON_UTF8)
    public UserDTO hello() throws Exception {
        return userRepository.getUserByW3Id("z00314494");
    }
}
