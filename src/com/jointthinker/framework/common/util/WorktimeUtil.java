package com.jointthinker.framework.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WorktimeUtil {
	
	public static Long getDifftime(Date tempstoretime, Date currenttime, String workdayam, String workdaypm) {
		//System.out.println("--111--tempstoretime:" + DateUtil.toDefaultString(tempstoretime) + "--222--currenttime:" + DateUtil.toDefaultString(currenttime));
		long t_diff = 0L;
		List resultList = new ArrayList();
		int days = DateUtil.getDaysOfTwoDate(tempstoretime, currenttime);
		//System.out.println("----days:" + days);
		if(days==0) {
			//当前天数不做操作,直接添加
			resultList.add(new Object[]{tempstoretime,currenttime});			
		}else if(days>0) {
			//第一个分组
			Date _oper = tempstoretime;
			Date _curr = DateUtil.toDate(DateUtil.toDefaultString(_oper).substring(0,10)+ " 23:59:59");
			resultList.add(new Object[]{_oper,_curr});			
			//中间分组
			if(days>1){
				for(int n=1; n<days; n++) {						
					Date _oper1 = DateUtil.toDate(DateUtil.getDaysAfterDate(tempstoretime, n).substring(0,10)+ " 00:00:00");
					Date _curr1 = DateUtil.toDate(DateUtil.getDaysAfterDate(tempstoretime, n).substring(0,10)+ " 23:59:59");
					resultList.add(new Object[]{_oper1,_curr1});						
				}
			}	
			//最后一个分组
			Date _operDay = DateUtil.toDate(DateUtil.getDaysAfterDate(tempstoretime, days).substring(0,10)+ " 00:00:00");
			Date _currDay = currenttime;
			resultList.add(new Object[]{_operDay,_currDay});			
		}
		for(int i=0; i<resultList.size(); i++) {
			Object[] dateObj = (Object[])resultList.get(i);
			Date start = (Date)dateObj[0];
			Date end = (Date)dateObj[1];
			Date starttime = DateUtil.getWorkDateTime(start,workdayam,workdaypm);
			Date endtime = DateUtil.getWorkDateTime(end,workdayam,workdaypm);
			t_diff += DateUtil.compareDateLong(starttime, endtime);
		}
		System.out.println("----t_diff:" + t_diff);
		return new Long(t_diff);
	}

	public static Object[] getTermtype(Date operatetime, Date currenttime, String workdayam, String workdaypm,long preterms, long terms) {
	
		String termtype = "";
		Object[] obj = new Object[2];
		List resultList = new ArrayList();
		int days = DateUtil.getDaysOfTwoDate(operatetime, currenttime);
		//System.out.println("days:" + days);
		if(days==0) {
			//当前天数不做操作,直接添加
			resultList.add(new Object[]{operatetime,currenttime});			
		}else if(days>0) {
			//第一个分组
			Date _oper = operatetime;
			Date _curr = DateUtil.toDate(DateUtil.toDefaultString(_oper).substring(0,10)+ " 23:59:59");
			resultList.add(new Object[]{_oper,_curr});			
			//中间分组
			if(days>1){
				for(int n=1; n<days; n++) {						
					Date _oper1 = DateUtil.toDate(DateUtil.getDaysAfterDate(operatetime, n).substring(0,10)+ " 00:00:00");
					Date _curr1 = DateUtil.toDate(DateUtil.getDaysAfterDate(operatetime, n).substring(0,10)+ " 23:59:59");
					resultList.add(new Object[]{_oper1,_curr1});						
				}
			}	
			//最后一个分组
			Date _operDay = DateUtil.toDate(DateUtil.getDaysAfterDate(operatetime, days).substring(0,10)+ " 00:00:00");
			Date _currDay = currenttime;
			resultList.add(new Object[]{_operDay,_currDay});			
		}
		long t_diff = 0L;
		//System.out.println("resultList.size():" + resultList.size() + "---holidayList.size():" + holidayList.size());
		for(int i=0; i<resultList.size(); i++) {
			Object[] dateObj = (Object[])resultList.get(i);
			Date start = (Date)dateObj[0];
			Date end = (Date)dateObj[1];
			Date starttime = DateUtil.getWorkDateTime(start,workdayam,workdaypm);
			Date endtime = DateUtil.getWorkDateTime(end,workdayam,workdaypm);
			//System.out.println("starttime:" + DateUtil.toDefaultString(starttime) + "endtime:" + DateUtil.toDefaultString(endtime));
			String cudate = DateUtil.getDateByDate(start);//获取当前日期+"00:00:00"
		}
		//System.out.println(preterms+"----相差时间:" + t_diff);
		if(t_diff < preterms) {
			termtype = "正常";
		}else if(t_diff>= preterms && t_diff<=terms) {
			termtype="预警";
		}else{
			termtype="超期";
		}
		obj[0] = termtype;
		obj[1] = new Long(t_diff);
		return obj;
	}

	
	
	 /**
	   * 
	   * @param sjtime
	   *          事件开始时间
	   * @param dqtime
	   *          当前时间
	   * @param timecha
	   *          系统定义的相差毫秒数
	   * @return 返回boolean
	   */
	  public static boolean isUpgrade(Date begindate, Date currenttime,long termseconds, TimeInstanceBean timeBean)
	  {
	    boolean result = false;    
	    String amstart = timeBean.getAmstart();
	    String amend = timeBean.getAmend();
	    String pmstart = timeBean.getPmstart();
	    String pmend = timeBean.getPmend();    
	    long rsult = 0;
	    try
	    {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	      String sjstarttime = sdf.format(begindate);// 事件时间
	      String nowtime = sdf.format(currenttime);// 格式化当前工的 时间
	      String nowdate = sdf2.format(currenttime);// 取出当前时间的日期
	      String vamstart = nowdate + " " + amstart;// 拼当前时间当天上午的上班时间
	      String vamend = nowdate + " " + amend;// 拼传入时间当天上午的下班时间
	      String vpmstart = nowdate + " " + pmstart;// 拼传入时间当天下午的上班时间
	      String vpmend = nowdate + " " + pmend;// 拼传入时间当天下午的下班时间
	      // 将所有日期转为 date型
	      Date a = sdf.parse(nowtime);
	      Date sj = sdf.parse(sjstarttime);
	      Date amst = sdf.parse(vamstart);
	      Date amen = sdf.parse(vamend);
	      Date pmst = sdf.parse(vpmstart);
	      Date pmen = sdf.parse(vpmend);

	      // 取得当前日期是星期几
	      String dt = a.toString().substring(0, 3);
	      // 将所有时间换成秒数
	      long nt = a.getTime();
	      long sjt = sj.getTime();
	      long ast = amst.getTime();
	      long aen = amen.getTime();
	      long pst = pmst.getTime();
	      long pen = pmen.getTime();
	      long daywork = aen - ast + (pen - pst);
	      // 当前时间小于早上8点 或者
	      // 大于下午下班时间
	      // 或者大于上午下班小于下午上班时间
	      // 或者现在时间是星期六或星期日
	      if (nt < ast || nt > pen || (nt > aen && nt < pst) || "Sat".equals(dt) || "Sun".equals(dt))
	      {
	        result = false;
	        return result;
	      }
	      long astsjt = ast - sjt;// 事件开始时间和当天上午上班时间的间隔
	      long howday = 0;// 事件开始时间和当天上午上班间隔几天
	      long lastast = 0;
	      long lastaen = 0;
	      long lastpst = 0;
	      long lastpen = 0;
	      long jgseconds = 0;// 如果事件是前几天上班时间启动的，此值为前几天的事件启动时间
	      if (astsjt > 0)// 如果事件开始时间早于当天上午上班时间
	      {
	        howday = astsjt / 86400000;
	        lastast = ast - (howday + 1) * 86400000;// 事件启动时间所在天的前（howday+1）天上午上班时间
	        lastaen = aen - (howday + 1) * 86400000;// 事件启动时间所在天的前（howday+1）天上午下班时间
	        lastpst = pst - (howday + 1) * 86400000;// 事件启动时间所在天的前（howday+1）天下午上班时间
	        lastpen = pen - (howday + 1) * 86400000;// 事件启动时间所在天的前（howday+1）天下午下班时间
	        if (lastast >= sjt && sjt < lastaen)
	        {// 事件启动时间晚于上午上班时间 早于上午下班时间
	          jgseconds = lastaen - sjt + (lastpen - lastpst);
	        }
	        if (sjt >= lastaen && sjt < lastpst)
	        {
	          jgseconds = lastpen - lastpst;
	        }
	        if (sjt >= lastpst && sjt < lastpen)
	        {
	          jgseconds = lastpen - sjt;
	        }
	        if (sjt >= lastpen)
	        {
	          jgseconds = 0;
	        }
	      }

	      // 如果事件开始时间小于早上上班时间8：00
	      if (sjt < ast)
	      {
	        // 当前时间晚于8：00早并且早于12：00
	        if (nt >= ast && nt <= aen)
	        {
	          rsult = nt - ast;
	        }
	        // 当前时间晚于13：30并且早于18：00
	        if (nt >= pst && nt <= pen)
	        {
	          rsult = aen - ast + (nt - pst);
	        }
	      }
	      // 如果事件开始时间晚于8：00并且早于12：00
	      if (sjt >= ast && sjt < aen)
	      {
	        // 当前时间晚于8：00早并且早于12：00
	        if (nt >= ast && nt <= aen)
	        {
	          rsult = nt - sjt;
	        }
	        // 当前时间晚于13：30早并且早于18：00
	        if (nt >= pst && nt <= pen)
	        {
	          rsult = aen - sjt + (nt - pst);
	        }
	      }
	      // 如果事件开始时间晚于12：00并且早于13：30
	      if (sjt >= aen && sjt < pst)
	      {
	        // 当前时间晚于13：30并且早于18：00
	        if (nt >= pst && nt <= pen)
	        {
	          rsult = nt - pst;
	        }
	      }
	      // 如果事件开始时间晚于13：30 并且早于 18：00
	      if (sjt >= pst && sjt < pen)
	      {
	        // 当前时间晚于13：30并且早于18：00
	        if (nt >= pst && nt <= pen)
	        {
	          rsult = nt - sjt;
	        }
	      }
	      // 如果事件开始时间晚于18：00
	      if (sjt >= pen)
	      {
	        result = false;
	        return result;
	      }
	      long jjzmdays = WorktimeUtil.howmanyrest(begindate, currenttime, howday);
	      long jg = howday - jjzmdays;
	      if (jg < 0)
	      {
	        jg = 0;
	      }
	      rsult = rsult + jgseconds + jg * daywork;
	     // System.out.println("相差的毫秒数=" + rsult + " 系统定义的相隔毫秒数=" + termseconds);
	      if (rsult < termseconds)
	      {
	        result = false;
	      }
	      else
	      {
	        result = true;
	      }
	      return result;

	    }
	    catch (ParseException e)
	    {
	      
	      e.printStackTrace();
	      result = false;
	    }
	    return result;
	  }

	  //取到当前时间和发送时间之间有几个节假日
	  //
	  /**
	   * 
	   * @param sjtime
	   *          事件开始时间
	   * @param nowamstart
	   *          现在时间
	   * @param jgdays
	   *          两个时间相隔的天数（取整）
	   * @return 返回两个日期之间有几天是周末
	   */
	  public static int howmanyrest(Date begindate, Date currenttime, long jgdays)
	  {
	    int days = 0;
	    try
	    {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	      SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
	      String sjdate = sdf.format(begindate);
	      String nowdate = sdf.format(currenttime);
	      Date sj = sdf.parse(sjdate);
	      Date now = sdf.parse(nowdate);
	      Calendar sjcl = Calendar.getInstance();
	      sjcl.setTime(sj);
	      Calendar nowcl = Calendar.getInstance();
	      nowcl.setTime(now);
	      for (int i = 0; i <= jgdays; i++)
	      {
	        if (i == 0)
	        {
	          sjcl.add(Calendar.DAY_OF_WEEK, 0);
	        }
	        else
	        {
	          sjcl.add(Calendar.DAY_OF_WEEK, 1);
	        }
	        Date sjcd = sjcl.getTime();
	        String strsj = sdf.format(sjcd);
	        String yr = sdf2.format(sjcd);
	       
	        int xingqi = sjcl.get(Calendar.DAY_OF_WEEK);
	      
	        if (xingqi == 7 || xingqi == 1)
	        {
	            days++;
	        }
	        if (strsj.equals(nowdate))
	        {
	          break;
	        }
	      }
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return days;
	  }

}