package com.jointthinker.framework.bean;

import java.util.ArrayList;
import java.util.List;

public class ViewBean {

	@SuppressWarnings("unchecked")
	private List labellist;
	
	@SuppressWarnings("unchecked")
	private List valuelist;
	
	private int size;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ViewBean() {}
	
	@SuppressWarnings("unchecked")
	public List getLabellist() {
		return labellist;
	}

	@SuppressWarnings("unchecked")
	public void setLabellist(List labellist) {
		this.labellist = labellist;
	}

	@SuppressWarnings("unchecked")
	public List getValuelist() {
		return valuelist;
	}

	@SuppressWarnings("unchecked")
	public void setValuelist(List valuelist) {
		this.valuelist = valuelist;
	}
	/**
	 * 去除ViewBean中某个字段
	 * @param fields
	 * @return
	 */
	public boolean removeViewBeanField(String... fields) {
		if (fields == null || fields.length == 0) {
			return false;
		}
			
		int len_fields = fields.length;
		int len_label = this.labellist.size();
		int len_value = this.valuelist.size();
		for (int i=0; i<len_fields; i++) {
			for (int j=0; j<this.labellist.size(); j++) {
				if (fields[i].toLowerCase().equals(this.labellist.get(j).toString())) {
					
					for (int k=0; k<this.valuelist.size(); k++) {
						((List)(this.valuelist.get(k))).remove(j);
					}
					this.labellist.remove(j);
				}
			}
		}
		return true;
	}
	/**
	 * 修改ViewBean中某个字段名称
	 * @param oldfields
	 * @param newfields
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean modifyViewBeanField(String[] oldfields, String[] newfields) {
		if (oldfields == null || newfields == null || oldfields.length == 0 || newfields.length == 0) {
			return false;
		}
		
		int len_oldfields = oldfields.length;
		int len_newfields = newfields.length;
		int len_label = this.labellist.size();
		
		if (len_oldfields != len_newfields) {
			return false;
		}
		for (int i=0; i<len_oldfields; i++) {
			for (int j=0; j<len_label; j++) {
				if (oldfields[i].toLowerCase().equals(this.labellist.get(j).toString())) {
					this.labellist.set(j, newfields[j]);
					break;
				}
			}
		}
		return true;
	}
	
	public String getViewBeanValue(String label,int number)
	{
		String tempstr=null;
		
		if (this.labellist!=null&&this.labellist.size()>0&&this.valuelist!=null&&this.valuelist.size()>number)
		{
			List li=(List)this.valuelist.get(number);
			if (li.size()>0)
			{
				for(int j=0;j<li.size();j++)
				{
					if (this.labellist.get(j).toString().compareToIgnoreCase(label)==0)
					{
						tempstr= li.get(j)==null?null:li.get(j).toString();
						break;
					}
				}
			}
		}
		return tempstr;
	}	

	public void setViewBeanValue(String label,int number,String value)
	{
		String tempstr=null;
		
		if (this.labellist!=null&&this.labellist.size()>0&&this.valuelist!=null&&this.valuelist.size()>number)
		{
			List li=(List)this.valuelist.get(number);
			if (li.size()>0)
			{
				for(int j=0;j<li.size();j++)
				{
					if (this.labellist.get(j).toString().compareToIgnoreCase(label)==0)
					{
						li.set(j, value);
						break;
					}
				}
			}
		}
		return ;
	}	
	public int ifField(String fieldName){
		if (this.labellist!=null&&this.labellist.size()>0)
		{
			for(int j=0;j<this.labellist.size();j++)
			{
				if (this.labellist.get(j).toString().compareToIgnoreCase(fieldName)==0)
				{
					return j;
				}
			}
		}
		return -1;
	}
	public List select(String field,String value){
		List result = new ArrayList();
		if (this.labellist!=null&&this.labellist.size()>0&&this.valuelist!=null&&this.valuelist.size()>0)
		{	
			for(int j=0;j<this.labellist.size();j++)
			{
				if (this.labellist.get(j).toString().compareToIgnoreCase(field)==0)
				{
					
					for (int i=0;i<this.valuelist.size();i++){
						if (((List)this.valuelist.get(i)).get(j).toString().equals(value)){
							result.add(this.valuelist.get(i));
						}
					}
					break;
				}
			}
		}
		return result;
	}
	public List selectField(String whereField,String value,String returnField){
		List result = new ArrayList();
		int fieldNum;
		if (this.labellist!=null&&this.labellist.size()>0&&this.valuelist!=null&&this.valuelist.size()>0&&(fieldNum=ifField(returnField))>-1)
		{	
			for(int j=0;j<this.labellist.size();j++)
			{
				if (this.labellist.get(j).toString().compareToIgnoreCase(whereField)==0)
				{
					for (int i=0;i<this.valuelist.size();i++){
						if (((List)this.valuelist.get(i)).get(j).toString().equalsIgnoreCase(value)){
							result.add(((List)this.valuelist.get(i)).get(fieldNum));
						}
					}
					break;
				}
			}
		}
		return result;
	}	
	public static void main(String[] args) {
//		String str = "{'ro':'1','id':'297312','username':'ccidcall_test2','logindate':'2013-12-10 11:24:16','status':'1','ip':'10.1.159.221','source':'web'},{'ro':'2','id':'297311','username':'ccidcall_test1','logindate':'2013-12-10 10:28:55','status':'1','ip':'10.1.159.221','source':'web'},{'ro':'3','id':'297310','username':'root','logindate':'2013-12-10 10:28:38','status':'0','ip':'10.1.159.221','source':'web'},{'ro':'4','id':'297309','username':'zhanglei27','logindate':'2013-12-10 09:45:35','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'5','id':'271568','username':'ccidcall_test1','logindate':'2013-12-09 19:18:58','status':'1','ip':'10.1.159.154','source':'app'},{'ro':'6','id':'271567','username':'ccidcall_test1','logindate':'2013-12-09 19:12:43','status':'1','ip':'10.1.159.154','source':'app'},{'ro':'7','id':'271566','username':'ccidcall_test1','logindate':'2013-12-09 19:09:39','status':'1','ip':'10.1.159.205','source':'app'},{'ro':'8','id':'271565','username':'zhanglei27','logindate':'2013-12-09 19:03:44','status':'1','ip':'10.1.159.205','source':'app'},{'ro':'9','id':'271564','username':'ccidcall_test1','logindate':'2013-12-09 18:49:30','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'10','id':'271563','username':'ccidcall_test1','logindate':'2013-12-09 18:45:53','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'11','id':'271562','username':'ccidcall_test1','logindate':'2013-12-09 18:44:40','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'12','id':'271561','username':'ccidcall_test1','logindate':'2013-12-09 18:43:01','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'13','id':'271560','username':'ccidcall_test1','logindate':'2013-12-09 18:28:31','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'14','id':'271559','username':'ccidcall_test1','logindate':'2013-12-09 18:22:52','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'15','id':'271558','username':'ccidcall_test1','logindate':'2013-12-09 18:20:34','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'16','id':'271557','username':'ccidcall_test1','logindate':'2013-12-09 18:19:54','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'17','id':'271556','username':'ccidcall_test1','logindate':'2013-12-09 18:19:23','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'18','id':'271555','username':'ccidcall_test1','logindate':'2013-12-09 18:15:13','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'19','id':'271554','username':'ccidcall_test1','logindate':'2013-12-09 18:14:15','status':'1','ip':'10.1.159.221','source':'app'},{'ro':'20','id':'271553','username':'ccidcall_test1','logindate':'2013-12-09 18:00:24','status':'1','ip':'10.1.159.221','source':'app'}";
	}
}
