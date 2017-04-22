package org.itachi.cms.dto;

import java.io.Serializable;

/**
 * Created by itachi on 2017/4/23.
 * User: itachi
 * Date: 2017/4/23
 * Time: 01:29
 */
public class AdminUserDTO implements Serializable {
    private long id;
    private String account;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
