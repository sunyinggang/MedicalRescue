package com.jointthinker.framework.core;
import java.util.*;
import java.io.StringWriter;
import java.io.PrintWriter;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BaseException extends RuntimeException{
    public BaseException() {
    }
    public BaseException(String msg) {
        super(msg);
    }
    public BaseException(Exception ex) {
        super(ex);
    }

    public String getStackTraceString() {
        String temp = "";
        StringWriter st = new StringWriter();
        this.printStackTrace(new PrintWriter(st));
        temp = st.toString();
        return temp;
    }

}
