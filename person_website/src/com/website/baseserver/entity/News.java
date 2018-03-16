package com.website.baseserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 新闻
 * @author dream
 *
 */
@Entity
@Table(name="news")
public class News {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;//主键
	
	@Column(name="title")
	private String title;//标题
	
	@Column(name="content",length=2000)
	private String content;//内容（以@@@分隔段落）
	
	@Column(name="img")
	private String img;//图片
	
	@Column(name="newsdate")
	private String newsdate;//新闻日期
	
	@Column(name="scource")
	private String scource;//来源
	
	@Column(name="ftype")
	private String ftype;//第一级类型
	
	@Column(name="stype")
	private String stype;//第二级类型
	
	@Column(name="writer")
	private String writer;//责编
	
	@Column(name="createdate")
	private String createdate;//创建日期
	
	@Column(name="updatedate")
	private String updatedate;//更新日期

	@Column(name="status")
	private int status;//状态（0禁用）
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNewsdate() {
		return newsdate;
	}

	public void setNewsdate(String newsdate) {
		this.newsdate = newsdate;
	}

	public String getScource() {
		return scource;
	}

	public void setScource(String scource) {
		this.scource = scource;
	}


	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getStype() {
		return stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	
}
