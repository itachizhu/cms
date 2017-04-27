package org.itachi.cms.dto;

import java.io.Serializable;

/**
 * Created by kyoiorizzf on 2017/4/14. User: kyoiorizzf Date: 2017/4/14 Time:
 * 13:17
 */
public class GroupRoleRelDTO implements Serializable {

	private Long id;
	private Long groupid;
	private Long roleid;
	private int isdel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGroupid() {
		return groupid;
	}

	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

}
