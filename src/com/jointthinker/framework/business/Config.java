package com.jointthinker.framework.business;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
/**
 * <p>Title: 系统参数配置类 </p>
 *
 * <p>Description: 实现系统参数的配置访问 </p>
 *
 */
public class Config {

    //配置文件路径
    private static final String CONFIGFILE = "config.properties";
    //资源绑定
    private static PropertyResourceBundle propsRB = null;
    private static Properties prop = null;

    //常用属性获取
    private static String ldapCtxServer = null;
    private static String ldapCtxUser  = null;
    private static String ldapCtxUserPass = null;
    private static String ldapCtxFactory  = null;
    private static String ldapCtxPrincipal  = null;
    private static String ldapRootEntry  = null;
    private static String delimiter_attrValue  = null;
    private static String delimiter_oaOrgName  = null;
    private static String delimiter_oaOrgName_convert  = null;
    private static int pageCount = 0;

    //日志文件
    private static FileWriter logFw  = null;   //数据同步日志文件名
    private static FileWriter misAlertFw  = null; //通知MIS管理员的警示文件

    public static boolean isDebug = true;

    //是否已装载数据
    private static boolean isLoad = false;

    /**
     * 构造方法
     */
    public Config() {
        loadData();
    }

    /**
     * 从配置文件中获取属性值
     * @return 1 成功 －1 失败
     */
    private static void loadData(){
    	
        List list = new ArrayList();

        String regString = "classpath*:"+CONFIGFILE;
        ResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
        try {
          Resource[] rs = rpr.getResources(regString);
          Resource[] arr$ = rs; 
          int len$ = arr$.length; 
          System.out.println("---len:" + len$);
          for (int i$ = 0; i$ < len$; ++i$) {
        	Resource r = arr$[i$];
            list.add(r.getURL().getPath());
            //System.out.println("---resource--desc:" + r.getDescription() + "---fname:" + r.getFilename() + " ----" + r.getURL().getPath());
          }
        } catch (IOException e) {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
    	
        isLoad = false;
        if (list.size()>0){
            String cfile = (String)list.get(0);
            try {
    			//System.out.println("Loading config file:"+cfile);
    			File configFile = new File(cfile );
    			FileInputStream inConfig = new FileInputStream( configFile );
    			  
    			propsRB = new PropertyResourceBundle(inConfig);
    			inConfig.close();
    			isLoad = true;
            }catch (java.io.FileNotFoundException ex){
                //System.out.println("系统没有找到配置文件：" +cfile);
            }
            catch(Exception ex ){
                ex.printStackTrace();
            }
        }else{
        	throw new RuntimeException("无法定位"+CONFIGFILE);
        }
    }

    /**
     * 获取属性值
     * @param propKey 属性标识
     * @return 返回属性值
     */
    public static String getProp(String propKey){
      try{
        if (!isLoad)
          loadData();
        if (propsRB == null) {
          //System.out.println("当前目录和类路径下找不到系统配置文件：" + CONFIGFILE);
          return null;
        }
        else {
          return propsRB.getString(propKey);
        }
      }catch(Exception e){
        return "";
      }
    }

    public static boolean getBooleanbyKey(String key){
    	return "true".equalsIgnoreCase(getProp(key));
    }
    
    /**
     * 获取资源绑定对象
     * @param
     * @return
     */
    public static PropertyResourceBundle getPropsRB(){
        if ( !isLoad ) loadData();
        if ( propsRB == null )
            System.out.println("当前目录和类路径下找不到系统配置文件：" + CONFIGFILE);
        return propsRB;
    }

    //=======================================================
    /**
     * 获取属性值分隔符
     * @return 返回属性值分隔符
     */
    public static String getDelimiterAttrValue(){
        if ( delimiter_attrValue == null)
            delimiter_attrValue = getProp("delimiter_attrValue");
        return delimiter_attrValue;
    }
    /**
     * 获取OA组织名称分隔符
     * @return 返回OA组织名称分隔符
     */
    public static String getDelimiterOaOrgName(){
        if ( delimiter_oaOrgName == null)
            delimiter_oaOrgName = getProp("delimiter_oaOrgName");
        return delimiter_oaOrgName;
    }
    /**
     * 获取OA组织名称分隔符
     * @return 返回OA组织名称分隔符
     */
    public static String getDelimiterOaOrgNameConvert(){
        if ( delimiter_oaOrgName_convert == null)
            delimiter_oaOrgName_convert = getProp("delimiter_oaOrgName_convert");
        return delimiter_oaOrgName_convert;
    }
    /**
     * 获取ldapCtxServer
     * @return 返回ldapCtxServer
     */
    public static String getLdapCtxServer(){
        if ( ldapCtxServer == null)
            ldapCtxServer = getProp("ldapCtxServer");
        return ldapCtxServer;
    }
    /**
     * 获取ldapCtxUser
     * @return 返回ldapCtxUser
     */
    public static String getLdapCtxUser(){
        if ( ldapCtxUser == null)
            ldapCtxUser = getProp("ldapCtxUser");
        return ldapCtxUser;
    }
    /**
     * 获取ldapCtxUserPass
     * @return 返回ldapCtxUserPass
     */
    public static String getLdapCtxUserPass(){
        if ( ldapCtxUserPass == null)
            ldapCtxUserPass = getProp("ldapCtxUserPass");
        return ldapCtxUserPass;
    }
    /**
     * 获取ldapCtxFactory
     * @return 返回ldapCtxFactory
     */
    public static String getLdapCtxFactory(){
        if ( ldapCtxFactory == null)
            ldapCtxFactory = getProp("ldapCtxFactory");
        return ldapCtxFactory;
    }
    /**
     * 获取ldapCtxPrincipal
     * @return 返回ldapCtxPrincipal
     */
    public static String getLdapCtxPrincipal() {
        if ( ldapCtxPrincipal == null )
            ldapCtxPrincipal = getProp( "ldapCtxPrincipal" );
        return ldapCtxPrincipal;
    }
    /**
     * 获取ldapRootEntry
     * @return 返回ldapRootEntry
     */
    public static String getLdapRootEntry(){
        if ( ldapRootEntry == null)
            ldapRootEntry = getProp("ldapRootEntry");
        return ldapRootEntry;
    }
    /**
     * 获取pageCount 每页显示的记录数
     * @return 返回 pageCount
     */
    public static int getPageCount(){
    	if (pageCount == 0){
    		String tmp  = getProp("pageCount");
    		if ("".equals(tmp)){
    			pageCount = 20;
    		}else{
    			pageCount = Integer.parseInt(tmp);
    		}
    	}
    	return pageCount;
    }

    /**
     * 获取日志文件句柄
     * @return 返回日志文件句柄
     */
    public static FileWriter getLogFile() {
        if ( logFw == null ) openLogFile();
        return logFw;
    }

    /**
     * 获取日志文件句柄
     * @return 返回日志文件句柄
     */
    public static FileWriter getMisAlertFile() {
        if ( misAlertFw == null ) openLogFile();
        return misAlertFw;
    }

    /**
     * open log file
     * @return fileWriter
     * @return 1 succeed -1 fail
     */
    private static int openLogFile() {
        try{
            SimpleDateFormat syncDtaFmt = new SimpleDateFormat("yyyyMMdd");
            String dataSyncStr = syncDtaFmt.format( new java.util.Date() );
            String logDirStr = getProp( "logDir" );
            //日志文件
            String logFilePath = logDirStr + "syncLDAP" + dataSyncStr + ".log";
            if ( logFw == null ) {
                logFw = new FileWriter( logFilePath, true );
            }
            //MIS管理员 警示文件
            String misAlertPath = logDirStr + "misAlert" + dataSyncStr + ".log";
            if ( misAlertFw == null ) {
                misAlertFw = new FileWriter( misAlertPath, true );
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    /**
     * 主方法，测试
     */
    public static void main(String args[]){
        //枚举属性值
        Config cfg =  new Config();
//        PropertyResourceBundle propsRB = cfg.getPropsRB();
//        Enumeration keys = propsRB.getKeys();
//        while ( keys.hasMoreElements() ){
//            String key = keys.nextElement().toString();
//            System.out.println("【key】" + key + "【value】" + propsRB.getString(key));
//        }
    }

}//end Config
