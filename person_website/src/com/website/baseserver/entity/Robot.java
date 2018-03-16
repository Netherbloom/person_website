package com.website.baseserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机器人问答
 * @author dream
 *
 */
@Entity
@Table(name="robot")
public class Robot implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(nullable = false, name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;//编号
	
	@Column(name="question",length=500)
	private String question;//问题
	
	@Column(name="relquestion",length=500)
	private String relquestion;//相关问题
	
	@Column(name="content",length=500)
	private String content;//回复内容
	
	@Column(name="type",length=100)
	private String type;//回复类型

	@Column(name="createdate",length=30)
	private String createdate;//创建时间
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRelquestion() {
		return relquestion;
	}

	public void setRelquestion(String relquestion) {
		this.relquestion = relquestion;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
}
