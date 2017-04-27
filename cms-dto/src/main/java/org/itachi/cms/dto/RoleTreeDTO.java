package org.itachi.cms.dto;

import java.io.Serializable;

/**
 * Created by kyoiorizzf on 2017/4/14. User: kyoiorizzf Date: 2017/4/14 Time:
 * 13:17
 */
public class RoleTreeDTO implements Serializable {

	private Long id;
	private Long pId;
	private String name;
	private boolean open;
	private boolean checked;
	private String roleurl;
	private String click;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getRoleurl() {
		return roleurl;
	}

	public void setRoleurl(String roleurl) {
		this.roleurl = roleurl;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

}
