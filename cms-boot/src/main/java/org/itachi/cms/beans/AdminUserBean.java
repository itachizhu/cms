package org.itachi.cms.beans;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by kyo on 2017/5/15.
 */
public class AdminUserBean {

    private Long id;
    @Size(min = 1, max = 20, message = "账号长度不合法")
    private String account;
    @Size(min = 1, max = 20, message = "邮箱长度不合法")
    private String mail;
    @Size(min = 6, max = 20, message = "姓名长度不合法")
    private String name;
    @Size(min = 11, max = 11, message = "手机格式不合法")
    private String phone;
    @Size(min = 1, max = 20, message = "部门长度不能超过20个字符")
    private String department;
    @Size(min = 0, max = 20, message = "密码长度不能超过20个字符")
    private String password;

    private String groupids;

    private Date createTime;

    private Date updateTime;

    private int isdel;

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

    public String getGroupids() {
        return groupids;
    }

    public void setGroupids(String groupids) {
        this.groupids = groupids;
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

