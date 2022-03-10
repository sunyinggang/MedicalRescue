package com.jointthinker.module.fileupload.server;



import java.io.File;
import java.util.List;



import org.springframework.stereotype.Service;

import com.jointthinker.framework.business.ManagerBase;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.module.fileupload.pojo.UploadFileInfo;
@Service
public class UploadFileServer  extends ManagerBase {
	public List<UploadFileInfo> getFiles(Long categoryid,String category) throws PersistentException{
		String query = "select * from Upload_File_Info where category='"+category+"' and categoryid="+categoryid +" order by uploadtime desc ";
		List<UploadFileInfo> list = (List<UploadFileInfo>)this.getContextStrategy().jdbcQuery(query , UploadFileInfo.class);
		return list;
	}
	
	public void deleteFileInfoById(Long id,String savePath) throws PersistentException{
		UploadFileInfo fileInfo = (UploadFileInfo)this.getContextStrategy().load(id, UploadFileInfo.class);
		if(fileInfo!=null){
			String savefilepath = savePath + fileInfo.getFilepath();
			File file = new File(savefilepath);
			if(file.exists()){
				file.delete();
			}
			this.getContextStrategy().remove(fileInfo);
		}
	}
	
	public void handleEmpPhoto(Long empId,String savePath, String filePath) throws PersistentException{
		List<UploadFileInfo> list = (List<UploadFileInfo>)getFiles(empId, "emp_photo");
		if(list!=null&&list.size()>0){
			UploadFileInfo fileInfo = list.get(0);
			deleteFileInfoById(fileInfo.getId(),savePath);
		}
		String sql = "update core_employee set photopath='"+filePath+"' where id="+empId;
		this.getContextStrategy().jdbcupdate(sql);
	}
	public void handleCmsSlt(Long contentId,String savePath, String filePath) throws PersistentException{
		List<UploadFileInfo> list = (List<UploadFileInfo>)getFiles(contentId, "cms_slt");
		if(list!=null&&list.size()>0){
			UploadFileInfo fileInfo = list.get(0);
			deleteFileInfoById(fileInfo.getId(),savePath);
		}
		String sql = "update cms_pagecontent set img='"+filePath+"' where id="+contentId;
		this.getContextStrategy().jdbcupdate(sql);
	}
	
	public void delFileNotId(String category,Long categoryid,String id) throws PersistentException{
		String sql = "delete from upload_file_info where category= '"+category+"' and categoryid="+categoryid+" and id not in ("+id+")";
		this.getContextStrategy().jdbcupdate(sql);
	}
	
	
}
