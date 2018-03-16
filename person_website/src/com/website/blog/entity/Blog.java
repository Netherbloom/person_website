package com.website.blog.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="blog")
public class Blog implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;
	
	@Column(nullable = false,length=32)
	private String userId;//用户id
	
	@Column(length=32)
	private String parentId;
	
	@Column(length=225)
	private String title;//标题
	
	@Column(length=4)
	private int status;//状态 0：删除、1;正常
	
	@Column(length=11)
	private int browseTimes;//浏览次数
	
	@Column(length=11)
	private int likeTimes;//点赞次数
	
	@Column(length=32)
	private String createTime;//创建时间
	
	@Column(length=32)
	private String ico;//封面图
	
	@Column(length=1024)
	private byte[] content;//内容
	
	@Transient
	private List<Blog> chileBlogs;
	
	@Transient
	private BlogDetail blogDetail;
	
	public String getContentstr() {
		return contentstr;
	}

	public void setContentstr(String contentstr) {
		this.contentstr = contentstr;
	}

	@Transient
	private String contentstr;//内容
	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public BlogDetail getBlogDetail() {
		return blogDetail;
	}

	public void setBlogDetail(BlogDetail blogDetail) {
		this.blogDetail = blogDetail;
	}

	public List<Blog> getChileBlogs() {
		return chileBlogs;
	}

	public void setChileBlogs(List<Blog> chileBlogs) {
		this.chileBlogs = chileBlogs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getBrowseTimes() {
		return browseTimes;
	}

	public void setBrowseTimes(int browseTimes) {
		this.browseTimes = browseTimes;
	}

	public int getLikeTimes() {
		return likeTimes;
	}

	public void setLikeTimes(int likeTimes) {
		this.likeTimes = likeTimes;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}
	
}
