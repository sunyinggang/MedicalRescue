package com.jointthinker.framework.common.util;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
  
import org.apache.log4j.Logger;  
//这个类主要用来处理一个系统调用而新创建一个线程或进程执行期间所产生的输入流的处理  
public class InputStreamWathThread extends Thread {  
    private Process process = null;  
    private boolean over = false;  
    private Logger logger = Logger.getLogger(getClass());  
  
    public InputStreamWathThread(Process p) {  
        process = p;  
        over = false;  
    }  
  
    public void run() {  
        try {  
            if (process == null) {  
                logger.info("process为null，无法处理文件转换");  
                return;  
            }  
  
            //对输入流，可能是一个回车之类的输入  
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));  
            while (true) {  
                if (process == null || over) {  
                    logger.info("处理完成");  
                    break;  
                }  
                String temp;  
                while ((temp = br.readLine()) != null) {  
//                  logger.info("输入流信息:" + temp);//如这些信息:NOTICE  processing PDF page 10 (595x842:0:0) (move:0:0)等等的打印时提示信息  
                    ;  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            logger.info("发生异常" + e.getMessage());  
        }  
    }  
  
    public void setOver(boolean over) {  
        this.over = over;  
    }  
}  