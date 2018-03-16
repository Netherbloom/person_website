package com.website.core.vo;

import java.util.Map;


/**
 * 页面信息返回
 * 
 * @author root
 * 
 */
public class ResultVo {
	private boolean result; // 状态 true为成功
	private String msg; // 弹出消息 
	private Map<String,Object> ret;

	public Map<String, Object> getRet() {
		return ret;
	}

	public void setRet(Map<String, Object> ret) {
		this.ret = ret;
	}

	public ResultVo() {
		this.result = true;
		this.msg = "";
	}

	public ResultVo(boolean result, String msg) {
		this.result = result;
		this.msg = msg;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
