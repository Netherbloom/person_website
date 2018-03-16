package com.website.admin.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

/**
 * 书籍
 * @author Administrator
 *
 */
@Entity
@Table(name="ebooks",uniqueConstraints ={@UniqueConstraint(columnNames={"name","writer"})})
public class Ebooks implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;
	
	@Column(name="name")
	public String name;//书名
	
	@Column(name="type")
	public String type;//类型
	
	@Column(name="copyurl")
	public String copyurl;//爬取地址
	
	@Column(name="createtime")
	public String createtime;//创建时间
	
	@Column(name="updatetime")
	public String updatetime;//最后更新时间
	
	@Column(name="writer")
	public String writer;//作者
	
	@Column(name="intro",length=2000)
	public String intro;//简介
	
	@Column(name="cover")
	public String cover;//封面
	
	@Transient
	public  List<EbookChapter> chapters;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCopyurl() {
		return copyurl;
	}

	public void setCopyurl(String copyurl) {
		this.copyurl = copyurl;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}


	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
	
	
	 public List<EbookChapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<EbookChapter> chapters) {
		this.chapters = chapters;
	}

	@Override  
	public boolean equals(Object o) {  
	        if (o instanceof Ebooks) {  
	        	Ebooks ebooks = (Ebooks) o;  
	            return this.name.equals(ebooks.name)&&this.writer.equals(ebooks.writer);  
	        }  
	        return super.equals(o);  
	    }  
}
