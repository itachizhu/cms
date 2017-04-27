package org.itachi.cms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kyoiorizzf on 2017/4/14. User: kyoiorizzf Date: 2017/4/14 Time:
 * 13:17
 */
public class AdmusergroupDTO implements Serializable {

	private Long id;
	private String groupname;
	private String des;
	private Date createtime;
	private Date updatetime;
	private int isdel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

}
