package org.itachi.cms.action;

import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by itachi on 2017/4/12.
 * User: itachi
 * Date: 2017/4/12
 * Time: 23:03
 */
@Path("/")
public class ViewAction extends BaseAction {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Viewable index() throws Exception {
        return new Viewable("/index");
    }
}
