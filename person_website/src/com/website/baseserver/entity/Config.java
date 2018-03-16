package com.website.baseserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统配置
 * @author dream
 *
 */
@Entity
@Table(name="config",uniqueConstraints={@UniqueConstraint(columnNames={"systemkey"})})
public class Config implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;//主键
	
	@Column(name="systemkey",length=20)
	private String systemkey;//键
	
	@Column(name="systemvalue",length=200)
	private String systemvalue;//键值
	
	@Column(name="memo",length=200)
	private String memo;//说明
	
	@Column(name="isuse",length=4)
	private int isuse;//是否启用（0否1是）

	@Column(name="careatedate")
	private String careatedate;//创建时间
	
	@Column(name="updatedate")
	private String updatedate;//更新时间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getSystemkey() {
		return systemkey;
	}

	public void setSystemkey(String systemkey) {
		this.systemkey = systemkey;
	}

	public String getSystemvalue() {
		return systemvalue;
	}

	public void setSystemvalue(String systemvalue) {
		this.systemvalue = systemvalue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getIsuse() {
		return isuse;
	}

	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}

	public String getCareatedate() {
		return careatedate;
	}

	public void setCareatedate(String careatedate) {
		this.careatedate = careatedate;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	
}
