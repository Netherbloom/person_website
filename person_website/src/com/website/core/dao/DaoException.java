package com.website.core.dao;

/**
 * DAO异常
 * @author liangli
 *
 */
public class DaoException extends RuntimeException {

	/** */
	private static final long serialVersionUID = 1L;

	public DaoException() {
		
	}
	
	public DaoException(String msg) {
		super(msg);
	}
	
	public DaoException(Throwable t) {
		super(t);
	}
	
}
