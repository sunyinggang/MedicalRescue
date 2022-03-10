package com.jointthinker.framework.common.exceptions;

public class SystemRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	protected Throwable throwable;
    
    public SystemRuntimeException() {
        super("System Runtime Error");
        throwable = null;
    }
    
    public SystemRuntimeException(String msg) {
        super(msg);
        throwable = null;
    }

    public SystemRuntimeException(Throwable throwable) {
        this.throwable = throwable;
    }

    public SystemRuntimeException(String msg, Throwable throwable) {
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
