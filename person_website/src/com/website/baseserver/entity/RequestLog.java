package com.website.baseserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 请求记录
 * @author dream
 *
 */
@Entity
@Table(name="requestlog")
public class RequestLog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false, name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;//编号
	
	@Column(name="ip",length=100)
	private String ip;//请求ip
	
	@Column(name="type",length=10)
	private String type;//类型
	
	@Column(name="createtime",length=30)
	private String createtime;//创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	

	
	
}
