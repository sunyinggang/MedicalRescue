package com.jointthinker.framework.common.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FileUtils {
	@SuppressWarnings("finally")
	public static boolean createFile(String fileName,Map attribute){
		boolean result = true;
		Path path = Paths.get(fileName);
		if (path.getParent()!=null){
			createDirectory(path.getParent().toString());
		}
		try {
			Files.deleteIfExists(path);
			Files.createFile(path);
			if (attribute!=null&&attribute.size()>0){
				UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);  
				Iterator entries = attribute.entrySet().iterator();
				while(entries.hasNext()){
					Map.Entry entry = (Map.Entry) entries.next();
			        userDefinedFileAttributeView.write((String)entry.getKey(), Charset.defaultCharset().encode((String)entry.getValue()));
				}
			}
			result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean createFile(String fileName){
		boolean result = false;
		Path path = Paths.get(fileName);
		if (path.getParent()!=null){
			createDirectory(path.getParent().toString());
		}
		try {
			if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)){
				Files.createFile(path);
			}
			result = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			return result;
		}
	}
	
	public static boolean modifyUserDefinedFileAttribute(String fileName,Map attribute){
		boolean result = false;
		Path path = Paths.get(fileName);
		if (Files.exists(path)){
			if (attribute!=null&&attribute.size()>0){
				UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);  
				Iterator entries = attribute.entrySet().iterator();
				while(entries.hasNext()){
					Map.Entry entry = (Map.Entry) entries.next();
			        try {
			        	userDefinedFileAttributeView.write((String)entry.getKey(), Charset.defaultCharset().encode((String)entry.getValue()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally{
						
					}
				}
			}
			result = true;
		}
		return result;
	}
	
	public static Map getUserDefinedFileAttribute(Path path){
		Map result = new HashMap();
		if (Files.exists(path)){
			UserDefinedFileAttributeView userDefinedFileAttributeView = Files.getFileAttributeView(path, UserDefinedFileAttributeView.class);
			List<String> attrNames;
			try {
				attrNames = userDefinedFileAttributeView.list();
				// 读出所有属性  
		        for (String name: attrNames) {  
		            ByteBuffer bb = ByteBuffer.allocate(userDefinedFileAttributeView.size(name)); // 准备一块儿内存块读取  
		            userDefinedFileAttributeView.read(name, bb);  
		            bb.flip();  
		            String value = Charset.defaultCharset().decode(bb).toString();  
//		            System.out.println(name + " : " + value);
		            result.put(name, value);
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		}
		return result;
	}
	
	public static Map getUserDefinedFileAttribute(String fileName){
		Path path = Paths.get(fileName);
		return getUserDefinedFileAttribute(path);
	}
	
	public static boolean createDirectory(String directory){
		boolean result = true;
		String dir = directory.replace("/", File.separator);
		Path path = Paths.get(dir);
		if (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
			try {
				Files.createDirectories(Paths.get(dir));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = false;
			} finally{
				
			}
		}
		return result;
	}

	@SuppressWarnings("finally")
	public static boolean moveFile(String source,String target){
		boolean result = true;
		Path pathFrom = Paths.get(source);
		Path pathTo = Paths.get(target);
		
		try {
			Files.move(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} finally{
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean moveFile(Path pathFrom,String target){
		boolean result = true;
		Path pathTo = Paths.get(target);
		
		try {
			Files.move(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} finally{
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean copyFile(String source,String target){
		boolean result = true;
		Path pathFrom = Paths.get(source);
		Path pathTo = Paths.get(target);
		createDirectory(pathTo.getParent().toString());
		try {
			Files.copy(pathFrom, pathTo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} finally{
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	public static boolean deleteFile(String source){
		boolean result = true;
		Path path = Paths.get(source);
		return deleteFile(path);
	}
	
	@SuppressWarnings("finally")
	public static boolean deleteFile(Path path){
		boolean result = true;
		try {
			if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
				org.apache.commons.io.FileUtils.deleteQuietly(path.toFile());
			}else{
				result = Files.deleteIfExists(path);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = false;
		} finally{
			return result;
		}
	}
	
	public static boolean deleteFiles(List<String> source){
		boolean result = true;
		for (int i=0;i<source.size();i++){
			result = result&&deleteFile(source.get(i));
		}
		return result;
	}
	
	public static boolean deleteFiles(Collection<String> source){
		boolean result = true;
		for (String fileName : source) {
			result = result&&deleteFile(fileName);
		}
		return result;
	}
	
	public static String readFile2String(String filename){
		String fileContent = "[]";
		Path path = Paths.get(filename);
		if (Files.exists(path)){
			try {
				fileContent = new String(Files.readAllBytes(path),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
		}else{
			try {
				createDirectory(path.getParent().toString());
				Files.createFile(path);
				Files.write(path, fileContent.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
		}
		return fileContent;
	}
	
	public static void writeFile4String(String filename,String fileContent){
		Path path = Paths.get(filename);
		if (Files.exists(path)){
			try {
				Files.write(path, fileContent.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
		}else{
			try {
				createDirectory(path.getParent().toString());
				Files.createFile(path);
				Files.write(path, fileContent.getBytes("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
		}
		return ;
	}
	
	public static void writeFile4Byte(String filename,byte[] fileContent){
		Path path = Paths.get(filename);
		if (Files.exists(path)){
			try {
				Files.write(path, fileContent);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
		}else{
			try {
				createDirectory(path.getParent().toString());
				Files.createFile(path);
				Files.write(path, fileContent);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}
		}
		return ;
	}
	
	/*
	 * 获取目录下文件总数，仅当前目录不处理子目录
	 */
	public static int getDirectoryFileCount(String directory){
		int fileCount = 0;
		File file = new File(directory);
		if (file.isDirectory()){
			String[] files = file.list(new FilenameFilter() {
	            public boolean accept(File dir, String name) {
	                return dir.isFile();
	            }
	        });
			fileCount = files.length;
		}
		return fileCount;
	}
	
	/*
	 * 获取MCN需要处理的目录下所有文件数量，用户自定义属性为mcnworkstatus=1，仅当前目录不处理子目录
	 */
	public static Collection<String> getDirectoryFileCountByAttribute(Path path){
		List<String> fileList = new ArrayList<String>();
		if (Files.exists(path)&&Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
			DirectoryStream.Filter<Path> dir_filter = new DirectoryStream.Filter<Path>() {
				public boolean accept(Path path) throws IOException {
			       return (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));
				}
			}; 
			try {
				DirectoryStream<Path> ds = Files.newDirectoryStream(path, dir_filter);
				for (Path file : ds) {
					Map fileAttribute = getUserDefinedFileAttribute(file);
					if (fileAttribute!=null&&fileAttribute.containsKey("mcnworkstatus")&&"1".equals((String)fileAttribute.get("mcnworkstatus"))){
						fileList.add(file.toString());
					}
				}
			} catch (IOException e) {
			     System.err.println(e);
			} finally {
				
			}
		}
		
		return fileList;
	}
	
	public static Collection<String> getDirectoryFileCountByAttribute(String directory){
		Path path = Paths.get(directory);
			
		return getDirectoryFileCountByAttribute(path);
	}
	
	/*
	 * 根据文件自定义属性获取目录下文件总数，仅当前目录不处理子目录
	 */
	public static int getDirectoryFileCountByAttribute(String directory,Map attribute){
		if (attribute.size()==0)	return getDirectoryFileCount(directory);
		int fileCount = 0;
		Path path = Paths.get(directory);
		if (Files.exists(path)&&Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)){
			DirectoryStream.Filter<Path> dir_filter = new DirectoryStream.Filter<Path>() {
				public boolean accept(Path path) throws IOException {
			       return (!Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));
				}
			}; 
			try {
				DirectoryStream<Path> ds = Files.newDirectoryStream(path, dir_filter);
				for (Path file : ds) {
					Map fileAttribute = getUserDefinedFileAttribute(file);
					if (fileAttribute.size()>0){
						Iterator entries = attribute.entrySet().iterator();
						while(entries.hasNext()){
							Map.Entry entry = (Map.Entry) entries.next();
							if (entry.getValue().equals(fileAttribute.get(entry.getKey()))){
								fileCount++;
								break;
							}
						}
					}
				}
			} catch (IOException e) {
			     System.err.println(e);
			} finally {
				
			}
		}
		
		return fileCount;
	}
	
	 /** 
     * <b>function:</b>传递一个File，返回该文件的FileInfo实体类 
     * @author hoojo 
     * @createDate Oct 10, 2010 10:10:19 PM 
     * @param file File 
     * @return FileInfo 
     */  
    public static FileInfo getFileInfo(File file) {  
        FileInfo info = new FileInfo();
        if (file != null) {  
            info.setId(UUID.randomUUID().toString()); 
            if (file.getName() == null || "".equals(file.getName()) || "::".equals(file.getName())) {
                info.setName(file.getAbsolutePath());  
                info.setText(file.getAbsolutePath());  
            } else {  
                info.setName(file.getName());  
                info.setText(file.getName());  
            }  
            //info.setLeaf(file.isFile());  
            info.setLeaf(!file.isDirectory());  
            info.setLength(file.length());  
            info.setPath(getDoPath(file.getAbsolutePath()));  
            info.setSuffix(getType(file.getName()));  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date date = new Date();  
            date.setTime(file.lastModified());  
            info.setEditDate(sdf.format(date));
        }  
        return info;  
    }  
      
    public static void setFileInfo(File file, FileInfo info) {  
        if (file != null && info != null) {  
            info.setId(UUID.randomUUID().toString());  
            if (file.getName() == null || "".equals(file.getName()) || "::".equals(file.getName())) {  
                info.setName(file.getAbsolutePath());  
                info.setText(file.getAbsolutePath());  
            } else {  
                info.setName(file.getName());  
                info.setText(file.getName());  
            }  
            //info.setLeaf(file.isFile());  
            info.setLeaf(!file.isDirectory());  
            info.setLength(file.length());  
            info.setPath(getDoPath(file.getAbsolutePath()));  
            info.setSuffix(getType(file.getName()));  
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date date = new Date();  
            date.setTime(file.lastModified());  
            info.setEditDate(sdf.format(date));  
        }  
    }  
      
    /** 
     * <b>function:</b>处理后的系统文件路径 
     * @author hoojo 
     * @createDate Oct 10, 2010 12:49:31 AM 
     * @param path 文件路径 
     * @return 返回处理后的路径 
     */  
    public static String getDoPath(String path) {  
        path = path.replace("//", "/");  
        String lastChar = path.substring(path.length() - 1);  
        if (!"/".equals(lastChar)) {  
            path += "/";  
        }  
        return path;  
    }  
      
    /** 
     * <b>function:</b>和文件后缀一样，不同的是没有“.” 
     * @author hoojo 
     * @createDate Oct 10, 2010 2:42:43 PM 
     * @param fileName 文件名称 
     * @return 
     */  
    public static String getType(String fileName) {  
     int index = fileName.lastIndexOf(".");  
        if (index != -1) {  
            String suffix = fileName.substring(index + 1);//后缀  
            return suffix;   
        } else {  
            return null;  
        }  
    }  
      
    /** 
     * <b>function:</b> 得到指定目录下所有的文件集合 
     * @createDate 2010-10-20 下午02:20:06 
     * @author hoojo 
     * @param info 将数据设置在该变量中 
     * @param file 文件目录 
     */  
    public static void getAllFileInfo(FileInfo info, File file) {  
        if (file.isDirectory()) {  
            long size = 0;  
            File[] allFiles = file.listFiles();  
            for (File f : allFiles) {  
                size += f.length();  
                FileInfo fi = getFileInfo(f);  
                info.getChildren().add(fi);  
                getAllFileInfo(fi, f);  
            }  
            info.setLength(size);  
        }  
    }  
      
    /** 
     * <b>function:</b> 得到当前目录所有文件 
     * @createDate 2010-10-20 下午02:21:06 
     * @author hoojo 
     * @param info 文件对象 
     * @param file 目录 
     */  
    public static void getFileInfo(FileInfo info, File file, String[] allowTypes) {  
        if (file.isDirectory()) {  
            long size = 0;  
            File[] allFiles = file.listFiles();  
            for (File f : allFiles) {  
                size += f.length();  
                FileInfo fi = getFileInfo(f);  
                if (f.isDirectory()) {  
                    info.getChildren().add(fi);  
                } else {  
                    if (validTypeByName(f.getName(), allowTypes, true)) {  
                        info.getChildren().add(fi);  
                    }  
                }  
            }  
            info.setLength(size);  
        }  
    }  
      
    /** 
     * <b>function:</b> 根据文件名和类型数组验证文件类型是否合法，flag是否忽略大小写 
     * @author hoojo 
     * @createDate Oct 10, 2010 11:54:54 AM 
     * @param fileName 文件名 
     * @param allowTypes 类型数组 
     * @param flag 是否获得大小写 
     * @return 是否验证通过 
     */  
    public static boolean validTypeByName(String fileName, String[] allowTypes, boolean flag) {  
        String suffix = getType(fileName);  
        boolean valid = false;  
        if (allowTypes.length > 0 && "*".equals(allowTypes[0])) {  
            valid = true;  
        } else {  
            for (String type : allowTypes) {  
                if (flag) {//不区分大小写后缀  
                    if (suffix != null && suffix.equalsIgnoreCase(type)) {  
                        valid = true;  
                        break;  
                    }  
                } else {//严格区分大小写  
                    if (suffix != null && suffix.equals(type)) {  
                        valid = true;  
                        break;  
                    }  
                }  
            }  
        }  
        return valid;  
    }  
      
    /** 
     * <b>function:</b> 在path目录下创建目录 
     * @createDate 2010-11-3 下午04:03:34 
     * @author hoojo 
     * @param path 
     * @param dirName 
     * @return 
     */  
    public static boolean mkDir(String path, String dirName) {  
        boolean success = false;  
        File file = new File(getDoPath(path) + dirName);  
        if (!file.exists()) {  
            success = file.mkdirs();  
        }   
        return success;  
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		FileUtils.createDirectory("e:/file_exchange/ftpserver/receive_tmp/test");
//		Map result = FileUtils.getUserDefinedFileAttribute("E:/file_exchange/sender/test/test_sender_1504625032332");
		Path path = Paths.get("E:/file_exchange/sender/test/test_sender_1504625032332");
		System.out.println(path.toString());
		
	}

}
