package com.website.user.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="userinfo")
public class Userinfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;
	
	@Column(length=32)
	private String nickname;//昵称
	
	@Column(nullable = false,length=32)
	private String username;//用户名
	
	@Column(nullable = false,length=32)
	private String password;//密码
	
	@Column(length=11)
	private String phone;//手机号
	
	@Column(length=32)
	private String email;//邮箱
	
	@Column(length=32)
	private String photo;//图片
	
	@Column(length=32)
	private String signature;//签名
	
	@Column(length=2)
	private int status;//状态：0 删除，1正常
	
	@Column(length=2)
	private int type;//1管理员
	
	@Column(length=32)
	private Date creaTime;//创建时间

	@Column(name="wxopenid")
	private String wxopenid;//微信openid
	
	@Column(name="qqopenid")
	private String qqopenid;//QQopenid
	
	@Column(name="register_source")
	private String register_source;//注册渠道
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreaTime() {
		return creaTime;
	}

	public void setCreaTime(Date creaTime) {
		this.creaTime = creaTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getWxopenid() {
		return wxopenid;
	}

	public void setWxopenid(String wxopenid) {
		this.wxopenid = wxopenid;
	}

	public String getQqopenid() {
		return qqopenid;
	}

	public void setQqopenid(String qqopenid) {
		this.qqopenid = qqopenid;
	}

	public String getRegister_source() {
		return register_source;
	}

	public void setRegister_source(String register_source) {
		this.register_source = register_source;
	}
	
}
