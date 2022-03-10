package com.jointthinker.framework.common.exceptions;

public class PersistentException extends AppRootException{

	private static final long serialVersionUID = 1L;

	public PersistentException() {
        super("Persistent Operation failure");
    }

    public PersistentException(String msg) {
        super(msg);
    }

    public PersistentException(Throwable throwable) {
        this.throwable = throwable;
    }

    public PersistentException(String msg, Throwable throwable) {
        this(msg);
        this.throwable = throwable;
    }
}
