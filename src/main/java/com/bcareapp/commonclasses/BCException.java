package com.bcareapp.commonclasses;

public class BCException extends Exception {

	private static final long serialVersionUID = -3965140316789517795L;

	public BCException() {

	}

	public BCException(String message) {
		super(message);
	}

	public BCException(Throwable e) {
		super(e);
	}

	public BCException(String message, Throwable e) {
		super(message, e);
	}

}
