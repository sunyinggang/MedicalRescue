package com.jointthinker.module.accident.controller;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jointthinker.framework.bean.JsonBean;
import com.jointthinker.framework.business.Config;
import com.jointthinker.framework.common.exceptions.PersistentException;
import com.jointthinker.framework.common.util.JSONutil;
import com.jointthinker.framework.common.util.StringUtil;
import com.jointthinker.framework.common.util.UUIDGenerator;
import com.jointthinker.framework.controller.BaseController;
import com.jointthinker.module.accident.pojo.Accident;
import com.jointthinker.module.accident.service.AccidentService;
import com.jointthinker.module.medical.pojo.Medical;
@RequestMapping("/accident")
@Controller
public class AccidentController extends BaseController{

	@Autowired
	private AccidentService accidentService;
	
	@RequestMapping("/WaitAccidentList")
	public void WaitAccidentList(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		Integer limit = StringUtil.getMapkeyValue2Int(map, "limit");
		Integer start = StringUtil.getMapkeyValue2Int(map, "start");
		JSONObject pathObj = accidentService.getCorePath();
		String[] keyArr = null;
		String[] mustKeyArr = null;
		String url = "https://news.baidu.com/";
		if(pathObj!=null){
			if(!"".equals(pathObj.get("keyword").toString())){
				keyArr=pathObj.get("keyword").toString().trim().split("、");
			}
			if(!"".equals(pathObj.get("must_keyword").toString())){
				mustKeyArr=pathObj.get("must_keyword").toString().trim().split("、");
			}
			if(!"".equals(pathObj.get("urlpath").toString())){
				url=pathObj.get("urlpath").toString();
			}
			
		}
		JSONArray arr = new JSONArray();
		//事故动态
		try {
			
			Document doc = Jsoup.connect(url).timeout(20*1000).get();
			Elements links = doc.getElementsByTag("a");
			for(Element link : links) {
				String href = link.attr("abs:href");
				String title = link.childNodeSize()>0?link.childNode(0).toString():"";
				if(arr.size()<100){
					boolean bool = true;
					if(mustKeyArr!=null&&mustKeyArr.length>0){
						for(int j=0;j<mustKeyArr.length;j++){
							if(title.indexOf(mustKeyArr[j])==-1){
								bool=false;
								break;
							}
						}
						
					}
					if(bool&&keyArr!=null){
						for(int i=0;i<keyArr.length;i++){
							if(title.indexOf(keyArr[i])!=-1){
								JSONObject obj = new JSONObject();
								obj.put("title", title);
								obj.put("url", href);
								arr.add(obj);
								break;
							}
						}
					}
					
					
				}
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONArray result = new JSONArray();
		for(int i = start;i<((limit+start)>arr.size()?arr.size():(limit+start));i++){  
			result.add(arr.getJSONObject(i));
        }  

		sendResponse(response, "{\"total\":"+arr.size()+",\"rows\":"+result.toString()+"}");
	}
	
	
	@RequestMapping("/getAccidentInfoByURl")
	public void getAccidentInfoByURl(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException, IOException{
		JSONObject json = new JSONObject();
		String url = request.getParameter("url");
		Document doc = Jsoup.connect(url).timeout(20*1000).get();
		Elements titles = doc.select("title");
		for (Element title:titles){
			json.put("title", title.childNodeSize()>0?title.childNode(0).toString():"");
        }
		Elements contents = doc.select("meta");
		for (Element content:contents){
			if("description".equals(content.attr("name"))||"Description".equals(content.attr("name"))){
				json.put("content",content.attr("content"));
			}
			
        }
		sendResponse(response, "{\"success\":true,\"json\":"+json+"}");
	}
	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response, @RequestParam("faceimg") CommonsMultipartFile file) throws PersistentException, IntrospectionException, IOException{
		Map map=new HashMap();
		map = request.getParameterMap();
		Accident adit = (Accident) JSONutil.mapToBean(map, Accident.class);
		if(adit.getId()!=null&&"".equals(adit.getId())){
			adit.setCreatetime(new Date());
		}
		if(file!=null&&file.getSize()>0){
			String photopath = Config.getProp("upfileRoot")+File.separator+"photo";
			File f = new File(photopath);
			if(!f.exists()){
				f.mkdirs();
			}
			String filename = file.getOriginalFilename();
			filename = UUIDGenerator.getUUID()+filename.substring(filename.lastIndexOf("."));
			FileOutputStream out = new FileOutputStream(new File(photopath+File.separator+filename));
			InputStream in = file.getInputStream();
			byte buffer[] = new byte[1024*200];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			out.flush();
			if(in!=null){in.close();in=null;}
			if(out!=null){out.close();out=null;}
			adit.setPhotopath(filename);
		}
		accidentService.storePO(adit);
		sendResponse(response, "{\"success\":true}");
	}
	
	@RequestMapping("/getAccidentMap")
	public void getAccidentMap(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		JsonBean json = accidentService.getAccidentMap(id);
		sendResponse(response, "{\"success\":true,\"json\":"+json.getJsonarray()+"}");
	}
	@RequestMapping("/getAccidentList")
	public void getAccidentList(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		Map map=new HashMap();
		map = request.getParameterMap();
		JsonBean json = accidentService.getAccidentList(map);
		sendResponse(response, "{\"total\":"+json.getSize()+",\"rows\":"+json.getJsonarray().toString()+"}");
	}
	@RequestMapping("/getAccidentInfo")
	public void getAccidentInfo(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		if(!"".equals(id)){
			Accident accident = (Accident) accidentService.getByPk(Long.parseLong(id), Accident.class);
			sendResponse(response, "{\"success\":true,\"json\":"+JSONutil.bean2json(accident)+"}");
		}
	}
	
	@RequestMapping("/delAccidentInfo")
	public void delAccidentInfo(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IntrospectionException{
		String id = request.getParameter("id");
		accidentService.removePO(Long.parseLong(id), Accident.class);
		sendResponse(response, "{\"success\":true}");
	}
	
	@RequestMapping("/getPhoto")
	public void getPhoto(HttpServletRequest request, HttpServletResponse response) throws PersistentException, IOException{
		String path =request.getParameter("path");
		String photopath = Config.getProp("upfileRoot")+File.separator+"photo"+File.separator+path;
		File f = new File(photopath);
		if(!f.exists()){
			return;
		}
		FileInputStream in = new FileInputStream(f);
		OutputStream out = response.getOutputStream();
		byte buffer[] = new byte[1024*200];
		int len = 0;
		while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
		}
		out.flush();
		if(in!=null){in.close();in=null;}
		if(out!=null){out.close();out=null;}
	}
	
	
}
