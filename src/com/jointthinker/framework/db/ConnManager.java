package com.jointthinker.framework.db;

import java.sql.Connection;

public interface ConnManager {
    public Connection getDBConnection();
    public void freeConnection(Connection conn);
}
