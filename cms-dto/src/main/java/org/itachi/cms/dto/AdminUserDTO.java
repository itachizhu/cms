package org.itachi.cms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 01:29
 */
public class AdminUserDTO implements Serializable {
    private Long id;
    private String account;
    private String mail;
    private String name;
    private String phone;
    private String department;
    private String password;
    private String groupids;
    private Date createTime;
    private Date updateTime;
    private int isdel;

    public String getGroupids() {
        return groupids;
    }

    public void setGroupids(String groupids) {
        this.groupids = groupids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }
}
