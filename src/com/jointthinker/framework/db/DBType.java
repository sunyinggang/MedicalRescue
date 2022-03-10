package com.jointthinker.framework.db;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author martin.guo
 * @version 1.0
 */

public class DBType {
    private String dbType;
/**Oracle */
    public static DBType ORACE = new DBType("oracle");
/**Mssql */
    public static DBType MSSQL = new DBType("sqlserver");
/**Mysql */
    public static DBType MYSQL = new DBType("mysql");
/**Informix */
    public static DBType INFORMIX = new DBType("Informix");


    /**
     * We consider the ldap as a special database.
     */
    public static DBType LDAP = new DBType("Ldap");
/** */
    public static DBType[] ALL_TYPES = {ORACE,MSSQL,MYSQL,INFORMIX,LDAP};

    private DBType(String type) {
        dbType = type;
    }
    /**
     * get database type
     * @return String
     */
    public String getType(){
        return dbType;
    }
    /**
     * compare types
     * @param type DBType
     * @return boolean
     */
    public boolean equals(DBType type){
        return dbType.equals(type.getType());
    }

}
