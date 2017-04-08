package org.itachi.cms.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * Created by itachi on 2017/3/18.
 * User: itachi
 * Date: 2017/3/18
 * Time: 17:01
 */
@Provider
@Consumes(MediaType.WILDCARD)
@Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
public class JacksonIgnoreNullProvider extends JacksonJsonProvider {
    public JacksonIgnoreNullProvider() {
        super(new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true));
    }
}
