package org.itachi.cms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DE-9887 on 2017/4/20.
 */
public class TestDTO implements Serializable {

    private Long id;

    private String name;

    private Date createtime;

    private Boolean sex;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }


}
