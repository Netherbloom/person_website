package com.website.core.vo;

import java.io.Serializable;

/**
 * 统计Vo
 * 
 * @author ll
 * 
 */
public class StatVo implements Serializable {

	/** 序列版本 */
	private static final long serialVersionUID = 1L;
	private int id; // 编号
	private int type; // 类型
	private long count; // 数量
	private double amount; // 汇总

	public int getId() {
		return id;
	}

	public long getCount() {
		return count;
	}

	public double getAmount() {
		return amount;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public StatVo(int id, long count) {

		this.id = id;
		this.count = count;
	}

	public StatVo(int id, int type, long count) {

		this.id = id;
		this.type = type;
		this.count = count;
	}

	public StatVo(int id, double amount) {

		this.id = id;
		this.amount = amount;
	}

	public StatVo(int id, int type, double amount) {

		this.id = id;
		this.type = type;
		this.amount = amount;
	}

	public StatVo(int id, long count, double amount) {

		this.id = id;
		this.count = count;
		this.amount = amount;
	}

	public StatVo(int id, int type, long count, double amount) {

		this.id = id;
		this.type = type;
		this.count = count;
		this.amount = amount;
	}
}
