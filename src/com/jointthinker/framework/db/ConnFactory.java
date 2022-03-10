package com.jointthinker.framework.db;


public class ConnFactory {
    private static ConnManager instance=null;
    public ConnFactory() {
    }
    public static ConnManager getConnManager()
    {
        if(instance==null)
            instance=(ConnManager)new DBPoolManager();
        return instance;
    }
}
