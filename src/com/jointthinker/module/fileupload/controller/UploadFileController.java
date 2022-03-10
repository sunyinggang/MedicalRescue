package com.jointthinker.module.fileupload.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jointthinker.framework.business.Config;
import com.jointthinker.framework.common.util.DocConverter;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.controller.BaseController;
import com.jointthinker.module.fileupload.pojo.UploadFileInfo;
import com.jointthinker.module.fileupload.server.UploadFileServer;
@RequestMapping("/upload")
@Controller
public class UploadFileController extends BaseController{
	@Autowired
	private UploadFileServer uploadService;
	
	static Logger logger = Logger.getLogger(UploadFileController.class.getName());
	
	@RequestMapping(value="/upfile")
	public void upfile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONArray jsons = new JSONArray();
		String type=request.getParameter("type");
		String fileType=request.getParameter("fileType");
		String savePath = Config.getProp("upfileRoot");
		if(fileType!=null&&"templatefile".equals(fileType)){
			savePath = Config.getProp("templatefileRoot");
		}
		String category = null;
		Long categoryid = null;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024*200);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateDir = sdf.format(new Date());
		
		SimpleDateFormat sdf2=new SimpleDateFormat("yyyyMMddhhmmssSSS");
		
		//String savePath = request.getSession().getServletContext().getRealPath("/uploadfile");
		File file = new File(savePath+File.separator+dateDir);
		if(!file.exists()){
			file.mkdirs();
		}
		
		if(ServletFileUpload.isMultipartContent(request)){    //如果是上传文件表单
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				if(item.isFormField()){
					if(item.getFieldName().equals("category")){
						category = item.getString();
					}
					if(item.getFieldName().equals("categoryid")){
						categoryid = Long.parseLong(item.getString());
					}
				}
			}
			  int index = 0;
			for(FileItem item : list){
				index++;
				if(!item.isFormField()){
					String srcfilename = item.getName()!=null?item.getName().substring(item.getName().lastIndexOf("\\")+1).replace(" ", "_"):"";
					//String suffix = filename.substring(filename.lastIndexOf(".")+1);
					String newfilename = sdf2.format(new Date())+index + srcfilename.substring(srcfilename.lastIndexOf("."));
					//if(!allow.contains(suffix)&&!suffix.equals("")){
					//	fails += filename+",";
					//	continue;
					//}
					srcfilename = srcfilename.toLowerCase();
					newfilename = newfilename.toLowerCase();
					InputStream in = item.getInputStream();
					String filepath = File.separator+dateDir + File.separator + newfilename;
					
					String savefilepath = savePath + filepath;
						
					FileOutputStream out = new FileOutputStream(savefilepath);
					byte buffer[] = new byte[1024*50];
					int len = 0;
					while((len=in.read(buffer))>0){
						out.write(buffer, 0, len);
					}
					if(in!=null){in.close();in=null;}
					if(out!=null){out.close();out=null;}
					if(item!=null){item.delete();}
					
					if("emp_photo".equals(category)){
						uploadService.handleEmpPhoto(categoryid,savePath,filepath);
					}else if("cms_slt".equals(category)){
						uploadService.handleCmsSlt(categoryid, savePath, filepath);
					}
					
					UploadFileInfo fileInfo = new UploadFileInfo();
					//fileInfo.setUserid(uso.getCurrentUser().getId());
					//fileInfo.setUsername(uso.getCurrentUser().getName());
					fileInfo.setCategory(category);
					fileInfo.setCategoryid(categoryid);
					fileInfo.setUploadtime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
					fileInfo.setSrcfilename(srcfilename);
					fileInfo.setNewfilename(newfilename);
					fileInfo.setFilepath(filepath);
					uploadService.storePO(fileInfo);
					jsons.add(fileInfo);
				}
			}
		}
		sendResponse(response, "{\"success\":true,\"files\":"+jsons.toString()+"}");
	}
	
	@RequestMapping(value="/deletefile",method = RequestMethod.POST)
	public void deletefile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long id = Long.parseLong(request.getParameter("id"));
		String fileType=request.getParameter("fileType");
		String savePath = Config.getProp("upfileRoot");
		if(fileType!=null&&"templatefile".equals(fileType)){
			savePath = Config.getProp("templatefileRoot");
		}
		uploadService.deleteFileInfoById(id,savePath);
		sendResponse(response, "{\"success\":true,\"msg\":\"删除成功！\"}");
	}
	
	@RequestMapping(value="/downloadfile")
	public void downloadfile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Long id = Long.parseLong(request.getParameter("id"));
		UploadFileInfo fileInfo = (UploadFileInfo) uploadService.getByPk(id, UploadFileInfo.class);
		
		String fileType=request.getParameter("fileType");
		String savePath = Config.getProp("upfileRoot");
		if(fileType!=null&&"templatefile".equals(fileType)){
			savePath = Config.getProp("templatefileRoot");
		}
		String savefilepath = savePath + fileInfo.getFilepath();
		File file = new File(savefilepath);
		
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] buf = new byte[1024*10];
        int len = 0;
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileInfo.getSrcfilename().getBytes("UTF-8"), "ISO8859-1"));
        OutputStream out = response.getOutputStream();
		
        while ((len = br.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        out.flush();
        if(br!=null)br.close();
        if(out!=null)out.close();
	}
	
	@RequestMapping(value="/showPhoto")
	public void showPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileid = request.getParameter("fileid");
		String root = Config.getProp("upfileRoot");
		String query = "select * from Upload_File_Info where id="+fileid+" order by uploadtime desc ";
		List<UploadFileInfo> list = (List<UploadFileInfo>)uploadService.getContextStrategy().jdbcQuery(query , UploadFileInfo.class);
		if(list==null||list.size()==0){
			return;
		}
		UploadFileInfo doc = list.get(0);
		String filePath = doc.getFilepath();
		filePath = root + filePath.replace( "/" , File.separator);
		String fileName = doc.getSrcfilename();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("image/jpeg");
        FileInputStream fis = new FileInputStream(filePath);
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1)
                os.write(buffer, 0, count);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null)
                os.close();
            if (fis != null)
                fis.close();
        }
	}
	
	
	
	@RequestMapping(value="/showFile")
	public void showFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("fileid");
		String root = Config.getProp("upfileRoot");
		String query = "select * from Upload_File_Info where  id="+id +" order by uploadtime desc ";
		List<UploadFileInfo> list = (List<UploadFileInfo>)uploadService.getContextStrategy().jdbcQuery(query , UploadFileInfo.class);
		if(list==null||list.size()==0){
			return;
		}
		UploadFileInfo doc = list.get(0);
		if(doc.getSrcfilename().endsWith(".jpg")||doc.getSrcfilename().endsWith(".png")
				||doc.getSrcfilename().endsWith(".gif")||doc.getSrcfilename().endsWith(".bmp")
				||doc.getSrcfilename().endsWith(".jpeg")){
			
		}
		String filePath = doc.getFilepath();
		filePath = root + filePath.replace( "/" , File.separator);
		String fileName = doc.getNewfilename();
        String converfilename = filePath.replaceAll("\\\\", "/");
		DocConverter d = new DocConverter(converfilename);
         //调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;
     	 d.conver();
     	 //调用getswfPath()方法，打印转换后的swf文件路径
         String swfpath = d.getswfPath();
		 //swfpath = swfpath.replace("D:/docs/upfiles", "");
         swfpath = "/"+swfpath.replace(Config.getProp("upfileRoot").replaceAll("\\\\", "/"), "");
		 sendResponse(response, "{\"success\":true,\"swfpath\":\""+swfpath+"\"}");
	}
	@RequestMapping(value="/getFile")
	public void getFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("id");
		String query = "select * from Upload_File_Info where  id="+id +" order by uploadtime desc ";
		List<UploadFileInfo> list = (List<UploadFileInfo>)uploadService.getContextStrategy().jdbcQuery(query , UploadFileInfo.class);
		if(list==null||list.size()==0){
			return;
		}
		UploadFileInfo doc = list.get(0);
		sendResponse(response, "{\"success\":true,\"file\":"+JSONutil.bean2json(doc)+"}");
	}

	@RequestMapping(value="/showServerFile")
	public void showServerFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String id = request.getParameter("fileid");
		String root = request.getSession().getServletContext().getRealPath("/");
		String query = "select * from Upload_File_Info where  id="+id +" order by uploadtime desc ";
		List<UploadFileInfo> list = (List<UploadFileInfo>)uploadService.getContextStrategy().jdbcQuery(query , UploadFileInfo.class);
		if(list==null||list.size()==0){
			return;
		}
		UploadFileInfo doc = list.get(0);
		if(doc.getSrcfilename().endsWith(".jpg")||doc.getSrcfilename().endsWith(".png")
				||doc.getSrcfilename().endsWith(".gif")||doc.getSrcfilename().endsWith(".bmp")
				||doc.getSrcfilename().endsWith(".jpeg")){
			
		}
		String filePath = doc.getFilepath();
		filePath = root + filePath.replace( "/" , File.separator);
        String converfilename = filePath.replaceAll("\\\\", "/");
		DocConverter d = new DocConverter(converfilename);
         //调用conver方法开始转换，先执行doc2pdf()将office文件转换为pdf;再执行pdf2swf()将pdf转换为swf;
     	 d.conver();
     	 //调用getswfPath()方法，打印转换后的swf文件路径

     	 String swfpath = d.getswfPath();
		 //swfpath = swfpath.replace("D:/docs/upfiles", "");
         swfpath = "/"+swfpath.replace(request.getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/"), "");
		 sendResponse(response, "{\"success\":true,\"swfpath\":\""+swfpath+"\"}");
	}
	
	@RequestMapping(value="/downloadServerfile")
	public void downloadServerfile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String name = request.getParameter("name");
		String root = request.getSession().getServletContext().getRealPath("/");
		String savefilepath = root +"\\documents\\"+ name;
		File file = new File(savefilepath);
		
		BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
        byte[] buf = new byte[1024*10];
        int len = 0;
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename="+ new String(name.getBytes("UTF-8"), "ISO8859-1"));
        OutputStream out = response.getOutputStream();
		
        while ((len = br.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        out.flush();
        if(br!=null)br.close();
        if(out!=null)out.close();
	}
}
