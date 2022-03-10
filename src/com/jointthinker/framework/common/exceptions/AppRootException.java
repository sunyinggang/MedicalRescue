package com.jointthinker.framework.common.exceptions;

public class AppRootException extends Exception {

	private static final long serialVersionUID = 1L;
	protected Throwable throwable;

    public AppRootException() {
        super("Application Error");
        throwable = null;
    }

    public AppRootException(String msg) {
        super(msg);
        throwable = null;
    }

    public AppRootException(Throwable throwable) {
        this.throwable = throwable;
    }

    public AppRootException(String msg, Throwable throwable) {
        this(msg);
        this.throwable = throwable;
    }

    public Throwable getLinkedException() {
        return throwable;
    }

    public void setLinkedException(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
	public String getMessage() {
        if (throwable == null) {
            return super.getMessage();
        } else {
            return super.getMessage() + ": " + throwable.getMessage();
        }
    }
    
}
