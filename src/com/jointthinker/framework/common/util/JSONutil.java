package com.jointthinker.framework.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import com.jointthinker.framework.business.InitialManager;


public class JSONutil
{
	private static DateMorpher dm=new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd"});
	
	static {
		JSONUtils.getMorpherRegistry().registerMorpher(dm);
	}
	
	public static void main(String[] args){
//		String aa = "\r\n \r\n";
//		String jsonstr= "与上报人艾琨沟通，确认上报问题已解决.\\r\\n";
//		jsonstr = "上报人表示顾客的兑换码手机APP不能正常使用。 2.故障现象：顾客购买的大众点评的兑换券，在手机APP购买两次都提示出票失败。 3.需要提供的其他资料： 顾客购票的账号：13949008818，兑换码：8423010536133540398。" +
//				"截图见附件。 4.尝试解决操作：核心系统查询该兑换券为可用状态。官网使用此兑换券尝试购票提示“此兑换券被其他账户绑定”。\r\n发生原因:无。\r";
//		jsonstr = "";
//		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
//		jsonstr = jsonstr.replaceAll("\'","\\\\'");
//		//jsonstr = jsonstr.replace("\\", "\\\\");
//		jsonstr = jsonstr.replaceAll(",}$", "}");
//		jsonstr = jsonstr.replace("\\\r", "\\\\r");
//		jsonstr = jsonstr.replace("\\\n", "\\\\n");
//		//jsonstr = jsonstr.replace("\\r", "\\\\r");
//		//jsonstr = jsonstr.replace("\\n", "\\\\n");
//		System.out.println(jsonstr);
		String jsonstr1= "{\"checkuserid\":42451,\"checktype\":\"转办\",\"modulename\":\"承办\",\"hostaccepttime\":\"2014-07-28 15:14:27\",\"stopcasetime\":\"2014-09-01 11:30:54\",\"stopcasenote\":\"2014年7月29日，我局北七家所执法人员对被举报的北京市昌平区北七家镇宏富苑小区6号楼、15号楼底商的肉饼店铺、烧饼店铺进行了执法检查；\r\n检查发现被举报人无餐饮服务许可证，涉嫌委屈的餐饮服务许可证从事餐饮服务经营活动，已予以取缔；\r\n举报人不愿意提供联系方式和姓名，不需要反馈检查结果；\r\n\",\"hosthandledesc\":\"2014年7月29日，我局北七家所执法人员对被举报的北京市昌平区北七家镇宏富苑小区6号楼、15号楼底商的肉饼店铺、烧饼店铺进行了执法检查；\r\n检查发现被举报人无餐饮服务许可证，涉嫌委屈的餐饮服务许可证从事餐饮服务经营活动，已予以取缔；\r\n举报人不愿意提供联系方式和姓名，不需要反馈检查结果；\r\n\",\"hosthandletime\":\"2014-09-01 11:30:54\",\"presentuserids\":\"42451\",\"presentusernames\":\"裴雪\",\"presentuserroleids\":\"104509\",\"presentuserrolenames\":\"上级交办指导岗\",\"presentuserdeptnames\":\"综合科\",\"hostdeptid\":2725,\"transferflag\":\"false\",\"oldoptuserid\":295697,\"oldoptroleid\":104492,\"oldoptstateid\":2,\"isviewed\":\"Y\",\"oldsenduserids\":\"101260,106055,295697\",\"protype\":\"转办\",\"pronote\":\"  转办到【昌平区食品药品监管局】【石新贵,刘巍巍,郑全意】\",\"viewtype\":\"inter\",\"oldhandleuserid\":295697,\"oldhandlenote\":\"2014年7月29日，我局北七家所执法人员对被举报的北京市昌平区北七家镇宏富苑小区6号楼、15号楼底商的肉饼店铺、烧饼店铺进行了执法检查；\r\n检查发现被举报人无餐饮服务许可证，涉嫌委屈的餐饮服务许可证从事餐饮服务经营活动，已予以取缔；\r\n举报人不愿意提供联系方式和姓名，不需要反馈检查结果；\r\n\",\"hostflag\":1,\"olddistributeuserid\":42451,\"olddistributeroleid\":104509,\"sheetid\":1166599,\"personname\":\"匿名先生\",\"gender\":\"男\",\"companyaddress\":\"北七家镇宏福苑小区\",\"credentialsname\":33913,\"credentialsno\":\"无\",\"callnumber\":\"13716102307\",\"status\":262,\"workunit\":\"无\",\"contactnumber\":\"13716102307\",\"phone\":\"13716102307\",\"portraiture\":\"无\",\"calltime\":\"2014-07-26 08:34:10\",\"iscrowd\":\"是\",\"issecrecy\":\"是\",\"respondentcompany\":\"肉饼店铺；烧饼店铺\",\"respondentpeople\":\"无\",\"job\":\"无\",\"respondentworkunit\":\"无\",\"productno\":\"无\",\"specification\":\"无\",\"marketingenterprise\":\"肉饼店铺；烧饼店铺\",\"productname\":\"无\",\"manufacturefactory\":\"无\",\"licensenumber\":\"无\",\"productionaddress\":\"无\",\"salesaddress\":\"宏福苑小区6号楼、15号楼底商\",\"otherdistricts\":\"无\",\"province\":35961,\"beijingdistricts\":90,\"validterm\":\"无\",\"qitabeizhu\":\"无\",\"respondentworkunitaddress\":\"无\",\"acceptanceloginname\":\"zhongwei\",\"employeename\":\"仲玮\",\"informationsourcename\":\"本埠\",\"statename\":\"不予立案\",\"hoststatename\":\"不予立案\",\"importancename\":\"一般\",\"businesstypename\":\"投诉举报\",\"sourcebusinessname\":\"上级交办\",\"businessclassifyname\":\"食品\",\"twoassortmentname\":\"无《餐饮服务许可证》经营\",\"submitusername\":\"仲玮\",\"checkusername\":\"裴雪\",\"checkuserphone\":\"59319078\",\"replywayname\":\"电话\"}";
		System.out.println(convertJSONString(jsonstr1));
	}
	
	public static Object json2bean(String jsonstr,Class clz) 
	{
		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		JSONObject jsonObject = JSONObject.fromObject(jsonstr); 
		Object bean = JSONObject.toBean(jsonObject,clz);
		return bean;		
	}
	
	public static Object json2beanU(String jsonstr,Class clz,Object obj) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException
	{
		java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(clz, Object.class);
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        for(int k=0; k<pds.length; k++)
        {
        	String pname = pds[k].getName();//.toLowerCase();
        	if(!pname.equals("id")&&jsonstr.contains(pname+"\":"))
        	{
        		int psi = jsonstr.indexOf(pname+"\":");
        		int pei = jsonstr.indexOf("\"",psi+pname.length()+3);
        		String value = jsonstr.substring(psi+pname.length()+3,pei);
//        		System.out.println("pname----------"+pname);
//        		System.out.println("pvalue----------"+value);
//        		System.out.println("pds[k].getPropertyType()----------"+pds[k].getPropertyType());
        		pds[k].getWriteMethod().invoke(obj,new Object[] {strToBean(value,pds[k].getPropertyType())});
        	}
        }
        return obj;
	}
	
	public static Object strToBean(String str,Class returnType)
	{
		str = str.trim();
		if (returnType.equals(Integer.class) || returnType.equals(int.class)) 
		{
			if (str!=null&&!"".equals(str))
				return new Integer(Integer.parseInt(str));
			else
				return null;
        }
		if(returnType.equals(Long.class) || returnType.equals(long.class))
		{
			if (str!=null&&!"".equals(str))
				return new Long(Long.parseLong(str));
			else
				return null;
        }
		if(returnType.equals(Float.class) || returnType.equals(float.class))
		{
			if (str!=null&&!"".equals(str))
				return new Float(Float.parseFloat(str));
			else 
				return null;
        }
		if(returnType.equals(Double.class) || returnType.equals(double.class))
		{
			if (str!=null&&!"".equals(str))
				return new Double(Double.parseDouble(str));
			else 
				return null;
        }
		if (returnType.equals(Date.class)) 
		{
			if (str!=null&&!str.equals(""))
			{
				SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	            try 
	            {
	            	if(str.contains(" "))
	            		return df1.parse(str);
	            	else
	            		return df2.parse(str);
				} 
	            catch (ParseException e) 
	            {
					e.printStackTrace();
				}
			}
			else return null;
				
        }
		if(returnType.equals(String.class))
		{
            return str;
        }
		return str;
	}

	
	public static List json2list(String jsonstr,Class clz)
	{
		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		JSONArray jsonArray = JSONArray.fromObject(jsonstr);
		List li = new ArrayList();
		for(int i = 0;i<jsonArray.size();i++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			Object it = JSONObject.toBean(jsonObject, clz);
			li.add(it);
		}
		return li;
	}
	
	public static Object getArrayObject(JSONArray jsonArray,String findField,String find,String field){
		if (jsonArray==null) return null;
		int i;
		JSONObject jsonObject=null;
		for (i=0;i<jsonArray.size();i++){
			jsonObject=jsonArray.getJSONObject(i);
			if (jsonObject == null)	break;
			if (jsonObject.get(findField) != null && find.equals(jsonObject.get(findField).toString())){
				return jsonObject.get(field);
			}
		}	
		return null;
	}
	public static JSONObject bean2json(Object bean)
	{
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() {
			@Override
			public Object processObjectValue(String key, Object value,JsonConfig arg2)
			{
				if(value==null)
					return "";
				if (value instanceof Date) {
					Date v = (Date)value;
					Calendar cv = Calendar.getInstance();
					cv.setTime(v);
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String str = df1.format(v);
					if(cv.get(Calendar.HOUR_OF_DAY)!=0||cv.get(Calendar.MINUTE)!=0||cv.get(Calendar.SECOND)!=0)
					{
						df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						str = df1.format(v);
					}
					return str;
				}
				return value.toString();
			}
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});

        JSONObject jsonObject = JSONObject.fromObject(bean,cfg);
        return jsonObject;
	}
	
	public static JSONArray list2json(Object bean)
	{
		JsonConfig cfg = new JsonConfig();
		cfg.registerJsonValueProcessor(java.util.Date.class,new JsonValueProcessor() 
		{
			@Override
			public Object processObjectValue(String key, Object value,JsonConfig arg2)
			{
				if(value==null)
					return "";
				if (value instanceof Date) 
				{
					Date v = (Date)value;
					Calendar cv = Calendar.getInstance();
					cv.setTime(v);
					SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String str = df1.format(v);
					if(cv.get(Calendar.HOUR_OF_DAY)!=0||cv.get(Calendar.MINUTE)!=0||cv.get(Calendar.SECOND)!=0)
					{
						df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						str = df1.format(v);
					}
					return str;
				}
				return value.toString();
			}
			@Override
			public Object processArrayValue(Object arg0, JsonConfig arg1) {
				// TODO Auto-generated method stub
				return null;
			}
		});

        JSONArray jsonObject = JSONArray.fromObject(bean,cfg);
        return jsonObject;
	}
	
	public static Map getMap4Json(String jsonString){
		JSONObject jsonObject = JSONObject.fromObject( jsonString );
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;

		Map valueMap = new HashMap();
		while( keyIter.hasNext())
		{
			key = (String)keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
	
	public static Map getMap4Json(JSONObject jsonObject){
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;

		Map valueMap = new HashMap();
		while( keyIter.hasNext())
		{
			key = (String)keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}
		return valueMap;
	}
	public static JSONArray list2jsonAllFields(List li1,List li2) {
		// TODO Auto-generated method stub
		StringBuffer str=new StringBuffer();
		str.append("[");
		if(li1!=null&&li1.size()>0)
		{
			for (int i=0;i<li2.size();i++)
			{
				List li=(List)li2.get(i);
				if (li.size()>0)
				{
					if (i>0)	str.append(",");
					str.append("{");
					for (int j=0;j<li.size();j++)
					{
						if (j>0)	str.append(",");
						str.append("\"");
						str.append(li1.get(j));
						str.append("\"");
						str.append(":");
//						if (li.get(j) instanceof String)
//						{
//							str.append("\"");
//						}
						str.append("\"");
						if(li.get(j)!=null&&li.get(j).toString()!=null&&li.get(j).toString().indexOf("\"")!=-1) //将值中的双引号转义
						{
							String str1 = li.get(j).toString().replace("\"","\\\"");
							str.append(str1);
						}else{
//							
								str.append(li.get(j));
						}
//						if (li.get(j) instanceof String)
//						{
//							str.append("\"");
//							System.out.println("jsonstr"+j+"---9--"+str.toString());
//						}
						str.append("\"");		
						str = new StringBuffer(str.toString().replace("\r\n", "\\r\\n"));
					}
					str.append("}");
				}
			}
		}
		str.append("]");
		String jsonstr=str.toString();
/*		//jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll("\'","\\\\'");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		jsonstr = jsonstr.replace("\r", "\\r");
		jsonstr = jsonstr.replace("\n", "\\n");
		///jsonstr = jsonstr.replace("\\", "\\\\");
		//jsonstr = jsonstr.replaceAll("\n", "\\n");
		//jsonstr = jsonstr.replaceAll("\r", "\\r");
//		System.out.println("jsonstr1-----"+jsonstr);
*/	
		JSONArray jsonArray = JSONArray.fromObject(convertJSONString(jsonstr));
		return jsonArray;}
	
	public static JSONArray list2json(List li1,List li2) {
		StringBuffer str=new StringBuffer();
		str.append("[");
		if(li1!=null&&li1.size()>0){
			for (int i=0;i<li2.size();i++){
				List li=(List)li2.get(i);
				if (li.size()>0){
					if (i>0)	str.append(",");
					str.append("{");
					for (int j=0;j<li.size();j++){
						if (j>0)	str.append(",");
						str.append("\"");
						str.append(li1.get(j));
						str.append("\"");
						str.append(":");
						str.append("\"");
						
						if(li.get(j)!=null && !"".equals(li.get(j).toString())){
							String str1=convertStringJsonStr(li.get(j).toString()); 
							str.append(str1);
						}else{
							str.append(li.get(j));
						}
						str.append("\"");
//						str = new StringBuffer(convertStringJsonStr(str.toString()));
						
/*						if(li.get(j)!=null&&li.get(j).toString()!=null){
							String str1=li.get(j).toString().replace("\\","\\\\"); 
							str1= str1.replace("\"","\\\"");
							str.append(str1);
						}else{
							str.append(li.get(j));
						}
						str.append("\"");
						str = new StringBuffer(str.toString().replace("\r\n", "\\r\\n"));
*/
					}
					str.append("}");
				}
			}
		}
		str.append("]");
		String jsonstr=str.toString();
		JSONArray jsonArray = JSONArray.fromObject(convertJSONString(jsonstr));
		return jsonArray;
	}
	
	public static JSONObject object2json(List li1,List li2) {
		// TODO Auto-generated method stub
		StringBuffer str=new StringBuffer();
		if(li1!=null&&li1.size()>0&&li2!=null&&li2.size()>0)
		{
			List li=(List)li2.get(0);
			if (li.size()>0)
			{
				str.append("{");
				for (int j=0;j<li.size();j++)
				{
					if (j>0)	str.append(",");
					str.append("\"");
					str.append(li1.get(j));
					str.append("\"");
					str.append(":");
					if (li.get(j) instanceof String||li.get(j) instanceof Date)
						str.append("\"");							
					if(li.get(j)!=null&&li.get(j).toString()!=null&&li.get(j).toString().indexOf("\"")!=-1) //将值中的双引号转义
					{
						String str1 = li.get(j).toString().replace("\"","\\\"");
						str.append(str1);
					}else{
						str.append(li.get(j));
					}
					if (li.get(j) instanceof String||li.get(j) instanceof Date)
						str.append("\"");		
					str = new StringBuffer(str.toString());
				}
				str.append("}");
			}
		}
		String jsonstr=str.toString();
/*		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		jsonstr = jsonstr.replaceAll("\n", "\\\\n");
		jsonstr = jsonstr.replaceAll("\r", "\\\\r");
*/
		JSONObject jsonObject = JSONObject.fromObject(convertJSONString(jsonstr));		
		return jsonObject;
	}
	
	public static JSONObject sqlobject2json(List li1,List li2) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {

		StringBuffer str=new StringBuffer("{");
		if(li1!=null&&li1.size()>0&&li2!=null&&li2.size()>0)
		{
			List li=(List)li2.get(0);
			if (li.size()>0)
			{
				int z=0;
				for (int j=0;j<li.size();j++)
				{
					for (z=j-1;z>=0;z--)
					{
						if (li1.get(z).equals(li1.get(j)))	break;
					}
					if (z>=0)	continue;
					if (j>0)	str.append(",");
					str.append("\"");
					str.append(li1.get(j));
					str.append("\"");
					str.append(":");
					if (li.get(j) instanceof String||li.get(j) instanceof Date)
						str.append("\"");
//					if (li.get(j) instanceof String){
//						str.append(((String)li.get(j)).replace("\\", "\\\\"));
//					}
//					else
//						str.append(li.get(j));
					if(li.get(j)!=null&&li.get(j).toString()!=null&&li.get(j).toString().indexOf("\"")!=-1) //将值中的双引号转义
					{
						String str1 = li.get(j).toString().replace("\"","\\\"");
						str.append(str1);
					}else{
						str.append(li.get(j));
					}
					if (li.get(j) instanceof String||li.get(j) instanceof Date)
						str.append("\"");		
					str = new StringBuffer(str.toString().replace("\r\n", "\\r\\n"));
				}
			}
		}
		str.append("}");
		String jsonstr=str.toString();
/*		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		jsonstr = jsonstr.replaceAll("\n", "\\\\n");
		jsonstr = jsonstr.replaceAll("\r", "\\\\r");
//		jsonstr = jsonstr.replaceAll("<", "&lt;");
//		jsonstr = jsonstr.replaceAll(">", "&gt;");		
*/
		JSONObject jsonObject = JSONObject.fromObject(convertJSONString(jsonstr));
		return jsonObject;
	}
	
	public static JSONObject sqlOracleobject2json(List li1,List li2) throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException {

		StringBuffer str=new StringBuffer("{");
		if(li1!=null&&li1.size()>0&&li2!=null&&li2.size()>0)
		{
			List li=(List)li2.get(0);
			if (li.size()>0)
			{
				int z=0;
				for (int j=0;j<li.size();j++)
				{
					for (z=j-1;z>=0;z--)
					{
						if (li1.get(z).equals(li1.get(j)))	break;
					}
					if (z>=0)	continue;
					if (j>0)	str.append(",");
					str.append("\"");
					str.append(li1.get(j).toString().toLowerCase());
					str.append("\"");
					str.append(":");
					if (li.get(j) instanceof String||li.get(j) instanceof Date)
						str.append("\"");
//					if (li.get(j) instanceof String){
//						str.append(((String)li.get(j)).replace("\\", "\\\\"));
//					}
//					else
//						str.append(li.get(j));
					if(li.get(j)!=null&&li.get(j).toString()!=null&&li.get(j).toString().indexOf("\"")!=-1) //将值中的双引号转义
					{
						String str1 = li.get(j).toString().replace("\"","\\\"");
						str.append(str1);
					}else{
						str.append(li.get(j));
					}
					if (li.get(j) instanceof String||li.get(j) instanceof Date)
						str.append("\"");			
					str = new StringBuffer(str.toString().replace("\r\n", "\\r\\n"));
				}
			}
		}
		str.append("}");
		String jsonstr=str.toString();
/*		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		jsonstr = jsonstr.replaceAll("\n", "\\\\n");
		jsonstr = jsonstr.replaceAll("\r", "\\\\r");
//		jsonstr = jsonstr.replaceAll("<", "&lt;");
//		jsonstr = jsonstr.replaceAll(">", "&gt;");		
*/	
		JSONObject jsonObject = JSONObject.fromObject(convertJSONString(jsonstr));
		return jsonObject;
	}
	public static Object object2Bean(Object ob,Class clz) throws IntrospectionException{
		JSONObject jsonObj = JSONObject.fromObject(ob);
		JSONObject jsonObjValue=JSONObject.fromObject(ob);
		Iterator it = jsonObj.keys();  
      
		while (it.hasNext()) {
            String key = (String) it.next();
            if (jsonObj.get(key).getClass().equals(net.sf.json.JSONArray.class)){
               	jsonObjValue.remove(key);
               	continue;
            }
            if (jsonObj.get(key)==null||"".equals(jsonObj.get(key))||"null".equals(jsonObj.get(key))){
               	jsonObjValue.remove(key);
               	continue;
            }
        }  
		return  JSONObject.toBean(jsonObjValue, clz);
	}
	public static Object mapToBean(Map map, Class clz) throws IntrospectionException{
		
		Object obj = null;
		if (map==null)	return obj;
		Map tempMap=new HashMap();
		
		java.util.Iterator entries = map.entrySet().iterator();
		while (entries.hasNext ()) {
	      java.util.Map.Entry entry = (java.util.Map.Entry) entries.next ();
	      if (entry.getValue().getClass().isArray()){
	    	  String value[]=(String [])entry.getValue();
	    	  if (value.length==1){
	    		  tempMap.put(entry.getKey(), value[0]);
	    	  }else if(value.length>1){
	    		  StringBuilder sb = new StringBuilder();
	    		  for(int i=0;i<value.length;i++){
	    			  sb.append(value[i]);
	    			  if(i<value.length-1){
	    				  sb.append(",");
	    			  }
	    		  }
	    		  tempMap.put(entry.getKey(), sb.toString());
	    	  }else{
	    		  tempMap.put(entry.getKey(), value);
	    	  }
	      }else{
    		  tempMap.put(entry.getKey(), entry.getValue());
	      }
	    }
		return object2Bean(tempMap,clz);
	}
	public static Object mapToBeanForUpdate(Map map,Class clz,Object obj) throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException, InvocationTargetException
	{
		java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(clz, Object.class);
        PropertyDescriptor[] pds = info.getPropertyDescriptors();
        String value;
        for(int k=0; k<pds.length; k++)
        {
        	String pname = pds[k].getName();//.toLowerCase();
        	if(!pname.equals("id")&&map.get(pname)!=null)
        	{	
        		if (map.get(pname).getClass().isArray()){
	      	    	  String values[]=(String [])map.get(pname);
      	    		  value=values[0];
	      	      }else{
	      	    	  value=(String)map.get(pname);
	      	      }
        		pds[k].getWriteMethod().invoke(obj,new Object[] {strToBean(value,pds[k].getPropertyType())});
        	}
        }
        return obj;
	}

	/**
	 * 存储编码中文值进行判断
	 * @param map
	 * @param clz
	 * @return
	 * @throws IntrospectionException
	 */
	public static Object mapToBeanSpecial(Map map, Class clz) throws IntrospectionException{
		Object obj = null;
		if (map==null)	return obj;
		Map tempMap=new HashMap();
		java.util.Iterator entries = map.entrySet().iterator();
		while (entries.hasNext ()) {
	      java.util.Map.Entry entry = (java.util.Map.Entry) entries.next ();
	      if (entry.getValue().getClass().isArray()){
	    	  String value[]=(String [])entry.getValue();
	    	  if (value.length==1){
	    		  tempMap.put(entry.getKey(), value[0]);
	    	  }
	    	  else{
	    		  tempMap.put(entry.getKey(), value);
	    	  }
	      }else{
    		  tempMap.put(entry.getKey(), entry.getValue());
	      }
	    }
		JSONObject jsonObj = JSONObject.fromObject(tempMap);
		JSONObject jsonObjValue=JSONObject.fromObject(tempMap);
		Iterator it = jsonObj.keys();  
		while (it.hasNext()) {
            String key = (String) it.next();
           // System.out.println("---key:" + key);
            if (jsonObj.get(key).getClass().equals(net.sf.json.JSONArray.class)){
               	jsonObjValue.remove(key);
               	continue;
            }
            if (jsonObj.get(key)==null||"".equals(jsonObj.get(key))||"null".equals(jsonObj.get(key))){
               	jsonObjValue.remove(key);
               	continue;
            }
            if(isViewField(key+"_view",clz)) {
            	//实体类中包含中文显示字段
            	if(jsonObjValue.containsKey(key+"_view")) {
            		jsonObjValue.remove(key+"_view");
            	}
            	jsonObjValue.put(key+"_view", InitialManager.getItemMap().get(jsonObj.get(key)));
            }    
        }  
		return  JSONObject.toBean(jsonObjValue, clz);
	}
	
	public static boolean isViewField(String key, Class clz) {
		boolean temp = false;
		try {
			Field ff = clz.getDeclaredField(key);
			temp = true;
			
		} catch (SecurityException e) {
			temp = false;
		} catch (NoSuchFieldException e) {
			temp = false;
		}
		return temp;
	}
	
	/**
	 * 修改数据时考虑存储编码中文值进行判断
	 * @param map
	 * @param clz
	 * @param obj
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object mapToBeanForUpdateSpecial(Map map,Class clz,Object obj) throws InstantiationException, IllegalAccessException, 
		IntrospectionException, IllegalArgumentException, InvocationTargetException{
		
		java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(clz, Object.class);
	    PropertyDescriptor[] pds = info.getPropertyDescriptors();
	    String value;
	    for(int k=0; k<pds.length; k++)
	    {
	    	String pname = pds[k].getName();
	    	if(!pname.equals("id"))
	    	{	
	    		if(pname.endsWith("_view")) {
	    			String subname = pname.substring(0, pname.length()-5);
	    			if (map.get(subname) == null) continue;
	    			if (map.get(subname).getClass().isArray()){
		    			if(map.get(subname) != null && ((String[])map.get(subname))[0] != null && !"".equals(((String[])map.get(subname))[0])) {
		    				value = InitialManager.getItemMap().get(((String[])map.get(subname))[0]).toString();
		    				pds[k].getWriteMethod().invoke(obj,new Object[] {strToBean(value,pds[k].getPropertyType())});
		    			}
    				}else{
		    			if(map.get(subname) != null && map.get(subname)!= null && !"".equals(map.get(subname))) {
		    				value = InitialManager.getItemMap().get(map.get(subname)).toString();
		    				pds[k].getWriteMethod().invoke(obj,new Object[] {strToBean(value,pds[k].getPropertyType())});
		    			}
    				}
	    		}else {
	    			if(map.get(pname) != null){
	    				if (map.get(pname).getClass().isArray()){
			      	    	  String values[]=(String [])map.get(pname);
			  	    		  value=values[0];
			      	    }else{
			      	    	  value=(String)map.get(pname);
			      	    }
	    				pds[k].getWriteMethod().invoke(obj,new Object[] {strToBean(value,pds[k].getPropertyType())});
	    			}
	    		}
	    	}
	    }
	    return obj;
	}
	
	private static String convertJSONString(String jsonstr){
		jsonstr = jsonstr.replaceAll("\"\\w*?\\W??\\w*?\":\"\",?","");
		jsonstr = jsonstr.replaceAll("\'","\\\\'");
		jsonstr = jsonstr.replaceAll(",}$", "}");
		jsonstr = jsonstr.replace("\n", "\\u000A").replace("\r", "\\u000D");
		return jsonstr;
	}
	public static String convertStringJsonStr(String str){
		String tempStr=str;
		tempStr=tempStr.replace("\\", "\\\\");
		tempStr=tempStr.replace("\"", "\\\"");
		tempStr=tempStr.replace("/", "\\/");
		tempStr=tempStr.replace("\b", "\\b");
		tempStr=tempStr.replace("\f", "\\f");
		tempStr=tempStr.replace("\n", "\\n");
		tempStr=tempStr.replace("\r", "\\r");
		str=str.replace("\r\n", "\\r\\n");
		tempStr=tempStr.replace("\t", "\\t");
		
		return tempStr;
	}
	
	public static JSONObject compareJsonObject(JSONObject oldJsonObj,JSONObject newJsonObj){
		JSONObject returnJsonObj=new JSONObject();
		Iterator it;
		if (!oldJsonObj.isNullObject()){
			it= oldJsonObj.keys();  
			while (it.hasNext()) {
	            String key = (String) it.next();
	            if (!oldJsonObj.get(key).equals(newJsonObj.get(key))){
	            	JSONObject value=new JSONObject();
	            	value.put("new", newJsonObj.get(key));
	            	value.put("old", oldJsonObj.get(key));
	            	returnJsonObj.put(key, value);
	            	if(key.endsWith("_view")){     // 当以_view结尾,则需要去掉该字段的编码描述
	            		newJsonObj.remove(key.substring(0, key.length()-5));
	            		returnJsonObj.remove(key.substring(0, key.length()-5));
		            }
	            }
	            newJsonObj.remove(key);
			}
		}
		it = newJsonObj.keys();  
		while (it.hasNext()) {
            String key = (String) it.next();
        	JSONObject value=new JSONObject();
        	value.put("new", newJsonObj.get(key));
        	value.put("old", null);
        	returnJsonObj.put(key, value);
		}
		return returnJsonObj;
    }	
	public static JSONObject mapToJson(Map map){
		return JSONObject.fromObject(filterMap(map));
	}
	private static Map filterMap(Map map){
		if (map==null)	return null;
		Map tempMap=new HashMap();
		java.util.Iterator entries = map.entrySet().iterator();
		while (entries.hasNext ()) {
	      java.util.Map.Entry entry = (java.util.Map.Entry) entries.next ();
	      if (entry.getValue().getClass().isArray()){
	    	  String value[]=(String [])entry.getValue();
	    	  if (value.length==1){
	    		  tempMap.put(entry.getKey(), value[0]);
	    	  }
	    	  else{
	    		  tempMap.put(entry.getKey(), value);
	    	  }
	      }else{
    		  tempMap.put(entry.getKey(), entry.getValue());
	      }
	    }
		return tempMap;
	}
	
}
