package com.website.admin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ebookchapter")
public class EbookChapter implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;
	
	@Column(name="ebookid")
	private String ebookid;

	@Column(name="chapter")
	private String chapter;//章节名
	
	@Column(name="content",length=2000)
	private String content;//内容
	
	@Column(name="copyurl")
	private String copyurl;//章节名
	
	@Column(name="pri",length=8)
	private int pri;//排序
	
	@Column(name="bookname")
	private String bookname;//书名

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEbookid() {
		return ebookid;
	}

	public void setEbookid(String ebookid) {
		this.ebookid = ebookid;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPri() {
		return pri;
	}

	public void setPri(int pri) {
		this.pri = pri;
	}

	public String getCopyurl() {
		return copyurl;
	}

	public void setCopyurl(String copyurl) {
		this.copyurl = copyurl;
	}
	
}
