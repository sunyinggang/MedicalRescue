package com.jointthinker.framework.common.util;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.jointthinker.framework.business.Config;
 
 
/**
 * doc docx格式转换 
 */
public class DocConverter {
    private static final int environment = 1;// 环境 1：Windows 2：Linux
    private String fileString;// (只涉及PDF2swf路径问题)
    private String outputPath = "";// 输入路径 ，如果不设置就输出在默认 的位置
    private String fileName;
    private File pdfFile;
    private File swfFile;
    private File docFile;
 
    public DocConverter(String fileString) {
        ini(fileString);
        System.out.println("文件路径"+fileString);
    }
 
    /**
     * * 重新设置file
     *
     * @param fileString
     *            32.
     */
    public void setFile(String fileString) {
        ini(fileString);
    }
 
    /**
     *  * 初始化
     *
     * @param fileString
     *          
     */
    private void ini(String fileString) {
        this.fileString = fileString;
        fileName = fileString.substring(0, fileString.lastIndexOf("."));
        docFile = new File(fileString);
        pdfFile = new File(fileName+ ".pdf");
        swfFile = new File(fileName+ ".swf");
    }
 
    /**
     *  转为PDF
     *
     * @param file
     *      
     */
    private void doc2pdf() throws Exception {
        if (docFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
                try {
                    connection.connect();
                    DocumentConverter converter = new OpenOfficeDocumentConverter(
                            connection);
                    converter.convert(docFile, pdfFile);
                    // close the connection
                    connection.disconnect();
                    System.out.println("****pdf转换成功，PDF输出： "+ pdfFile.getPath() + "****");
                } catch (java.net.ConnectException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，openoffice 服务未启动！****");
                    throw e;
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，读取转换文件 失败****");
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                System.out.println("****已经转换为pdf，不需要再进行转化 ****");
            }
        } else {
            System.out.println("****swf转换器异常，需要转换的文档不存在， 无法转换****");
        }
    }
 
    /** * 转换成 swf */
    @SuppressWarnings("unused")
    private void pdf2swf() throws Exception {
        Runtime r = Runtime.getRuntime();
        if (!swfFile.exists()) {
            if (pdfFile.exists()) {
                if (environment == 1) {// windows环境处理
                    try {
                         /*Process p = r.exec("F:/ruanjian/SWFTools/pdf2swf.exe -t "+ pdfFile.getPath() + " -s flashversion=9 -o "+ swfFile.getPath());
                         System.out.print(loadStream(p.getInputStream()));
                         System.err.print(loadStream(p.getErrorStream()));
                         System.out.print(loadStream(p.getInputStream()));
                         System.err.println("****swf转换成功，文件输出： "+swfFile.getPath() + "****");*/
                    	 PDF2SWF(pdfFile.getPath(), swfFile.getPath());
                        if (pdfFile.exists()){
                            //pdfFile.delete();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw e;
                    }
                } else if (environment == 2) {// linux环境处理
                    try {
                        Process p = r.exec("pdf2swf" + pdfFile.getPath()+ " -o " + swfFile.getPath() + " -T 9");
                         System.out.print(loadStream(p.getInputStream()));
                         System.err.print(loadStream(p.getErrorStream()));
                         System.err.println("****swf转换成功，文件输出： "+ swfFile.getPath() + "****");
                        if (pdfFile.exists()) {
                            pdfFile.delete();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            } else {
                System.out.println("****pdf不存在,无法转换****");
            }
        } else {
            System.out.println("****swf已经存在不需要转换****");
        }
    }
 
    static String loadStream(InputStream in) throws IOException {
        int ptr = 0;
        in = new BufferedInputStream(in);
        StringBuffer buffer = new StringBuffer();
        while ((ptr = in.read()) != -1) {
            buffer.append((char) ptr);
        }
        return buffer.toString();
    }
 
    /**
     * * 转换主方法
     */
    @SuppressWarnings("unused")
    public boolean conver() {
        if (swfFile.exists()) {
            System.out.println("****swf转换器开始工作，该文件已经转换为 swf****");
            return true;
        }
        if (environment == 1) {
            System.out.println("****swf转换器开始工作，当前设置运行环境 windows****");
        } else {
            System.out.println("****swf转换器开始工作，当前设置运行环境 linux****");
        }
        try {
            doc2pdf();
            pdf2swf();
        } catch (Exception e) {
              e.printStackTrace();
              return false;
        }
        System.out.println("文件存在吗？"+swfFile);
        if (swfFile.exists()) {
            System.out.println("存在");
            return true;
        } else {
            System.out.println("不存在");
            return false;
        }
    }
    /**
     * * 转换pdf主方法
     */
    @SuppressWarnings("unused")
    public boolean converpdf() {
        if (pdfFile.exists()) {
            System.out.println("****swf转换器开始工作，该文件已经转换为 swf****");
            return true;
        }
        if (environment == 1) {
            System.out.println("****swf转换器开始工作，当前设置运行环境 windows****");
        } else {
            System.out.println("****swf转换器开始工作，当前设置运行环境 linux****");
        }
        try {
            doc2pdf();
        } catch (Exception e) {
              e.printStackTrace();
              return false;
        }
        System.out.println("文件存在吗？"+pdfFile);
        if (pdfFile.exists()) {
            System.out.println("存在");
            return true;
        } else {
            System.out.println("不存在");
            return false;
        }
    }
    
    /**
     *返回文件路径      
     * @param     
     * @throws Exception 
     */
    public String getpdfPath() throws Exception{
    	if (docFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
                try {
                    connection.connect();
                    DocumentConverter converter = new OpenOfficeDocumentConverter(
                            connection);
                    converter.convert(docFile, pdfFile);
                    // close the connection
                    connection.disconnect();
                    return pdfFile.getPath();
                } catch (java.net.ConnectException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，openoffice 服务未启动！****");
                    throw e;
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    e.printStackTrace();
                    System.out.println("****swf转换器异常，读取转换文件 失败****");
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else {
                System.out.println("****已经转换为pdf，不需要再进行转化 ****");
                return fileString.toLowerCase().endsWith("pdf")?fileString:pdfFile.getPath();
            }
        } else {
            System.out.println("****swf转换器异常，需要转换的文档不存在， 无法转换****");
            return "文件不存在";
        }
    }
 
    /**
     *返回文件路径      
     * @param     
     */
    public String getswfPath(){
        if (this.swfFile.exists()){
            String tempString = swfFile.getPath();
            tempString = tempString.replaceAll("\\\\", "/");
            System.out.println("最后文件路径为"+tempString);
            return tempString;
        } else {
            return "文件不存在";
        }
    }
 
    /**
     * 设置输出路径
     *
     * @param outputPath
     */
    public void setOutputPath(String outputPath){
        this.outputPath = outputPath;
        if (!outputPath.equals("")) {
            String realName = fileName.substring(fileName.lastIndexOf("/"),
                    fileName.lastIndexOf("."));
            if (outputPath.charAt(outputPath.length()) == '/') {
                swfFile = new File(outputPath + realName + ".swf");
            } else {
                swfFile = new File(outputPath + realName + ".swf");
            }
        }
    }
    
  //注意destPath最后必须带文件分隔符  
    //这个用来转换的swfTool工具，一但待转换的文件有不可识别的字符在里面时，它就会堵塞在那里。这个情况要处理掉  
    public String  PDF2SWF(String sourcePath, String destPath) throws Exception {  
          
       /* // 目标路径不存在则建立目标路径  
        File dest = new File(destPath);  
        if (!dest.exists()) {  
            dest.mkdirs();  
        }  */
  
        // 源文件不存在则返回  
        File source = new File(sourcePath);  
          
        if (!source.exists()) {  
            System.out.println("pdf转换swf失败，源文件不存在!");  
            throw new Exception();  
        }  
  
        //因为下面进行系统调用，这样就会把系统执行的操作新开启一个线程（在此linux也叫进程），所以它和主扫描程序是独立运行，所以下次还会扫描这个转换中的文件，所以这里要将它设置为不可读，  
        source.setReadable(false);  
          
        
      
        String outputFile = destPath;
  
        System.out.println("开始调用swftools转换pdf文件:" + outputFile);  
          
          
        List<String>  command = new   ArrayList<String>();  
        command.add(Config.getProp("pdfswfRoot"));//从配置文件里读取  
        command.add("-z");  
//      command.add("-B");  
//      command.add("rfxview.swf");  
        command.add("-s");  
        command.add("flashversion=9");  
          
        command.add("-s");  
        command.add("poly2bitmap");//加入poly2bitmap的目的是为了防止出现大文件或图形过多的文件转换时的出错，没有生成swf文件的异常  
          
          
        //windows平台下  
//      command.add("languagedir=C:/xpdf/chinese-simplified/");  
        command.add(sourcePath);  
        command.add("-o");  
        command.add(outputFile);  
          
        ProcessBuilder processBuilder = new ProcessBuilder();  
        processBuilder.command(command);  
        Process process = processBuilder.start();  
          
//      dealWith(process);//改用下面的方式来处理：  
        InputStreamWathThread inputWathThread = new InputStreamWathThread(process);  
        inputWathThread.start();  
        ErrorInputStreamWathThread errorInputWathThread = new ErrorInputStreamWathThread(process);  
        errorInputWathThread.start();  
          
        try {  
            process.waitFor();//等待子进程的结束，子进程就是系统调用文件转换这个新进程  
            inputWathThread.setOver(true);//转换完，停止流的处理  
            errorInputWathThread.setOver(true);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
          
        System.out.println("转换完成");  
  
        return outputFile;  
    }  
}