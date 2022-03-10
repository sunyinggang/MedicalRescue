package com.jointthinker.framework.common.exceptions;

public class BusinessLogicException extends AppRootException{


	private static final long serialVersionUID = 1L;

	public BusinessLogicException() {
        super();
    }

    public BusinessLogicException(String msg) {
        super(msg);
    }

    public BusinessLogicException(Throwable throwable) {
        super(throwable);
    }

    public BusinessLogicException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
    
}
