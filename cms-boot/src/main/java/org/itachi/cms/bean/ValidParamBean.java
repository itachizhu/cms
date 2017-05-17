package org.itachi.cms.bean;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;

/**
 * Created by itachi on 2017/5/17.
 * User: itachi
 * Date: 2017/5/17
 * Time: 18:50
 */
public class ValidParamBean {
    @NotNull
    @NotEmpty
    @QueryParam("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
