package org.itachi.cms.dto;

import java.io.Serializable;

/**
 * Created by kyoiorizzf on 2017/4/14. User: kyoiorizzf Date: 2017/4/14 Time:
 * 13:17
 */
public class RoleDTO implements Serializable {

	private Long id;
	private Long pid;
	private String name;
	private String module;
	private String action;
	private String roleurl;
	private int ismenu;
	private String describe;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRoleurl() {
		return roleurl;
	}

	public void setRoleurl(String roleurl) {
		this.roleurl = roleurl;
	}

	public int getIsmenu() {
		return ismenu;
	}

	public void setIsmenu(int ismenu) {
		this.ismenu = ismenu;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
