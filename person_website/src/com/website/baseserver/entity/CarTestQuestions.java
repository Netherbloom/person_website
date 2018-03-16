package com.website.baseserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 驾照试题
 * @author dream
 *
 */
@Entity
@Table(name="car_test_questions")
public class CarTestQuestions implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;//主键
	
	@Column(name="subject",length=10)
	private String subject;//科目类别（科目类别 1为科目一 4为科目四 默认1）
	
	@Column(name="type",length=10)
	private String type;//题目类型（题目类型 分为A1,A3,B1,A2,B2,C1,C2,C3,D,E,F 默认C1）
	
	@Column(name="question",length=1000)
	private String question;//题目
	
	@Column(name="option1",length=1000)
	private String option1;//选项1
	
	@Column(name="option2",length=1000)
	private String option2;//选项2
	
	@Column(name="option3",length=1000)
	private String option3;//选项3
	
	@Column(name="option4",length=1000)
	private String option4;//选项4
	
	@Column(name="answer",length=1000)
	private String answer;//答案
	
	@Column(name="a_explain",length=1000)
	private String aexplain;//解析
	
	@Column(name="pic",length=200)
	private String pic;//图片
	
	@Column(name="apply_type",length=200)
	private String apply_type;//适用类型
	
	@Column(name="chapter",length=200)
	private String chapter;

	@Column(name="createtime",length=30)
	private String createtime;//创建时间
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
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

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAexplain() {
		return aexplain;
	}

	public void setAexplain(String aexplain) {
		this.aexplain = aexplain;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getApply_type() {
		return apply_type;
	}

	public void setApply_type(String apply_type) {
		this.apply_type = apply_type;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	
	
}
