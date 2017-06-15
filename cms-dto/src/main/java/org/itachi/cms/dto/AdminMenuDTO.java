package org.itachi.cms.dto;

import java.io.Serializable;

/**
 * Created by itachi on 2017/6/15.
 * User: itachi
 * Date: 2017/6/15
 * Time: 15:12
 */
public class AdminMenuDTO implements Serializable {
    private Long id;
    private Long pId;
    private String no;
    private String name;
    private String url;
    private Integer type;
    private Integer order;
    private Integer isChild;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getIsChild() {
        return isChild;
    }

    public void setIsChild(Integer isChild) {
        this.isChild = isChild;
    }
}
