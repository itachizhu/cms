package org.itachi.cms.beans;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

/**
 * Created by itachi on 2017/5/14.
 * User: itachi
 * Date: 2017/5/14
 * Time: 17:00
 */
public class ValidBean {
    @Range(min = 1L, max = 200L, message = "年龄不合法")
    private Long age;
    @Size(min = 1, max = 50, message = "名字长度不合法")
    private String name;

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
